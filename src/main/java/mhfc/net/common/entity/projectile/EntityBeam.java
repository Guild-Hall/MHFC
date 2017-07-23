package mhfc.net.common.entity.projectile;

import java.util.ArrayList;
import java.util.List;

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

public class EntityBeam extends Entity {
	private final double RADIUS = 20;
	public EntityLivingBase caster;
	public double endPosX, endPosY, endPosZ;
	public double collidePosX, collidePosY, collidePosZ;

	public boolean on = true;

	public EnumFacing blockSide = null;

	private static final DataParameter<Float> YAW = EntityDataManager
			.createKey(EntityBeam.class, DataSerializers.FLOAT);

	private static final DataParameter<Float> PITCH = EntityDataManager
			.createKey(EntityBeam.class, DataSerializers.FLOAT);

	private static final DataParameter<Integer> DURATION = EntityDataManager
			.createKey(EntityBeam.class, DataSerializers.VARINT);

	private static final DataParameter<Boolean> HAS_PLAYER = EntityDataManager
			.createKey(EntityBeam.class, DataSerializers.BOOLEAN);

	private static final DataParameter<Integer> CASTER = EntityDataManager
			.createKey(EntityBeam.class, DataSerializers.VARINT);

	public EntityBeam(World world) {
		super(world);
		setSize(0.1F, 0.1F);
		ignoreFrustumCheck = true;
	}

	public EntityBeam(
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
		this.setYaw(yaw);
		this.setPitch(pitch);
		this.setDuration(duration);
		this.setPosition(x, y, z);
		this.calculateEndPos();
		if (!world.isRemote) {
			this.setCasterID(caster.getEntityId());
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (ticksExisted == 1 && world.isRemote) {
			caster = (EntityLivingBase) world.getEntityByID(getCasterID());
		}



		if (ticksExisted > 20) {
			this.calculateEndPos();
			List<EntityLivingBase> hit = raytraceEntities(
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
				for (EntityLivingBase target : hit) {
					target.attackEntityFrom(DamageSource.ON_FIRE, 2f);
					target.attackEntityFrom(DamageSource.causeMobDamage(caster), 2f);
				}
			} 
			}


	}

	private void spawnExplosionParticles(int amount) {
		for (int i = 0; i < amount; i++) {
			final float velocity = 0.1F;
			float yaw = (float) (rand.nextFloat() * 2 * Math.PI);
			float motionY = rand.nextFloat() * 0.08F;
			float motionX = velocity * MathHelper.cos(yaw);
			float motionZ = velocity * MathHelper.sin(yaw);
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
		HitResult result = new HitResult();
		result.setBlockHit(
				world.rayTraceBlocks(
						new Vec3d(from.xCoord, from.yCoord, from.zCoord),
						to,
						stopOnLiquid,
						ignoreBlockWithoutBoundingBox,
						returnLastUncollidableBlock));
		if (result.blockHit != null) {
			collidePosX = result.blockHit.hitVec.xCoord;
			collidePosY = result.blockHit.hitVec.yCoord;
			collidePosZ = result.blockHit.hitVec.zCoord;
			blockSide = result.getBlockHit().sideHit;
		} else {
			collidePosX = endPosX;
			collidePosY = endPosY;
			collidePosZ = endPosZ;
			blockSide = null;
		}
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(
				EntityLivingBase.class,
				new AxisAlignedBB(
						Math.min(posX, collidePosX),
						Math.min(posY, collidePosY),
						Math.min(posZ, collidePosZ),
						Math.max(posX, collidePosX),
						Math.max(posY, collidePosY),
						Math.max(posZ, collidePosZ)).expand(1, 1, 1));
		for (EntityLivingBase entity : entities) {
			if (entity == caster) {
				continue;
			}
			float pad = entity.getCollisionBorderSize() + 0.5f;
			AxisAlignedBB aabb = entity.getEntityBoundingBox().expand(pad, pad, pad);
			RayTraceResult hit = aabb.calculateIntercept(from, to);
			if (aabb.isVecInside(from)) {
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

	@SuppressWarnings("unused")
	private void updateWithPlayer() {
		this.setYaw((float) ((caster.rotationYawHead + 90) * Math.PI / 180));
		this.setPitch((float) (-caster.rotationPitch * Math.PI / 180));
		this.setPosition(caster.posX, caster.posY + 1.2f, caster.posZ);
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
