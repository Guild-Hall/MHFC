package mhfc.net.common.entity.type;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;
import com.github.worldsender.mcanm.client.renderer.IAnimatedObject;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.IManagedActions;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.TargetTurnHelper;
import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.ai.manager.AIActionManager;
import mhfc.net.common.core.registry.MHFCPotionRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

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
public abstract class EntityMHFCBase<YC extends EntityMHFCBase<YC>> extends EntityCreature
		implements
		IEntityMultiPart,
		IAnimatedObject,
		IManagedActions<YC> {
	/**
	 * {@link #getDataWatcher()}
	 */
	protected static final int DATA_FRAME = 12;
	private final TargetTurnHelper turnHelper;
	private IActionManager<? extends YC> attackManager;

	private IExecutableAction<? super YC> deathAction;
	private IExecutableAction<? super YC> inWaterAction;
	private IExecutableAction<? super YC> stunAction;
	
	public int set_Armor_Value = 19;

	public boolean FREEZE; // trying to implement this to disable all AI's for the monster temporality.

	protected boolean hasDied;
	// @see deathTime. DeathTime has the random by-effect of rotating corpses...
	protected int deathTicks;

	public EntityMHFCBase(World world) {
		super(world);
		turnHelper = new TargetTurnHelper(this);
		attackManager = Objects.requireNonNull(constructActionManager());
		ignoreFrustumCheck = true;
		hasDied = false;
	}

	protected <A extends IExecutableAction<? super YC>> A setDeathAction(A action) {
		this.deathAction = action;
		return action;
	}
	
	protected <A extends IExecutableAction<? super YC>> A setInWaterAction(A action){
		inWaterAction = action;
		return action;
	}
	
	protected <A extends IExecutableAction<? super YC>> A setStunAction(A action){
		stunAction = action;
		return action;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		setFrame(this.attackManager.getCurrentFrame());
		if (this.attackManager.continueExecuting()) {
			this.attackManager.updateTask();
		}

		if (this.isPotionActive(MHFCPotionRegistry.getRegistry().stun)) {
			getActionManager().switchToAction(stunAction);
		}
		if (this.isInWater()){
			getActionManager().switchToAction(inWaterAction);
		}

	}

	public abstract IActionManager<YC> constructActionManager();

	/**
	 * Specialize the return type to a {@link EntityMHFCPart}
	 */
	@Override
	public abstract EntityMHFCPart[] getParts();

	/**
	 * Drops the given item near this entity in the world. Utility method
	 *
	 * @param item
	 *            the item to drop
	 * @param count
	 *            the stack size
	 */
	public void dropItemRand(Item item, int count) {
		dropItemRand(new ItemStack(item, count, 0));
	}
	
	
	
	protected void specificArmorValue(int value){
		value = set_Armor_Value;
	}

	// Armor has been increase by 3% from 17
	@Override
	public int getTotalArmorValue() {
		return set_Armor_Value;
	}
	
	

	@Override
	protected void onDeathUpdate() {
		if (!hasDied) {
			onDeath();
			hasDied = true;
		}
		spawnDeadParticles();
		deathTicks++;
		if (deathTicks >= AIGeneralDeath.deathLingeringTicks || deathAction == null) {
			onDespawn();
			setDead();
		}
	}

	protected void spawnDeadParticles() {
		float timed = (float) deathTicks / AIGeneralDeath.deathLingeringTicks;
		timed = Math.max(0, timed - 0.1f) / 0.9f;
		int particleCount = (int) (20 * Math.pow(timed, 4));
		for (int i = 0; i < particleCount; i++) {
			double randX = rand.nextDouble(), randZ = rand.nextDouble(), randY = rand.nextDouble();
			randX = randX * (boundingBox.maxX - boundingBox.minX) + boundingBox.minX;
			randZ = randZ * (boundingBox.maxZ - boundingBox.minZ) + boundingBox.minZ;
			randY = Math.pow(randY, 3) * (boundingBox.maxY - boundingBox.minY) + boundingBox.minY;
			double velX = this.rand.nextGaussian() * 0.01D;
			double velY = Math.abs(this.rand.nextGaussian() * 0.7D);
			double velZ = this.rand.nextGaussian() * 0.01D;
			worldObj.spawnParticle("depthsuspend", randX, randY, randZ, velX, velY, velZ);
		}
	}

	protected void onDespawn() {
		boolean killedByPlayer = true;
		int specialLuck = 100;
		if (!worldObj.isRemote) {
			dropFewItems(killedByPlayer, specialLuck);
		}
		for (int i = 0; i < 20; i++) {
			double randX = rand.nextDouble(), randZ = rand.nextDouble(), randY = rand.nextGaussian();
			randX = randX * (boundingBox.maxX - boundingBox.minX) + boundingBox.minX;
			randZ = randZ * (boundingBox.maxZ - boundingBox.minZ) + boundingBox.minZ;
			randY += posY;
			double velX = this.rand.nextGaussian() * 0.01D;
			double velY = Math.abs(this.rand.nextGaussian() * 0.2D);
			double velZ = this.rand.nextGaussian() * 0.01D;
			worldObj.spawnParticle("cloud", randX, randY, randZ, velX, velY, velZ);
		}
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.3D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(60D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}
	
	protected void onDeath() {
		if (deathAction != null) {
			getActionManager().switchToAction(deathAction);
		}
	}

	@Override
	protected boolean func_146066_aG() {
		// Don't do normal drops
		return false;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(DATA_FRAME, Integer.valueOf(-1));
	}

	public void dropItemRand(ItemStack stack) {
		Random rand = worldObj.rand;
		EntityItem entityItem = new EntityItem(
				this.worldObj,
				posX + rand.nextInt(10) - 5,
				posY + 1.0D,
				posZ + rand.nextInt(10) - 5,
				stack);
		worldObj.spawnEntityInWorld(entityItem);
	}

	public double healthbaseHP(double defaultHP) {
		// This will be a quest data base.
		return defaultHP;
	}

	// FIXME: will update location, rotation set algs in 1.8, bc huge changes
	// inc
	/**
	 * Sets the location AND angles of this entity. Custom implementation because we need to be notified when the angles
	 * of the entity change.<br>
	 * This is only called once per update-tick
	 */

	// @Override
	// public void setLocationAndAngles(double posX, double posY, double posZ,
	// float yaw, float pitch) {
	// super.setLocationAndAngles(posX, posY, posZ, yaw, pitch);
	// this.setRotation(yaw, pitch);
	// super.setPosition(posX, posY, posZ);
	// this.setRotation(yaw, pitch);
	// }
	/**
	 * This is a custom implementation of the movement algorithm. It just moves the bounding box by the difference
	 * between the current and next position. No height or width, just offset the whole box.
	 */
	// @Override
	// public void setPosition(double newPosX, double newPosY, double newPosZ) {
	// hm, somehow keep the BB the same size
	// this.posX = newPosX;
	// this.posY = newPosY;
	// this.posZ = newPosZ;
	// double diffX = newPosX - this.posX;
	// double diffY = newPosY - this.posY;
	// double diffZ = newPosZ - this.posZ;
	// this.boundingBox.offset(diffX, diffY, diffZ);
	// this.offsetEntity(diffX, diffY, diffZ);
	// }
	// @Override
	// protected void setRotation(float newYaw, float newPitch) {
	// float diffYawDeg = newYaw - this.rotationYaw;
	// double diffYawRad = diffYawDeg / 180D;
	// this.rotationYaw = newYaw % 360.0F;
	// this.rotationPitch = newPitch % 360.0F;
	// EntityMHFCPart[] parts = this.getParts();
	// if (parts == null)
	// return;
	// for (EntityMHFCPart part : parts) {
	// double offXPart = part.posX - this.posX;
	// double offZPart = part.posZ - this.posZ;
	// part.posX = this.posX
	// + (Math.cos(diffYawRad) * offXPart - Math.sin(diffYawRad)
	// * offZPart);
	// part.posY = this.posY
	// + (Math.sin(diffYawRad) * offXPart + Math.cos(diffYawRad)
	// * offZPart);
	// }
	// }

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected void kill() {
		this.attackEntityFrom(DamageSource.outOfWorld, getMaxHealth());
	}

	/**
	 * Tries to moves the entity by the passed in displacement. Args: x, y, z
	 *
	 * Have to reimplement this function because it fiddles with the bounding box in an unwanted way (also doesn't
	 * respect entityParts)
	 */
	@Override
	public void moveEntity(double currOffX, double currOffY, double currOffZ) {
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
			EntityMHFCPart[] parts = this.getParts();
			if (parts == null) {
				parts = new EntityMHFCPart[0];
			}

			List<AxisAlignedBB> bbsInWay = this.worldObj
					.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(currOffX, currOffY, currOffZ));
			// Calculates the smallest possible offset in Y direction
			for (AxisAlignedBB bb : bbsInWay) {
				currOffY = bb.calculateYOffset(this.boundingBox, currOffY);
			}
			for (AxisAlignedBB bb : bbsInWay) {
				currOffX = bb.calculateXOffset(this.boundingBox, currOffX);
			}
			for (AxisAlignedBB bb : bbsInWay) {
				currOffZ = bb.calculateZOffset(this.boundingBox, currOffZ);
			}
			for (EntityMHFCPart part : parts) {
				List<AxisAlignedBB> bbsInWayPart = this.worldObj
						.getCollidingBoundingBoxes(this, part.boundingBox.addCoord(currOffX, currOffY, currOffZ));
				// Calculates the smallest possible offset in Y direction
				for (AxisAlignedBB bb : bbsInWayPart) {
					currOffY = bb.calculateYOffset(part.boundingBox, currOffY);
				}
				for (AxisAlignedBB bb : bbsInWayPart) {
					currOffX = bb.calculateXOffset(part.boundingBox, currOffX);
				}
				for (AxisAlignedBB bb : bbsInWayPart) {
					currOffZ = bb.calculateZOffset(part.boundingBox, currOffZ);
				}
			}
			/** If we will are or will land on something */
			boolean landed = this.onGround || (correctedOffY != currOffY && correctedOffY < 0.0D);

			if (this.stepHeight > 0.0F && landed && (this.ySize < 0.125F)
					&& (correctedOffX != currOffX || correctedOffZ != currOffZ)) {
				double nostepOffX = currOffX;
				double nostepOffY = currOffY;
				double nostepOffZ = currOffZ;

				currOffX = correctedOffX;
				currOffY = this.stepHeight;
				currOffZ = correctedOffZ;

				List<AxisAlignedBB> bbsInStepup = this.worldObj.getCollidingBoundingBoxes(
						this,
						this.boundingBox.addCoord(correctedOffX, currOffY, correctedOffZ));

				for (AxisAlignedBB bb : bbsInStepup) {
					currOffY = bb.calculateYOffset(this.boundingBox, currOffY);
				}
				for (AxisAlignedBB bb : bbsInStepup) {
					currOffX = bb.calculateXOffset(this.boundingBox, currOffX);
				}
				for (AxisAlignedBB bb : bbsInStepup) {
					currOffZ = bb.calculateZOffset(this.boundingBox, currOffZ);
				}
				for (EntityMHFCPart part : parts) {
					List<AxisAlignedBB> bbsInStepupPart = this.worldObj
							.getCollidingBoundingBoxes(this, part.boundingBox.addCoord(currOffX, currOffY, currOffZ));
					for (AxisAlignedBB bb : bbsInStepupPart) {
						currOffY = bb.calculateYOffset(part.boundingBox, currOffY);
					}
					for (AxisAlignedBB bb : bbsInStepupPart) {
						currOffX = bb.calculateXOffset(part.boundingBox, currOffX);
					}
					for (AxisAlignedBB bb : bbsInStepupPart) {
						currOffZ = bb.calculateZOffset(part.boundingBox, currOffZ);
					}
				}

				double groundOffY = (-this.stepHeight);
				for (AxisAlignedBB bb : bbsInStepup) {
					groundOffY = bb.calculateYOffset(
							this.boundingBox.getOffsetBoundingBox(currOffX, currOffY, currOffZ),
							groundOffY);
				}
				for (EntityMHFCPart part : parts) {
					List<AxisAlignedBB> bbsInStepDown = this.worldObj
							.getCollidingBoundingBoxes(this, part.boundingBox.addCoord(currOffX, currOffY, currOffZ));
					// Calculates the smallest possible offset in Y direction
					for (AxisAlignedBB bb : bbsInStepDown) {
						currOffY = bb.calculateYOffset(part.boundingBox, currOffY);
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
			AxisAlignedBB originalBB = this.boundingBox.copy();
			// Handle things like fire, movesounds, etc
			super.moveEntity(originalOffX, originalOffY, originalOffZ);
			// Pop the state
			this.posX = originalX;
			this.posY = originalY;
			this.posZ = originalZ;
			this.boundingBox.setBB(originalBB);
			// Apply our offset
			this.offsetEntity(currOffX, currOffY, currOffZ);
		}
	}

	/**
	 * Convenience function, no checks, just offset
	 */
	private void offsetEntity(double offX, double offY, double offZ) {
		this.boundingBox.offset(offX, offY, offZ);

		this.posX += offX;
		this.posY += offY;
		this.posZ += offZ;
		EntityMHFCPart[] parts = this.getParts();
		if (parts == null) {
			return;
		}
		for (EntityMHFCPart part : parts) {
			part.offsetEntity(offX, offY, offZ);
		}
	}

	/**
	 * Unknown purpose, worldObj is public
	 */
	@Override
	public World func_82194_d() {
		return this.worldObj;
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	protected void setAIActionManager(AIActionManager<YC> newManager) {
		Objects.requireNonNull(newManager);
		this.attackManager = newManager;
	}

	@Override
	protected void updateAITick() {
		super.updateAITick();
		turnHelper.onUpdateTurn();
	}

	public void setFrame(int newframe) {
		this.dataWatcher.updateObject(DATA_FRAME, Integer.valueOf(newframe));
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

	protected int getCurrentFrame() {
		// CLEANUP: send only keyframes to reduce server load, assume advanced
		return this.dataWatcher.getWatchableObjectInt(DATA_FRAME);
	}

	@Override
	public RenderPassInformation preRenderCallback(float subFrame, RenderPassInformation passInfo) {
		return passInfo.setAnimation(Optional.ofNullable(attackManager.getCurrentAnimation()))
				.setFrame(getCurrentFrame() + subFrame);
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
	public String getDeathSound() {
		return super.getDeathSound();
	}

	@Override
	public boolean canBePushed() {
		return false;
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
		Vec3 view = getLookVec();

		float effectiveSpeed = (float) (movementSpeed
				* getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
		Vec3 forwardVector = Vec3
				.createVectorHelper(view.xCoord * effectiveSpeed, motionY, view.zCoord * effectiveSpeed);
		boolean jump = false;
		if (makeStep) {
			// copy the bounding box
			AxisAlignedBB bounds = boundingBox.getOffsetBoundingBox(0, 0, 0);

			bounds.offset(forwardVector.xCoord, 0, forwardVector.zCoord);
			List<?> normalPathCollision = AIUtils.gatherOverlappingBounds(bounds, this);

			bounds.offset(0, stepHeight, 0);
			List<?> jumpPathCollision = AIUtils.gatherOverlappingBounds(bounds, this);

			if (!normalPathCollision.isEmpty() && jumpPathCollision.isEmpty()) {
				jump = true;
			}
		}
		if (jump) {
			this.moveEntity(0, stepHeight + 0.5, 0);
			this.motionY = 0;
			this.isAirBorne = true;
		}
		addVelocity(forwardVector.xCoord, forwardVector.yCoord, forwardVector.zCoord);
		setPositionAndUpdate(posX, posY, posZ);
	}
	
	public String stunSound;
	protected String setStunnedSound(String sound){
		return stunSound = sound;
	}
	
	public void playStunnedSound(){
		this.playSound(stunSound, 2.0F, 1.0F);
	}
}
