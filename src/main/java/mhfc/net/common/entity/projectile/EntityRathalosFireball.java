package mhfc.net.common.entity.projectile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

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

	protected void onImpact(MovingObjectPosition var1) {
		@SuppressWarnings("rawtypes")
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(4.5D, 3.0D, 4.5D));
		list.remove(getThrower());
		for (int i = 0; i < list.size(); i++) {
			Entity entity = (Entity) list.get(i);
			if (!worldObj.isRemote) {
				if (var1.entityHit != null) {
					this.worldObj.newExplosion(
							(Entity) null,
							this.posX,
							this.posY,
							this.posZ,
							(float) this.radius,
							true,
							this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
					if (entity instanceof EntityPlayer) {
						entity.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), 4 + this.rand.nextInt(14));
					} else {
						entity.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), 29 + this.rand.nextInt(121));
					}
				}
			}

		}
	}

	@Override
	protected float getGravityVelocity() {
		return 0;
	}

	public void writeEntityToNBT(NBTTagCompound tagcompound) {
		super.writeEntityToNBT(tagcompound);
	}

	public void readEntityFromNBT(NBTTagCompound tagcompound) {
		super.readEntityFromNBT(tagcompound);
	}
}
