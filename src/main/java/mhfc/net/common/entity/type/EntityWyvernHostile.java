package mhfc.net.common.entity.type;

import mhfc.net.common.ai.AIWyvernAttackOnCollide;
import mhfc.net.common.ai.AIWyvernWander;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWyvernHostile extends EntityCreature implements IMob
{
	
	public int getArmor = 12;
	public int health;
	public int speed;
	public int getExpValue = 1000;
	public boolean isHightoStun;
	public boolean lethResist;
	
    public EntityWyvernHostile(World par1World)
    {
        super(par1World);
        experienceValue = getExpValue;
    	getNavigator().setBreakDoors(true);
		getNavigator().setAvoidsWater(true);
        tasks.addTask(1, new EntityAISwimming(this));
        tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, false));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(6, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, 0, true));
        targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }

    public void onUpdate()
    {
        super.onUpdate();
        if(this.dimension != 0){
			this.setDead();
		}
    }
    
    protected Entity findPlayerToAttack()
    {
        EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 40.0D);
        return entityplayer != null && canEntityBeSeen(entityplayer) ? entityplayer : null;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
    	float dmg = par2;
    	Entity entity = par1DamageSource.getEntity();
    	return super.attackEntityFrom(par1DamageSource, dmg);
    }
    
    public boolean attackEntityAsMob(Entity par1Entity)
    {
		boolean flag = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 4);
		return flag;
    }

    
    public void dropItemRand(Item index, int par1)
    {
    	EntityItem var3 = new EntityItem(this.worldObj, posX + worldObj.rand.nextInt(5) - worldObj.rand.nextInt(5), posY + 1.0D, this.posZ + worldObj.rand.nextInt(5) - worldObj.rand.nextInt(5), new ItemStack(index, par1, 0));
		worldObj.spawnEntityInWorld(var3);
    }


    public float getBlockPathWeight(int par1, int par2, int par3)
    {
        return 1F - this.worldObj.getLightBrightness(par1, par2, par3);
    }
    
    public int getMaxSpawnedInChunk(){
		return 1;
	}
    
	protected boolean canDespawn(){
		return false;
	}
	
	protected boolean isAIEnabled(){
		return true;
	}
	
	
	public int getTotalArmorValue(){
		return getArmor;
	}

	protected void updateFallState(double par1, boolean par3) {}
     
     public void applyMonsterAttributes(double knockback, double lowhp, double medhp, double highhp, double range , double speed){
 		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(knockback);
 		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(lowhp, medhp, highhp));
 		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(range);
 		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed);
 	 }
     
     protected void fall(float f1){}
     
     public double healthbaseHP(double lowhp, double medhp, double highhp){
    	 
    	 if(this.rand.nextInt(60) == 0){
    		 return medhp;
    	 }else if(this.rand.nextInt(120) == 0){
    		 return highhp;
    	 }else if(this.rand.nextInt(80) == 0){
    		 return lowhp;
    	 }return medhp;
     }
     

}
