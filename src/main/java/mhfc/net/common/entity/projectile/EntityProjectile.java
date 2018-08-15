package mhfc.net.common.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

public abstract class EntityProjectile extends EntityArrow implements IThrowableEntity {

	public float extraDamage;

	public EntityProjectile(World world) {
		super(world);
		setSize(0.5F, 0.5F);
		arrowShake = 0;
		pickupStatus = PickupStatus.DISALLOWED;

		extraDamage = 0;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
	}

	@Override
	protected void arrowHit(EntityLivingBase living) {
		applyEntityHitEffects(living);
	}

	protected void applyEntityHitEffects(Entity entity) {
		// TODO: elemental effects & extra damage
	}

	protected float getAirResistance() {
		return 0.99F;
	}

	protected float getGravity() {
		return 0.05F;
	}

	public final double getTotalVelocity() {
		return Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (!this.inGround) {
			// Values from super.onUpdate()
			float f1 = 0.99F;
			float f2 = 0.05F;
			float customAirResist = getAirResistance();
			float customGravity = getGravity();
			motionY += f2;
			motionX *= customAirResist / f1;
			motionY *= customAirResist / f1;
			motionZ *= customAirResist / f1;
			motionY -= customGravity;
		}
	}

	@Override
	public boolean getIsCritical() {
		return false;
	}

	public void setExtraDamage(float f) {
		extraDamage = f;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setFloat("extraDamage", extraDamage);

	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		extraDamage = nbttagcompound.getFloat("extraDamage");
	}
}
