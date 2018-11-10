package mhfc.net.common.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import com.github.worldsender.mcanm.client.model.util.ModelStateInformation;
import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;
import com.github.worldsender.mcanm.client.renderer.IAnimatedObject;
import com.github.worldsender.mcanm.common.animation.IAnimation;

import io.netty.buffer.ByteBuf;
import mhfc.net.MHFCMain;
import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.IManagedActions;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.TargetTurnHelper;
import mhfc.net.common.ai.general.actions.DeathAction;
import mhfc.net.common.ai.manager.AIActionManager;
import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class should provide a good base to code off. As almost every entity in Monster Hunter is a multi- box Entity
 * extending this class will have you covered. Instead of setting {@link Entity#width} and {@link Entity#height} you
 * should set your bounding box correctly in the constructor.
 *
 * @author WorldSEnder, HeroicKatora
 *
 * @param <YC>
 *            your class, the class of the attacks
 *
 */
public abstract class CreatureAttributes<YC extends CreatureAttributes<YC>> extends EntityCreature
		implements
		IEntityMultiPart,
		IAnimatedObject,
		IManagedActions<YC>,
		IEntityAdditionalSpawnData {
	private static final DataParameter<Integer> ANIM_FRAME = EntityDataManager
			.<Integer>createKey(CreatureAttributes.class, DataSerializers.VARINT);
	private final TargetTurnHelper turnHelper;
	private IActionManager<? extends YC> attackManager;

	private IExecutableAction<? super YC> deathAction;
	private IExecutableAction<? super YC> inWaterAction;
	private IExecutableAction<? super YC> stunAction;

	// Only of importance on the server. On the server, ANIM_FRAME is not the actual animation frame,
	// but the last frame we sent to the client. If the animation is set to a frame other than
	// ANIM_FRAME + ticksSinceFrameShared, we update ANIM_FRAME, otherwise, we believe that the
	// client is able to the frame up-to-date himself
	private int ticksSinceFrameShared = 0;

	protected boolean hasDied;
	// @see deathTime. DeathTime has the random by-effect of rotating corpses...
	protected int deathTicks;

	// Public Variables.
	public float targetDistance;

	/** This should be a setup, for every monster hoping they can feature to Quest and JSON. thus they
	 *  can have the multiplier and health boost feature soon for Quest difficulty Regards **/
	@SuppressWarnings("rawtypes")
	public static Map<Class, Double> baseHealthInstance = new HashMap<Class, Double>();

	// ***************************************************************************************
	
	public CreatureAttributes(World world) {
		super(world);
		turnHelper = new TargetTurnHelper(this);
		attackManager = Objects.requireNonNull(constructActionManager());
		this.ignoreFrustumCheck = true;
		hasDied = false;
		this.isImmuneToFire = true;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(45D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(30D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(5.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20D);

	}


	/** Get the entity name **/
	protected String getEntityName() {
		return EntityList.getEntityString(this);
	}

	protected <A extends IExecutableAction<? super YC>> A setDeathAction(A action) {
		this.deathAction = action;
		return action;
	}

	protected <A extends IExecutableAction<? super YC>> A setInWaterAction(A action) {
		inWaterAction = action;
		return action;
	}

	protected <A extends IExecutableAction<? super YC>> A setStunAction(A action) {
		stunAction = action;
		return action;
	}

	protected IAnimation getRestPoseAnimation() {
		return ModelStateInformation.BIND_POSE;
	}


	@Override
	public void onUpdate() {
		super.onUpdate();
		
		/** Updates when this monster target is not null **/
		if (this.getAttackTarget() != null) {
			this.targetDistance = this.getDistanceToEntity(this.getAttackTarget());
		}

		/** When the attackManager is still on the Executes, then it will still continuous to update the task    **/
		if (this.attackManager.continueExecuting()) {
			this.attackManager.updateTask();
		}
		
		/** A complex statement in which the frames of the AI ticks when the world is on Remoted sided **/
		if (this.world.isRemote) {
			int frame = getFrame();
			if (frame >= 0) {
				setFrame(frame + 1);
			}
		} else {
			ticksSinceFrameShared++;
			int nextFrame = this.attackManager.getCurrentFrame();
			setFrame(nextFrame);
		}

		/** IMMEDIATE AI AND ANIMATION SWITCH CASES   **/
		
		/** When this statements are fired the getActionManager will immediate executes the animation and AI variable **/
		if (this.isPotionActive(MHFCPotionRegistry.getRegistry().stun)) {
			getActionManager().switchToAction(stunAction);
		}
		if (this.isInWater()) {
			getActionManager().switchToAction(inWaterAction);
		}
		
	}


	protected abstract IActionManager<YC> constructActionManager();

	/**
	 * Specialize the return type to a {@link CollisionParts}
	 */
	@Override
	public abstract CollisionParts[] getParts();

	/** Monsters Death Update **/
	@Override
	protected void onDeathUpdate() {
		if (!hasDied) {
			onDeath();
			hasDied = true;
		}
		spawnDeadParticles();
		deathTicks++;
		/** Half the time of the mob spawns death items. **/
		if (deathTicks == (DeathAction.deathLingeringTicks / 2)) {
			if (!world.isRemote) {
				boolean killedByPlayer = true;
				int specialLuck = 100;
				dropFewItems(killedByPlayer, specialLuck);
			}
		}
		if (deathTicks >= DeathAction.deathLingeringTicks || deathAction == null) {
			onDespawn();
			setDead();
		}
	}

	/** A primitive method on how the monsters last and while dead they are decomposing. **/
	protected void spawnDeadParticles() {
		float timed = (float) deathTicks / DeathAction.deathLingeringTicks;
		timed = Math.max(0, timed - 0.1f) / 0.9f;
		int particleCount = (int) (20 * Math.pow(timed, 4));
		for (int i = 0; i < particleCount; i++) {
			double randX = rand.nextDouble(), randZ = rand.nextDouble(), randY = rand.nextDouble();
			randX = randX * (this.getEntityBoundingBox().maxX - getEntityBoundingBox().minX)
					+ getEntityBoundingBox().minX;
			randZ = randZ * (getEntityBoundingBox().maxZ - getEntityBoundingBox().minZ) + getEntityBoundingBox().minZ;
			randY = Math.pow(randY, 3) * (getEntityBoundingBox().maxY - getEntityBoundingBox().minY)
					+ getEntityBoundingBox().minY;
			double velX = this.rand.nextGaussian() * 0.01D;
			double velY = Math.abs(this.rand.nextGaussian() * 0.7D);
			double velZ = this.rand.nextGaussian() * 0.01D;
			world.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, randX, randY, randZ, velX, velY, velZ);
		}
	}

	/** Follow up method from this.spawnDeadParticles() **/
	protected void onDespawn() {

		for (int i = 0; i < 20; i++) {
			double randX = rand.nextDouble(), randZ = rand.nextDouble(), randY = rand.nextGaussian();
			randX = randX * (getEntityBoundingBox().maxX - getEntityBoundingBox().minX) + getEntityBoundingBox().minX;
			randZ = randZ * (getEntityBoundingBox().maxZ - getEntityBoundingBox().minZ) + getEntityBoundingBox().minZ;
			randY += posY;
			double velX = this.rand.nextGaussian() * 0.01D;
			double velY = Math.abs(this.rand.nextGaussian() * 0.2D);
			double velZ = this.rand.nextGaussian() * 0.01D;
			world.spawnParticle(EnumParticleTypes.CLOUD, randX, randY, randZ, velX, velY, velZ);
		}
	}



	protected void onDeath() {
		if (deathAction != null) {
			getActionManager().switchToAction(deathAction);
		}
	}

	/**
	 * 
	 * There will be some
	 * 
	 * 
	 */

	@Override
	protected boolean canDropLoot() {
		return false;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ANIM_FRAME, Integer.valueOf(-1));
	}

	protected void dropItemRand(ItemStack stack) {
		Random rand = world.rand;
		EntityItem entityItem = new EntityItem(
				this.world,
				posX + rand.nextInt(10) - 5,
				posY + 1.0D,
				posZ + rand.nextInt(10) - 5,
				stack);
		world.spawnEntity(entityItem);
	}

	public double healthbaseHP(double defaultHP) {
		return defaultHP;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected void kill() {
		this.attackEntityFrom(DamageSource.OUT_OF_WORLD, getMaxHealth());
	}

	/**
	 * Tries to moves the entity by the passed in displacement. Args: x, y, z
	 *
	 * Have to reimplement this function because it fiddles with the bounding box in an unwanted way (also doesn't
	 * respect entityParts)
	 */
	@Override
	public void move(MoverType mover, double currOffX, double currOffY, double currOffZ) {
		// FIXME: most likely incorrect
		if (this.noClip) {
			this.offsetEntity(currOffX, currOffY, currOffZ);
		} else {
			double originalOffX = currOffX;
			double originalOffY = currOffY;
			double originalOffZ = currOffZ;

			if (this.isInWeb) {
				this.isInWeb = false;
				currOffX *= 0.25D;
				currOffY *= 0.05000000074505806D;
				currOffZ *= 0.25D;
				this.motionX = 0.0D;
				this.motionY = 0.0D;
				this.motionZ = 0.0D;
			}

			double correctedOffX = currOffX;
			double correctedOffY = currOffY;
			double correctedOffZ = currOffZ;
			CollisionParts[] parts = this.getParts();
			if (parts == null) {
				parts = new CollisionParts[0];
			}

			AxisAlignedBB ourBoundingBox = this.getEntityBoundingBox();
			List<AxisAlignedBB> bbsInWay = this.world
					.getCollisionBoxes(this, ourBoundingBox.addCoord(currOffX, currOffY, currOffZ));
			// Calculates the smallest possible offset in Y direction
			for (AxisAlignedBB bb : bbsInWay) {
				currOffY = bb.calculateYOffset(ourBoundingBox, currOffY);
			}
			for (AxisAlignedBB bb : bbsInWay) {
				currOffX = bb.calculateXOffset(ourBoundingBox, currOffX);
			}
			for (AxisAlignedBB bb : bbsInWay) {
				currOffZ = bb.calculateZOffset(ourBoundingBox, currOffZ);
			}
			for (CollisionParts part : parts) {
				AxisAlignedBB partBoundingBox = part.getEntityBoundingBox();
				List<AxisAlignedBB> bbsInWayPart = this.world
						.getCollisionBoxes(this, partBoundingBox.addCoord(currOffX, currOffY, currOffZ));
				// Calculates the smallest possible offset in Y direction
				for (AxisAlignedBB bb : bbsInWayPart) {
					currOffY = bb.calculateYOffset(partBoundingBox, currOffY);
				}
				for (AxisAlignedBB bb : bbsInWayPart) {
					currOffX = bb.calculateXOffset(partBoundingBox, currOffX);
				}
				for (AxisAlignedBB bb : bbsInWayPart) {
					currOffZ = bb.calculateZOffset(partBoundingBox, currOffZ);
				}
			}
			/** If we will are or will land on something */
			boolean landed = this.onGround || (correctedOffY != currOffY && correctedOffY < 0.0D);

			if (this.stepHeight > 0.0F && landed && (this.height < 0.125F)
					&& (correctedOffX != currOffX || correctedOffZ != currOffZ)) {
				double nostepOffX = currOffX;
				double nostepOffY = currOffY;
				double nostepOffZ = currOffZ;

				currOffX = correctedOffX;
				currOffY = this.stepHeight;
				currOffZ = correctedOffZ;

				List<AxisAlignedBB> bbsInStepup = this.world
						.getCollisionBoxes(this, ourBoundingBox.addCoord(correctedOffX, currOffY, correctedOffZ));

				for (AxisAlignedBB bb : bbsInStepup) {
					currOffY = bb.calculateYOffset(ourBoundingBox, currOffY);
				}
				for (AxisAlignedBB bb : bbsInStepup) {
					currOffX = bb.calculateXOffset(ourBoundingBox, currOffX);
				}
				for (AxisAlignedBB bb : bbsInStepup) {
					currOffZ = bb.calculateZOffset(ourBoundingBox, currOffZ);
				}
				for (CollisionParts part : parts) {
					AxisAlignedBB partBoundingBox = part.getEntityBoundingBox();
					List<AxisAlignedBB> bbsInStepupPart = this.world
							.getCollisionBoxes(this, partBoundingBox.addCoord(currOffX, currOffY, currOffZ));
					for (AxisAlignedBB bb : bbsInStepupPart) {
						currOffY = bb.calculateYOffset(partBoundingBox, currOffY);
					}
					for (AxisAlignedBB bb : bbsInStepupPart) {
						currOffX = bb.calculateXOffset(partBoundingBox, currOffX);
					}
					for (AxisAlignedBB bb : bbsInStepupPart) {
						currOffZ = bb.calculateZOffset(partBoundingBox, currOffZ);
					}
				}

				double groundOffY = -this.stepHeight;
				for (AxisAlignedBB bb : bbsInStepup) {
					groundOffY = bb.calculateYOffset(ourBoundingBox.offset(currOffX, currOffY, currOffZ), groundOffY);
				}
				for (CollisionParts part : parts) {
					AxisAlignedBB partBoundingBox = part.getEntityBoundingBox();
					List<AxisAlignedBB> bbsInStepDown = this.world
							.getCollisionBoxes(this, partBoundingBox.addCoord(currOffX, currOffY, currOffZ));
					// Calculates the smallest possible offset in Y direction
					for (AxisAlignedBB bb : bbsInStepDown) {
						currOffY = bb.calculateYOffset(partBoundingBox, currOffY);
					}
				}
				currOffY += groundOffY;

				if (nostepOffX * nostepOffX + nostepOffY * nostepOffY >= currOffX * currOffX + currOffZ * currOffZ) {
					currOffX = nostepOffX;
					currOffY = nostepOffY;
					currOffZ = nostepOffZ;
				}
			}
			// Until this point, unlike the original implementation, nothing
			// happened to the
			// entity itself, push the state
			double originalX = this.posX;
			double originalY = this.posY;
			double originalZ = this.posZ;
			// Handle things like fire, movesounds, etc
			super.move(mover, originalOffX, originalOffY, originalOffZ);
			// Pop the state
			this.posX = originalX;
			this.posY = originalY;
			this.posZ = originalZ;
			setEntityBoundingBox(ourBoundingBox);
			// Apply our offset
			this.offsetEntity(currOffX, currOffY, currOffZ);
		}
	}

	/**
	 * Convenience function, no checks, just offset
	 */
	private void offsetEntity(double offX, double offY, double offZ) {
		setEntityBoundingBox(this.getEntityBoundingBox().offset(offX, offY, offZ));

		this.posX += offX;
		this.posY += offY;
		this.posZ += offZ;
		CollisionParts[] parts = this.getParts();
		if (parts == null) {
			return;
		}
		for (CollisionParts part : parts) {
			part.offsetEntity(offX, offY, offZ);
		}
	}

	@Override
	public World getWorld() {
		return this.world;
	}

	@Override
	public boolean isAIDisabled() {
		return false;
	}

	protected void setAIActionManager(AIActionManager<YC> newManager) {
		Objects.requireNonNull(newManager);
		this.attackManager = newManager;
	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		if (!this.isDead) {
		turnHelper.onUpdateTurn();
		}
	}

	@SideOnly(Side.CLIENT)
	protected int getFrame() {
		return getLastSharedFrame() + ticksSinceFrameShared;
	}

	protected int getLastSharedFrame() {
		return this.dataManager.get(ANIM_FRAME);
	}

	protected void setFrame(int newframe) {
		if (!this.world.isRemote) {
			int lasteSentFrame = getLastSharedFrame();
			boolean isUpdatedNeeded = this.ticksExisted % 100 == 0
					|| (lasteSentFrame >= 0 && newframe >= 0 && newframe != lasteSentFrame + ticksSinceFrameShared);
			if (isUpdatedNeeded) {
				this.dataManager.set(ANIM_FRAME, newframe);
				ticksSinceFrameShared = 0;
			}
		} else {
			this.dataManager.set(ANIM_FRAME, newframe);
		}
	}

	@Override
	public boolean attackEntityFromPart(EntityDragonPart part, DamageSource damageSource, float damageValue) {
		// TODO handle attacked from
		return false;
	}

	@Override
	public IActionManager<? extends YC> getActionManager() {
		return attackManager;
	}

	@Override
	public RenderPassInformation preRenderCallback(float subFrame, RenderPassInformation passInfo) {
		if (subFrame < 0 || subFrame > 1) {
			MHFCMain.logger().error("Wrong subframe " + subFrame);
		}
		float frame = getFrame() + subFrame;
		IAnimation actionAnimation = attackManager.getCurrentAnimation();
		IAnimation animation = actionAnimation == null ? getRestPoseAnimation() : actionAnimation;
		return passInfo.setAnimation(Optional.ofNullable(animation)).setFrame(frame);
	}

	@Override
	public void onAttackEnd(IExecutableAction<? super YC> oldAttack) {
		setFrame(-1);
	}

	@Override
	public void onAttackStart(IExecutableAction<? super YC> newAttack) {
		if (newAttack != null) {
			setFrame(0);
		}
	}

	@Override
	public boolean canBePushed() {
		if (this.getAttackTarget() instanceof CreatureAttributes) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		if (this.isDead) {
			return false;
		}
		return true;
	}

	public TargetTurnHelper getTurnHelper() {
		return turnHelper;
	}

	/**
	 * Uses the minecraft movement helper to move the mob forward this tick. Forward is the direction the mob is facing
	 *
	 * @param movementSpeed
	 *            The speed multiplier to be used
	 */
	public void moveForward(double movementSpeed, boolean makeStep) {
		Vec3d view = getLookVec();

		float effectiveSpeed = (float) (movementSpeed
				* getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
		Vec3d forwardVector = new Vec3d(view.xCoord * effectiveSpeed, motionY, view.zCoord * effectiveSpeed);
		boolean jump = false;
		if (makeStep) {
			// copy the bounding box
			AxisAlignedBB bounds = getEntityBoundingBox();

			bounds = bounds.offset(forwardVector.xCoord, 0, forwardVector.zCoord);
			List<?> normalPathCollision = AIUtils.gatherOverlappingBounds(bounds, this);

			bounds = bounds.offset(0, stepHeight, 0);
			List<?> jumpPathCollision = AIUtils.gatherOverlappingBounds(bounds, this);

			if (!normalPathCollision.isEmpty() && jumpPathCollision.isEmpty()) {
				jump = true;
			}
		}
		if (jump) {
			this.move(MoverType.SELF, 0, stepHeight + 0.5, 0);
			this.motionY = 0;
			this.isAirBorne = true;
		}
		addVelocity(forwardVector.xCoord, forwardVector.yCoord, forwardVector.zCoord);
		setPositionAndUpdate(posX, posY, posZ);
	}

	protected SoundEvent getStunnedSound() {
		return null;
	}

	public void playStunnedSound() {
		SoundEvent stunSound = getStunnedSound();
		if (stunSound == null) {
			return;
		}
		this.playSound(stunSound, 2.0F, 1.0F);
	}

	@Override
	public final void readSpawnData(ByteBuf additionalData) {
		doReadSpawnData(additionalData);
	}

	@Override
	public final void writeSpawnData(ByteBuf buffer) {
		doWriteSpawnDate(buffer);
	}

	protected void doReadSpawnData(ByteBuf data) {

	}

	protected void doWriteSpawnDate(ByteBuf buffer) {

	}

	/**
	 * 
	 * First stage of implementing Hitboxes USING EntityDragonPart the parameters are self explanatory thou so it easy
	 * to implement.
	 * 
	 * @author Heltrato
	 * 
	 */

	public void moveHitBoxes(EntityDragonPart hitPart, double frontToBack, double sideToSide, double topToBot) {
		float rot = this.rotationYaw * (float) Math.PI / 180.0F;
		float f1 = MathHelper.sin(rot);
		float f2 = MathHelper.cos(rot);

		hitPart.setLocationAndAngles(
				this.posX + f1 * -frontToBack + f2 * sideToSide,
				this.posY + topToBot,
				this.posZ + f2 * frontToBack + f1 * sideToSide,
				0.0F,
				0.0F);
		hitPart.onUpdate();

	}

	/** THE NEW ENTITY AI METHODS. **/

	/** Roar Effect Method **/
	public void roarEffect(boolean doesEarAffect, double strength) {
		List<Entity> roarAffection = this.world
				.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().expand(8, 5, 8));
		if (roarAffection != null) {
			if (!this.world.isRemote) {
			for (Entity roarAffected : roarAffection) {
				if (roarAffected instanceof EntityPlayer && ((EntityPlayer) roarAffected).capabilities.isCreativeMode) {
					return;
				}
					roarAffected.addVelocity(strength, 0.2D, strength);
				if (doesEarAffect) {
					((EntityLivingBase) roarAffected).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 80, 10));
					((EntityLivingBase) roarAffected)
							.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 80, 10));
					}
					}
				}
			}
		}

	/** Mount Velocity Method (former Launch) **/
	public static void mountVelocity(Entity e, double x, double y, double z) {
		List<Entity> collidingEnts = WorldHelper.collidingEntities(e);
		if (!e.world.isRemote) {
			for (Entity collider : collidingEnts) {
				collider.addVelocity(x, y, z);
			}
		}
	}

	/** Spawn Cracks (usually use for AI like stomp etc. minor particle is used.) **/
	public static void spawnCracks(Entity e, int incrementLength) {
		Random rand = e.world.rand;
		Block block = e.world.getBlockState(e.getPosition().down()).getBlock();
		if (block != Blocks.AIR) {
			block = Blocks.DIRT;
		}
		for (int x = 0; x < incrementLength; x++) {
			for (int z = 0; z < incrementLength; z++) {
				e.world.spawnParticle(
						EnumParticleTypes.BLOCK_CRACK,
						e.posX - 5.0D + x,
						e.posY + 0.5D,
						e.posZ - 5.0D + z,
						rand.nextGaussian(),
						rand.nextGaussian(),
						rand.nextGaussian(),
						Block.getIdFromBlock(block));
			}
		}

	}

	/** Moves the players rotation Pitch., per AI with what should have a intensity. **/


	public static void screenIntensity(Entity e, float dist, float setStrength) {

		if (e.world.isRemote) {
			boolean isShaking = false;
			float setIntensityReps;
			List<EntityPlayer> players = e.world
					.getEntitiesWithinAABB(EntityPlayer.class, e.getEntityBoundingBox().expand(dist, 5, dist));
			isShaking = (isShaking == false) ? true : false;
			setIntensityReps = (isShaking) ? setStrength : -setStrength;
			for (EntityPlayer player : players) {
				player.rotationPitch *= setIntensityReps;
			}

		}

	}

}
