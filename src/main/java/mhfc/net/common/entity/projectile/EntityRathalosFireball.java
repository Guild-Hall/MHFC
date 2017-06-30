package mhfc.net.common.entity.projectile;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityRathalosFireball extends EntityThrowable {

	public EntityLivingBase shootingEntity;
	public int radius = 1;

	public EntityRathalosFireball(World par1World) {
		super(par1World);
	}

	@SideOnly(Side.CLIENT)
	public EntityRathalosFireball(
			World par1World,
			double par2,
			double par4,
			double par6,
			double par8,
			double par10,
			double par12) {
		super(par1World, par2, par4, par6);
	}

	public EntityRathalosFireball(World par1World, EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
		shootingEntity = par2EntityLivingBase;
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		List<Entity> list = this.world
				.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(4.5D, 3.0D, 4.5D));
		list.remove(getThrower());
		for (Entity entity : list) {
			if (entity instanceof EntityPlayer) {
				entity.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), 4 + this.rand.nextInt(14));
			} else {
				entity.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), 29 + this.rand.nextInt(121));
			}
			if (world.isRemote || result.entityHit == null) {
				continue;
			}
			this.world.newExplosion(
					(Entity) null,
					this.posX,
					this.posY,
					this.posZ,
					this.radius,
					true,
					this.world.getGameRules().getBoolean("mobGriefing"));

		}

	}

	@Override
	protected float getGravityVelocity() {
		return 0;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagcompound) {
		super.writeEntityToNBT(tagcompound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagcompound) {
		super.readEntityFromNBT(tagcompound);
	}
}
