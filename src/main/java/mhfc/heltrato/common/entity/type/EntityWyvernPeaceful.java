package mhfc.heltrato.common.entity.type;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityWyvernPeaceful extends EntityCreature 
{
	public int armorNum = 5;
	public int health;
	public int speed;
	public int expValue = 100;
	public int spawnChunk = 4;
	
	
	public EntityWyvernPeaceful(World par1World) {
		super(par1World);
		experienceValue = expValue;
		getNavigator().setBreakDoors(true);
		getNavigator().setAvoidsWater(true);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIPanic(this, 0.6F));
        tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(2, new EntityAILookIdle(this));
        tasks.addTask(3, new EntityAIWander(this, 0.8F));
        
	}
	
	public int getTotalArmorValue(){
		return armorNum;
	}
	
	 public void onUpdate()
	 {
		 super.onUpdate();
		 if(this.dimension != 0){
			 this.setDead();
		 }
	 }
	 
	 public void dropItemRand(Item index, int par1)
	 {
		 EntityItem var3 = new EntityItem(this.worldObj, posX + worldObj.rand.nextInt(5) - worldObj.rand.nextInt(5), posY + 1.0D, this.posZ + worldObj.rand.nextInt(5) - worldObj.rand.nextInt(5), new ItemStack(index, par1, 0));
		 worldObj.spawnEntityInWorld(var3);
	 }
	
	public boolean isAIEnabled()
    {
        return true;
    }

	
	protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		playSound("mob.cow.step", 0.15F, 1.0F);
	}
	
	public int getMaxSpawnedInChunk()
    {
        return spawnChunk;
    }
    protected boolean canDespawn()
    {
        return false;
    }
    
    public void applyMonsterAttributes( double lowhp, double medhp, double highhp, double speed){
 		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(lowhp, medhp, highhp));
 		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed);
    }
     
    protected void fall(float f1){}
     
    public double healthbaseHP(double lowhp, double medhp, double highhp){
    	 
    	if(this.rand.nextInt(60) == 0){
    		return medhp;
    	}else if(this.rand.nextInt(120) == 0){
    		return highhp;
    	}else if(isChild()){
    		return lowhp;
    	}return medhp;
     }
     

	
	

}
