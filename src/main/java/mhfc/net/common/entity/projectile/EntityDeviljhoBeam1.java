package mhfc.net.common.entity.projectile;

import java.util.ArrayList;
import java.util.List;

import mhfc.net.common.util.math.TimerTick;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityDeviljhoBeam1 extends Entity {
	private final double RADIUS = 20;
	public EntityLivingBase caster;
	public double endPosX, endPosY, endPosZ;
	public double collidePosX, collidePosY, collidePosZ;
	public TimerTick appear = new TimerTick(3);
	public boolean on = true;

	public EnumFacing blockSide = null;

	private static final DataParameter<Float> YAW = EntityDataManager
			.createKey(EntityDeviljhoBeam1.class, DataSerializers.FLOAT);

	private static final DataParameter<Float> PITCH = EntityDataManager
			.createKey(EntityDeviljhoBeam1.class, DataSerializers.FLOAT);

	private static final DataParameter<Integer> DURATION = EntityDataManager
			.createKey(EntityDeviljhoBeam1.class, DataSerializers.VARINT);

	private static final DataParameter<Boolean> HAS_PLAYER = EntityDataManager
			.createKey(EntityDeviljhoBeam1.class, DataSerializers.BOOLEAN);

	private static final DataParameter<Integer> CASTER = EntityDataManager
			.createKey(EntityDeviljhoBeam1.class, DataSerializers.VARINT);

	public EntityDeviljhoBeam1(World world) {
		super(world);
		setSize(0.1F, 0.1F);
		ignoreFrustumCheck = true;
	}

	public EntityDeviljhoBeam1(
			World world,
			EntityLivingBase caster,
			double x,
			double y,
			double z,
			float yaw,
			float pitch,
			int duration) {
		this(world);
		this.caster = caster;
		setYaw(yaw);
		setPitch(pitch);
		setDuration(duration);
		setPosition(x, y, z);
		calculateEndPos();
		if (!world.isRemote) {
			setCasterID(caster.getEntityId());
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (ticksExisted == 1 && world.isRemote) {
			caster = (EntityLivingBase) world.getEntityByID(getCasterID());
		}
		if (!world.isRemote && getHasPlayer()) {
			updateWithPlayer();
		}

		if (!on && appear.getTimer() == 0) {
			setDead();
		}
		if (on && ticksExisted > 20) {
			appear.increaseTickTimer();
		} else {
			appear.decreaseTimer();
		}

		if (world.isRemote && ticksExisted <= 10) {
			int particleCount = 8;
			while (--particleCount != 0) {
				final double radius = 2f;
				final double yaw = rand.nextFloat() * 2 * Math.PI;
				final double pitch = rand.nextFloat() * 2 * Math.PI;
				final double ox = radius * Math.sin(yaw) * Math.sin(pitch);
				final double oy = radius * Math.cos(pitch);
				final double oz = radius * Math.cos(yaw) * Math.sin(pitch);
				double offsetX = -2 * Math.cos(getYaw());
				double offsetZ = -2 * Math.sin(getYaw());
				if (getHasPlayer()) {
					offsetX = offsetZ = 0;
				}

				world.spawnParticle(
						EnumParticleTypes.FLAME,
						posX + ox + offsetX,
						posY + oy + 0.3,
						posZ + oz + offsetZ,
						motionX,
						motionY,
						motionZ);
			}
		}
		if (ticksExisted > 20) {
			calculateEndPos();
			final List<EntityLivingBase> hit = raytraceEntities(
					world,
					new Vec3d(posX, posY, posZ),
					new Vec3d(endPosX, endPosY, endPosZ),
					false,
					true,
					true).entities;
			if (blockSide != null) {
				spawnExplosionParticles(2);
			}
			if (!world.isRemote) {
				for (final EntityLivingBase target : hit) {
					target.attackEntityFrom(DamageSource.ON_FIRE, 2f);
					target.attackEntityFrom(DamageSource.causeMobDamage(caster), 2f);
				}
			} else {
				if (ticksExisted - 15 < getDuration()) {
					int particleCount = 4;
					while (particleCount-- > 0) {
						final double radius = 1f;
						final double yaw = rand.nextFloat() * 2 * Math.PI;
						final double pitch = rand.nextFloat() * 2 * Math.PI;
						final double ox = radius * Math.sin(yaw) * Math.sin(pitch);
						final double oy = radius * Math.cos(pitch);
						final double oz = radius * Math.cos(yaw) * Math.sin(pitch);
						final double o2x = -1 * Math.cos(getYaw()) * Math.cos(getPitch());
						final double o2y = -1 * Math.sin(getPitch());
						final double o2z = -1 * Math.sin(getYaw()) * Math.cos(getPitch());
						world.spawnParticle(
								EnumParticleTypes.FLAME,
								posX + o2x + ox,
								posY + o2y + oy,
								posZ + o2z + oz,
								motionX,
								motionY,
								motionZ);
					}
					particleCount = 4;
					while (particleCount-- > 0) {
						@SuppressWarnings("unused")
						final double radius = 2f;
						final double yaw = rand.nextFloat() * 2 * Math.PI;
						final double pitch = rand.nextFloat() * 2 * Math.PI;
						Math.sin(yaw);
						Math.sin(pitch);
						Math.cos(pitch);
						Math.cos(yaw);
						Math.sin(pitch);
						final double o2x = -1 * Math.cos(getYaw()) * Math.cos(getPitch());
						final double o2y = -1 * Math.sin(getPitch());
						final double o2z = -1 * Math.sin(getYaw()) * Math.cos(getPitch());
						world.spawnParticle(
								EnumParticleTypes.FLAME,
								collidePosX + o2x,
								collidePosY + o2y,
								collidePosZ + o2z,
								motionX,
								motionY,
								motionZ);

					}
				}
			}
		}
		if (ticksExisted - 20 > getDuration()) {
			on = false;
		}
	}

	private void spawnExplosionParticles(int amount) {
		for (int i = 0; i < amount; i++) {
			final float velocity = 0.1F;
			final float yaw = (float) (rand.nextFloat() * 2 * Math.PI);
			final float motionY = rand.nextFloat() * 0.08F;
			final float motionX = velocity * MathHelper.cos(yaw);
			final float motionZ = velocity * MathHelper.sin(yaw);
			world.spawnParticle(
					EnumParticleTypes.FLAME,
					collidePosX,
					collidePosY + 0.1,
					collidePosZ,
					motionX,
					motionY,
					motionZ);
		}
		for (int i = 0; i < amount / 2; i++) {
			world.spawnParticle(EnumParticleTypes.LAVA, collidePosX, collidePosY + 0.1, collidePosZ, 0, 0, 0);
		}
	}

	@Override
	protected void entityInit() {
		getDataManager().register(YAW, 0F);
		getDataManager().register(PITCH, 0F);
		getDataManager().register(DURATION, 0);
		getDataManager().register(HAS_PLAYER, false);
		getDataManager().register(CASTER, -1);
	}

	public float getYaw() {
		return getDataManager().get(YAW);
	}

	public void setYaw(float yaw) {
		getDataManager().set(YAW, yaw);
	}

	public float getPitch() {
		return getDataManager().get(PITCH);
	}

	public void setPitch(float pitch) {
		getDataManager().set(PITCH, pitch);
	}

	public int getDuration() {
		return getDataManager().get(DURATION);
	}

	public void setDuration(int duration) {
		getDataManager().set(DURATION, duration);
	}

	public boolean getHasPlayer() {
		return getDataManager().get(HAS_PLAYER);
	}

	public void setHasPlayer(boolean player) {
		getDataManager().set(HAS_PLAYER, player);
	}

	public int getCasterID() {
		return getDataManager().get(CASTER);
	}

	public void setCasterID(int id) {
		getDataManager().set(CASTER, id);
	}

	@Override
	public boolean writeToNBTOptional(NBTTagCompound compound) {
		return false;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {}

	private void calculateEndPos() {
		endPosX = posX + RADIUS * Math.cos(getYaw()) * Math.cos(getPitch());
		endPosZ = posZ + RADIUS * Math.sin(getYaw()) * Math.cos(getPitch());
		endPosY = posY + RADIUS * Math.sin(getPitch());
	}

	public HitResult raytraceEntities(
			World world,
			Vec3d from,
			Vec3d to,
			boolean stopOnLiquid,
			boolean ignoreBlockWithoutBoundingBox,
			boolean returnLastUncollidableBlock) {
		final HitResult result = new HitResult();
		result.setBlockHit(
				world.rayTraceBlocks(
						new Vec3d(from.x, from.y, from.z),
						to,
						stopOnLiquid,
						ignoreBlockWithoutBoundingBox,
						returnLastUncollidableBlock));
		if (result.blockHit != null) {
			collidePosX = result.blockHit.hitVec.x;
			collidePosY = result.blockHit.hitVec.y;
			collidePosZ = result.blockHit.hitVec.z;
			blockSide = result.getBlockHit().sideHit;
		} else {
			collidePosX = endPosX;
			collidePosY = endPosY;
			collidePosZ = endPosZ;
			blockSide = null;
		}
		final List<EntityLivingBase> entities = world.getEntitiesWithinAABB(
				EntityLivingBase.class,
				new AxisAlignedBB(
						Math.min(posX, collidePosX),
						Math.min(posY, collidePosY),
						Math.min(posZ, collidePosZ),
						Math.max(posX, collidePosX),
						Math.max(posY, collidePosY),
						Math.max(posZ, collidePosZ)).expand(1, 1, 1));
		for (final EntityLivingBase entity : entities) {
			if (entity == caster) {
				continue;
			}
			final float pad = entity.getCollisionBorderSize() + 0.5f;
			final AxisAlignedBB aabb = entity.getEntityBoundingBox().expand(pad, pad, pad);
			final RayTraceResult hit = aabb.calculateIntercept(from, to);
			if (aabb.contains(from)) {
				result.addEntityHit(entity);
			} else if (hit != null) {
				result.addEntityHit(entity);
			}
		}
		return result;
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 1024;
	}

	private void updateWithPlayer() {
		setYaw((float) ((caster.rotationYawHead + 90) * Math.PI / 180));
		setPitch((float) (-caster.rotationPitch * Math.PI / 180));
		setPosition(caster.posX, caster.posY + 1.2f, caster.posZ);
	}

	public static class HitResult {
		private RayTraceResult blockHit;

		private List<EntityLivingBase> entities = new ArrayList<>();

		public RayTraceResult getBlockHit() {
			return blockHit;
		}

		public void setBlockHit(RayTraceResult blockHit) {
			this.blockHit = blockHit;
		}

		public void addEntityHit(EntityLivingBase entity) {
			entities.add(entity);
		}
	}
}
