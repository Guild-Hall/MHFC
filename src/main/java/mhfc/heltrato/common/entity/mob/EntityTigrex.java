package mhfc.heltrato.common.entity.mob;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.ai.tigrex.AITigrexAttack;
import mhfc.heltrato.common.ai.tigrex.AITigrexBite;
import mhfc.heltrato.common.ai.tigrex.AITigrexRoar;
import mhfc.heltrato.common.ai.tigrex.AITigrexSpin;
import mhfc.heltrato.common.ai.tigrex.AITigrexThrow;
import mhfc.heltrato.common.core.registry.MHFCRegItem;
import mhfc.heltrato.common.entity.type.EntityWyvernHostile;
import mhfc.heltrato.common.interfaces.iMHFC;
import mhfc.heltrato.common.network.message.MessageAITigrex;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public class EntityTigrex extends EntityWyvernHostile implements iMHFC{
	
	public int currentAttackID;
	public int animTick;
	public int deathTick;
	public int rageLevel;
			
	public EntityTigrex(World par1World) {
		super(par1World);
		animTick = 0;
		width = 6F;
		height = 4F;
		tasks.addTask(0, (new AITigrexAttack(this, 0.3F)));
		tasks.addTask(1, (new AITigrexBite(this)));
		tasks.addTask(1, (new AITigrexSpin(this)));
		tasks.addTask(1, (new AITigrexRoar(this)));
		tasks.addTask(1, (new AITigrexThrow(this)));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityRathalos.class, 0, true));
	}
	
	public void applyEntityAttributes(){
		super.applyEntityAttributes();
		applyMonsterAttributes(1.3D, 6000D, 7800D, 8600D,  35D, 0.3D);
	}

	public void entityInit(){
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte)0));
		dataWatcher.addObject(17, Byte.valueOf((byte)0));
	}
			
	public void setThrownBlock(){
		dataWatcher.updateObject(16, Byte.valueOf((byte)1));
	}
	
	public boolean getThrownBlock(){
		return dataWatcher.getWatchableObjectByte(16) == 1;
	}
			
	protected void dropFewItems(boolean par1, int par2)
	{
		int var4, i;
		for( var4 = 0; var4< 13; ++var4)
		{
			dropItemRand(MHFCRegItem.MHFCItemTigrexScale, 2);
		}
		for (var4 = 0; var4 < 8; ++var4){
			dropItemRand(MHFCRegItem.MHFCItemTigrexShell, 1);
			dropItemRand(MHFCRegItem.mhfcitemtigrexfang, 1);
			dropItemRand(MHFCRegItem.mhfcitemtigrexclaw, 1);
		}
		for (var4 = 0; var4 < 1; ++var4){
			dropItemRand(MHFCRegItem.mhfcitemtigrextail, 2);
		}dropItemRand(MHFCRegItem.mhfcitemtigrexskullshell, 1);
		    
	}
	
	public void onUpdate(){
		super.onUpdate();
		if(currentAttackID != 0){
			animTick++;
		}	
	}
			 
	protected String getLivingSound(){
		return	null;
	}
			
	protected String getHurtSound(){
		return null;
	}
	
	protected String getDeathSound(){
		return null;
	}
			
	public void attackEntityAtDistSq(EntityLivingBase living, float f){
		if(!worldObj.isRemote)
		{
			if(currentAttackID == 0 && onGround && rand.nextInt(20) == 0){
				sendAttackPacket(1);
			}
		       
			if(currentAttackID == 0 && f < 1.0F && rand.nextInt(100) == 0){
				sendAttackPacket(3);
			}
		}
	}
			
	public boolean attackEntityAsMob(Entity entity){
		if(!worldObj.isRemote)
		{
			if(currentAttackID == 0 && rand.nextInt(4) == 0){
				sendAttackPacket(3);
			}
			if(currentAttackID == 0 && onGround){
				sendAttackPacket(1);
			}
		}
		return true;
	}

	public void sendAttackPacket(int id){
		if (MHFCMain.isEffectiveClient()) return;
		currentAttackID = id;
		MHFCMain.network.sendToAll(new MessageAITigrex((byte) id, this));
	}

	public void setAnimID(int id){
		currentAttackID = id;
	}
			
	public void setAnimTick(int tick){
		animTick = tick;
	}
	
	public int getAnimID(){
		return currentAttackID;
	}

	public int getAnimTick(){
		return animTick;
	}
			
}

