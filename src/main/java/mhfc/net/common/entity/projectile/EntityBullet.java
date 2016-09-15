package mhfc.net.common.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBullet extends EntityThrowable {
	protected float damage;

	public EntityBullet(World par1World) {
		super(par1World);
	}

	public EntityBullet(World par1World, EntityLivingBase par2EntityLivingBase, float Damage) {
		super(par1World, par2EntityLivingBase);
		damage = Damage;

	}

	public EntityBullet(World par1World, double par2, double par4, double par6, float Damage) {
		super(par1World, par2, par4, par6);
		damage = Damage;
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.entityHit != null) {
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damage);
		}
		if (!this.worldObj.isRemote) {
			this.setDead();
		}
	}
}
