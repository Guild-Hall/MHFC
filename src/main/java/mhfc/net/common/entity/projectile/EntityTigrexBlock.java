package mhfc.net.common.entity.projectile;

import java.util.List;

import mhfc.net.common.entity.type.EntityWyvernHostile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityTigrexBlock extends EntityThrowable {

	public EntityTigrexBlock(World par) {
		super(par);
		setSize(1.0F, 1.0F);
	}
	
	public EntityTigrexBlock(World par, EntityLivingBase e) {
		super(par,e);
		rotationYaw = e.rotationYaw;
		setSize(1.0F, 1.0F);
	}
	
	protected float getGravityVelocity() {
		return 0.06F;
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(2.5D, 2.0D, 2.5D));
		list.remove(getThrower());
		
		for (int i = 0; i < list.size(); i++) {
			Entity entity = (Entity)list.get(i);
			
			if (getDistanceSqToEntity(entity) <= 6.25D){
				if(entity instanceof EntityPlayer || entity instanceof EntityWyvernHostile){
				entity.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), 14 + this.rand.nextInt(17));
				}else{
					entity.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), 86 + this.rand.nextInt(102));	
				}
			}
		}
	}
	
	
	public void writeEntityToNBT(NBTTagCompound tagcompound) {
		super.writeEntityToNBT(tagcompound);
	}
	
	public void readEntityFromNBT(NBTTagCompound tagcompound) {
		super.readEntityFromNBT(tagcompound);
	}

}
