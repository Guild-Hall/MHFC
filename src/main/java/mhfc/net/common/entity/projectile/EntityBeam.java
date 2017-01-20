package mhfc.net.common.entity.projectile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityBeam extends Entity {
	private static final DataParameter<Float> YAW = EntityDataManager
			.createKey(EntityBeam.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> PITCH = EntityDataManager
			.createKey(EntityBeam.class, DataSerializers.FLOAT);
	private static final DataParameter<Integer> DURATION = EntityDataManager
			.createKey(EntityBeam.class, DataSerializers.VARINT);

	private static final DataParameter<Integer> CASTER_ID = EntityDataManager
			.createKey(EntityBeam.class, DataSerializers.VARINT);

	private final double beamRadius = 20;
	public double endPosX, endPosY, endPosZ;
	public double collidePosX, collidePosY, collidePosZ;

	public boolean on = true;
	public EnumFacing blockSide = null;

	public EntityBeam(World worldObj) {
		super(worldObj);
		setSize(0.1F, 0.1F);
		ignoreFrustumCheck = true;
	}

	public EntityBeam(
			World worldObj,
			EntityLivingBase caster,
			double x,
			double y,
			double z,
			float yaw,
			float pitch,
			int duration) {
		this(worldObj);
		setCaster(caster);
		updateFromCaster();
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

	@Override
	protected void entityInit() {
		this.dataManager.register(YAW, 0f);
		this.dataManager.register(PITCH, 0f);
		this.dataManager.register(DURATION, 0);
		this.dataManager.register(CASTER_ID, -1);
	}

	public double getYaw() {
		return (double) this.dataManager.get(YAW);
	}

	protected void setYaw(float yaw) {
		this.dataManager.set(YAW, yaw);
	}

	public double getPitch() {
		return (double) this.dataManager.get(PITCH);
	}

	protected void setPitch(float pitch) {
		this.dataManager.set(PITCH, pitch);
	}

	public int getDuration() {
		return this.dataManager.get(DURATION);
	}

	protected int decDuration() {
		int duration = getDuration();
		duration--;
		this.dataManager.set(DURATION, duration);
		return duration;
	}

	public EntityLivingBase getCaster() {
		int casterId = dataManager.get(CASTER_ID);
		if (casterId == -1) {
			return null;
		}
		return EntityLivingBase.class.cast(world.getEntityByID(casterId));
	}

	protected void setCaster(EntityLivingBase entity) {
		int casterId = entity == null ? -1 : entity.getEntityId();
		dataManager.set(CASTER_ID, casterId);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {

	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		updateFromCaster();
		reCalculateEndPos();

		if (!on && decDuration() == 0) {
			this.setDead();
		}
	}

	private void reCalculateEndPos() {
		endPosX = posX + beamRadius * Math.cos(getYaw()) * Math.cos(getPitch());
		endPosZ = posZ + beamRadius * Math.sin(getYaw()) * Math.cos(getPitch());
		endPosY = posY + beamRadius * Math.sin(getPitch());
	}

	private void updateFromCaster() {
		EntityLivingBase beamCaster = getCaster();
		if (beamCaster == null) {
			return;
		}
		this.setYaw((float) ((beamCaster.rotationYawHead + 90) * Math.PI / 180));
		this.setPitch((float) (-beamCaster.rotationPitch * Math.PI / 180));
		this.setPosition(beamCaster.posX, beamCaster.posY + 1.2f, beamCaster.posZ);
	}

	public TargetHit raytraceEntities(
			World world,
			Vec3d from,
			Vec3d to,
			boolean stopOnLiquid,
			boolean ignoreBlockWithoutBoundingBox,
			boolean returnLastUncollidableBlock) {
		TargetHit result = new TargetHit();
		result.setBlockTrace(
				world.rayTraceBlocks(
						from,
						to,
						stopOnLiquid,
						ignoreBlockWithoutBoundingBox,
						returnLastUncollidableBlock));
		if (result.rayTraceBlocks != null) {
			collidePosX = result.rayTraceBlocks.hitVec.xCoord;
			collidePosY = result.rayTraceBlocks.hitVec.yCoord;
			collidePosZ = result.rayTraceBlocks.hitVec.zCoord;
			blockSide = result.rayTraceBlocks.sideHit;
		} else {
			collidePosX = endPosX;
			collidePosY = endPosY;
			collidePosZ = endPosZ;
			blockSide = null;
		}
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(
				EntityLivingBase.class,
				new AxisAlignedBB(posX, posY, posZ, collidePosX, collidePosY, collidePosZ).expand(1, 1, 1));
		for (EntityLivingBase entity : entities) {
			if (entity == getCaster()) {
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

	public static class TargetHit {
		// assert rayTraceBlocks.type == BLOCK
		private RayTraceResult rayTraceBlocks;

		private List<EntityLivingBase> entities = new ArrayList<>();

		public RayTraceResult getBlockHit() {
			return rayTraceBlocks;
		}

		public void setBlockTrace(RayTraceResult blockHit) {
			this.rayTraceBlocks = blockHit;
		}

		public void addEntityHit(EntityLivingBase entity) {
			entities.add(entity);
		}
	}

}
