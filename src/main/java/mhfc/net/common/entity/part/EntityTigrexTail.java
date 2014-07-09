package mhfc.net.common.entity.part;

import mhfc.net.common.entity.mob.EntityTigrex;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;


public class EntityTigrexTail extends Entity {
	
	
	public float taiLHealth;
	private EntityTigrex tigrex;
	int tail;

	public EntityTigrexTail(World par1World) {
		super(par1World);
		width = 1.5f;
		height = 1.5f;
		taiLHealth = tigrex.getHealth();
	}
	
	
	
	public void onUpdate() {
		super.onUpdate();
		if(tigrex == null && tigrex.isDead){
			this.setDead();
		}
		if(taiLHealth <= 1500){
			this.setDead();
		}
		
		 setDead();
		 this.worldObj.removeEntity(this);
	}
	
	public boolean attackEntityFrom(DamageSource source, float damage){
		if(tigrex != null){
			return this.tigrex.attackEntityFrom(source, Math.round(damage * 2.0F / 3.0F));
		}
		return false;
	}

	protected void entityInit() {
		
	}

	protected void readEntityFromNBT(NBTTagCompound var1) {
		
	}

	protected void writeEntityToNBT(NBTTagCompound var1) {
		
	}
	
	public boolean canBeCollidedWith()
	{
	return true;
	}
	
	public boolean canBePushed()
	{
	return false;
	}

	public boolean isEntityEqual(Entity entity)
	{
	return (this == entity) || (tigrex == entity);
	}

}
