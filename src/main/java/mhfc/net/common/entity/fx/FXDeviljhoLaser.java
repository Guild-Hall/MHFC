package mhfc.net.common.entity.fx;

import java.util.List;

import mhfc.net.client.particle.EnumParticles;
import mhfc.net.client.particle.api.ParticleFactory;
import mhfc.net.client.particle.particles.Cloud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class FXDeviljhoLaser extends Entity {
	private static final int point_range = 20;
	private static final int ARC = 90;
	private static final int damageAtTick = 35;
	public EntityLivingBase caster;
	private static final DataParameter<Integer> CASTER = EntityDataManager
			.createKey(FXDeviljhoLaser.class, DataSerializers.VARINT);

	public FXDeviljhoLaser(World w) {
		super(w);
		this.setSize(0, 0);
	}

	public FXDeviljhoLaser(World w, EntityLivingBase l) {
		super(w);
		this.setSize(0, 0);
		if (!world.isRemote) {
			if (caster != null)
			this.setCasterID(caster.getEntityId());
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (caster instanceof EntityPlayer) {
			rotationYaw = ((EntityPlayer) caster).rotationYaw;
			rotationPitch = ((EntityPlayer) caster).rotationPitch;
			posX = ((EntityPlayer) caster).posX;
			posY = ((EntityPlayer) caster).posY + ((EntityPlayer) caster).eyeHeight - 0.5f;
			posZ = ((EntityPlayer) caster).posZ;
		}

		if (ticksExisted == 1) {
			caster = (EntityLivingBase) world.getEntityByID(getCasterID());
		}
		float yaw = (float) Math.toRadians(-rotationYaw);
		float pitch = (float) Math.toRadians(-rotationPitch);
		float spread = 0.25f;
		float speed = 0.56f;
		float xComp = (float) (Math.sin(yaw) * Math.cos(pitch));
		float yComp = (float) (Math.sin(pitch));
		float zComp = (float) (Math.cos(yaw) * Math.cos(pitch));
		if (world.isRemote) {
			if (ticksExisted % 8 == 0) {
				if (world.isRemote)
					EnumParticles.FLAKE
							.spawn(
									world,
									posX - 5,
									posY,
									posZ + 5,
									ParticleFactory.ParticleArgs.get().withData(
							yaw,
							-pitch,
							40,
							1f,
							1f,
							1f,
							1f,
											250f * spread,
							false,
							0.5f * xComp,
							0.5f * yComp,
							0.5f * zComp));
			}

			for (int i = 0; i < 10; i++) {
				double xSpeed = speed * 1f * xComp;
				double ySpeed = speed * 1f * yComp;
				double zSpeed = speed * 1f * zComp;
				EnumParticles.FLAKE.spawn(
						world,
						posX - 5,
						posY,
						posZ + 5,
						ParticleFactory.ParticleArgs.get().withData(xSpeed, ySpeed, zSpeed, 37d, 1d));
			}
			for (int i = 0; i < 5; i++) {
				double xSpeed = speed * xComp
						+ (spread * 0.7 * (rand.nextFloat() * 2 - 1) * (Math.sqrt(1 - xComp * xComp)));
				double ySpeed = speed * yComp
						+ (spread * 0.7 * (rand.nextFloat() * 5 - 1) * (Math.sqrt(1 - yComp * yComp)));
				double zSpeed = speed * zComp
						+ (spread * 0.7 * (rand.nextFloat() * 5 - 1) * (Math.sqrt(1 - zComp * zComp)));
				double value = rand.nextFloat() * 0.15f;
				EnumParticles.CLOUD.spawn(world, posX - 5, posY, posZ + 5, ParticleFactory.ParticleArgs.get().withData(
						xSpeed,
						ySpeed,
						zSpeed,
						0.75d + value,
						0.75d + value,
						3d,
						true,
						10d + rand.nextDouble() * 20d,
						40,
						Cloud.EnumCloudBehavior.CONSTANT));
			}
		}
		if (ticksExisted > 10)
			hitEntities();

		if (ticksExisted > 65 && !(caster instanceof EntityPlayer))
			setDead();
	}



	@Override
	protected void entityInit() {
		this.getDataManager().register(CASTER, -1);
	}

	/**
	 * 
	 * Usable tools.Will clean soon, gonna need a global utilities again -_-.
	 */

	public void hitEntities() {
		 List<EntityLivingBase> entitiesHit = getEntityLivingBaseNearby(point_range, point_range, point_range, point_range);
		float damage = damageAtTick;
	        for (EntityLivingBase entityHit : entitiesHit) {
	            if (entityHit == caster) continue;
	            float entityHitYaw = (float) ((Math.atan2(entityHit.posZ - posZ, entityHit.posX - posX) * (180 / Math.PI) - 90) % 360);
	            float entityAttackingYaw = rotationYaw % 360;
	            if (entityHitYaw < 0) {
	                entityHitYaw += 360;
	            }
	            if (entityAttackingYaw < 0) {
	                entityAttackingYaw += 360;
	            }
			float entityRelativeYaw = entityHitYaw - entityAttackingYaw;
			float xzDistance = (float) Math.sqrt(
					(entityHit.posZ - posZ) * (entityHit.posZ - posZ)
							+ (entityHit.posX - posX) * (entityHit.posX - posX));
			float eHitPitch = (float) ((Math.atan2((entityHit.posY - posY), xzDistance) * (180 / Math.PI)) % 360);
			float eAttackingPitch = -rotationPitch % 360;
			float entityRelativePitch = eHitPitch - eAttackingPitch;

			float entityHitDistance = (float) Math.sqrt(
					(entityHit.posZ - posZ) * (entityHit.posZ - posZ)
							+ (entityHit.posX - posX) * (entityHit.posX - posX)
							+ (entityHit.posY - posY) * (entityHit.posY - posY));

			boolean isOnSight = entityHitDistance <= point_range;
			boolean onSightYaw = (entityRelativeYaw <= ARC / 2 && entityRelativeYaw >= -ARC / 2)
					|| (entityRelativeYaw >= 360 - ARC / 2 || entityRelativeYaw <= -360 + ARC / 2);
			boolean onSightPitch = (entityRelativePitch <= ARC / 2 && entityRelativePitch >= -ARC / 2)
					|| (entityRelativePitch >= 360 - ARC / 2 || entityRelativePitch <= -360 + ARC / 2);

	            if (eHitPitch < 0) {
	                eHitPitch += 360;
	            }
	            if (eAttackingPitch < 0) {
	                eAttackingPitch += 360;
	            }

			if (isOnSight && onSightYaw && onSightPitch) {
				if (entityHit != null)
				entityHit.attackEntityFrom(DamageSource.causeMobDamage(caster), damage);
			}

		}
	}

	public List<EntityLivingBase> getEntityLivingBaseNearby(
			double distanceX,
			double distanceY,
			double distanceZ,
			double radius) {
		return getEntitiesNearby(EntityLivingBase.class, distanceX, distanceY, distanceZ, radius);
	}

	public <T extends Entity> List<T> getEntitiesNearby(
			Class<T> entityClass,
			double dX,
			double dY,
			double dZ,
			double r) {
		return world.getEntitiesWithinAABB(
				entityClass,
				getEntityBoundingBox().expand(dX, dY, dZ),
				e -> e != this && getDistanceToEntity(e) <= r && e.posY <= posY + dY);
	}

	public int getCasterID() {
		return getDataManager().get(CASTER);
	}

	public void setCasterID(int id) {
		getDataManager().set(CASTER, id);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {

	}


}
