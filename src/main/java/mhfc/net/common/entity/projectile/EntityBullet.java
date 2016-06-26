package mhfc.net.common.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBullet extends EntityThrowable {
	protected float damage;
	@SuppressWarnings("unused")
	private short xTile;
	@SuppressWarnings("unused")
	private short yTile;
	@SuppressWarnings("unused")
	private short zTile;
	@SuppressWarnings("unused")
	private Block inTile;
	@SuppressWarnings("unused")
	private String throwerName;
	@SuppressWarnings("unused")
	private EntityLivingBase thrower;

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
	protected void onImpact(MovingObjectPosition position) {
		if (position.entityHit != null) {
			position.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damage);
		}
		if (!this.worldObj.isRemote) {
			this.setDead();
		}
	}
}
