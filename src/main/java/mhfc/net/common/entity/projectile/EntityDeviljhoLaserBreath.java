package mhfc.net.common.entity.projectile;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityDeviljhoLaserBreath extends Entity {
	private static final int RANGE = 10;
	private static final int ARC = 45;
	private static final int DAMAGE_PER_HIT = 1;
	public EntityLivingBase caster;
	private static final DataParameter<Integer> CASTER = EntityDataManager
			.createKey(EntityDeviljhoLaserBreath.class, DataSerializers.VARINT);

	public EntityDeviljhoLaserBreath(World w) {
		super(w);
		this.setSize(0, 0);
	}

	public EntityDeviljhoLaserBreath(World w, EntityLivingBase l) {
		super(w);
		this.setSize(0, 0);
		if (!world.isRemote) {
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
		if (ticksExisted % 8 == 0) {
			if (world.isRemote) {
				// add custom particle
			}
		}
		for (int i = 0; i < 6; i++) {
			double xSpeed = speed * 1f * xComp;// + (spread * (rand.nextFloat() * 2 - 1) * (1 - Math.abs(xComp)));
			double ySpeed = speed * 1f * yComp;// + (spread * (rand.nextFloat() * 2 - 1) * (1 - Math.abs(yComp)));
			double zSpeed = speed * 1f * zComp;// + (spread * (rand.nextFloat() * 2 - 1) * (1 - Math.abs(zComp)));
			// target particle
		}

		for (int i = 0; i < 5; i++) {
			double xSpeed = speed * xComp
					+ (spread * 0.7 * (rand.nextFloat() * 2 - 1) * (Math.sqrt(1 - xComp * xComp)));
			double ySpeed = speed * yComp
					+ (spread * 0.7 * (rand.nextFloat() * 2 - 1) * (Math.sqrt(1 - yComp * yComp)));
			double zSpeed = speed * zComp
					+ (spread * 0.7 * (rand.nextFloat() * 2 - 1) * (Math.sqrt(1 - zComp * zComp)));
			double value = rand.nextFloat() * 0.15f;
			// target particle
		}
		if (ticksExisted > 10) {
			hitEntities();
		}
		// 65 is duration
		if (ticksExisted > 65 && !(caster instanceof EntityPlayer)) {
			setDead();
	}
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
		 List<EntityLivingBase> entitiesHit = getEntityLivingBaseNearby(RANGE, RANGE, RANGE, RANGE);
	        float damage = DAMAGE_PER_HIT;
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

	            float xzDistance = (float) Math.sqrt((entityHit.posZ - posZ) * (entityHit.posZ - posZ) + (entityHit.posX - posX) * (entityHit.posX - posX));
	            float entityHitPitch = (float) ((Math.atan2((entityHit.posY - posY), xzDistance) * (180 / Math.PI)) % 360);
	            float entityAttackingPitch = -rotationPitch % 360;
	            if (entityHitPitch < 0) {
	                entityHitPitch += 360;
	            }
	            if (entityAttackingPitch < 0) {
	                entityAttackingPitch += 360;
	            }
	            float entityRelativePitch = entityHitPitch - entityAttackingPitch;

	            float entityHitDistance = (float) Math.sqrt((entityHit.posZ - posZ) * (entityHit.posZ - posZ) + (entityHit.posX - posX) * (entityHit.posX - posX) + (entityHit.posY - posY) * (entityHit.posY - posY));

	            boolean inRange = entityHitDistance <= RANGE;
	            boolean yawCheck = (entityRelativeYaw <= ARC / 2 && entityRelativeYaw >= -ARC / 2) || (entityRelativeYaw >= 360 - ARC / 2 || entityRelativeYaw <= -360 + ARC / 2);
	            boolean ------------------------------------------- = (entityRelativePitch <= ARC / 2 && entityRelativePitch >= -ARC / 2) || (entityRelativePitch >= 360 - ARC / 2 || entityRelativePitch <= -360 + ARC / 2);
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
