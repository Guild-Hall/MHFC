package mhfc.net.common.entity.projectile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityLightning extends EntityThrowable {
	public EntityLiving el;
	public long field_92057_e;
	@SuppressWarnings("unused")
	private int fire;

	public EntityLightning(World world) {
		super(world);
	}

	public EntityLightning(World world, EntityLiving entityliving) {
		super(world, entityliving);
	}

	public EntityLightning(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	/**
	 * Called when the throwable hits a block or entity.
	 */
	@Override
	protected void onImpact(RayTraceResult movingobjectposition) {
		worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);

		if (movingobjectposition.entityHit != null) {
			if (!movingobjectposition.entityHit
					.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0)) {
				;
			}
		}
		for (int bolt = 0; bolt < 5; bolt++) {
			;
		}
		if (!worldObj.isRemote) {
			EntityLightningBolt entitylightningbolt = new EntityLightningBolt(worldObj, posX, posY, posZ, true);
			entitylightningbolt.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			worldObj.addWeatherEffect(entitylightningbolt);
		}

		for (int i = 0; i < 8; i++) {
			worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		}

		if (!worldObj.isRemote) {
			setDead();
		}
	}

}
