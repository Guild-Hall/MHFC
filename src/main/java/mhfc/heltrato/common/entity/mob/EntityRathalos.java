package mhfc.heltrato.common.entity.mob;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.ai.rathalos.AIRathalosFireball;
import mhfc.heltrato.common.ai.rathalos.AIRathalosFlyAttack;
import mhfc.heltrato.common.core.registry.MHFCRegItem;
import mhfc.heltrato.common.entity.type.EntityWyvernHostile;
import mhfc.heltrato.common.interfaces.iMHFC;
import mhfc.heltrato.common.network.message.MessageAIRathalos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public class EntityRathalos extends EntityWyvernHostile implements iMHFC 
{
	
	public int currentAttackID;
	public int animTick;
	public int deathTick;
	public int rageLevel;

	public EntityRathalos(World par1World) {
		super(par1World);
		width = 4F;
		height = 5F;
		isImmuneToFire = true;
		tasks.addTask(0, (new AIRathalosFireball(this)));
		tasks.addTask(0, (new AIRathalosFlyAttack(this)));
	}
	
	public void onUpdate(){
		super.onUpdate();
		if(currentAttackID != 0){
			animTick++;
		}
	}
	
	public void setAnimID(int id) {
		currentAttackID = id;
	}
	
	public void setAnimTick(int tick) {
		animTick = tick;
	}
	
	public int getAnimID() {
		return currentAttackID;
	}
	
	public int getAnimTick() {
		return animTick;
	}
	protected String getLivingSound(){
		return"mhfc:rathalos.say";
	}
	protected String getHurtSound(){
		return "mhfc:rathalos.hurt";
	}
	
	public void applyEntityAttributes(){
		super.applyEntityAttributes();
		applyMonsterAttributes(1.3D, 4600D, 6100D, 7200D, 35D, 0.3D);
	}
	
	public void sendAttackPacket(int id) {
		if(MHFCMain.isEffectiveClient()) return;
		currentAttackID = id;
		MHFCMain.network.sendToAll(new MessageAIRathalos((byte)id, this));
	}
	
	public boolean attackEntityAsMob(Entity entity){
		if(!worldObj.isRemote) {
			if(currentAttackID == 0){
				sendAttackPacket(1);
			}
			if(currentAttackID == 0 && rand.nextInt(4) == 0){
				sendAttackPacket(2);
			}
		}
		return true;
	}
	
	protected void dropFewItems(boolean par1, int par2)
	{
		int var4, i;
		for( var4 = 0; var4< 13; ++var4)
		{
			dropItemRand(MHFCRegItem.mhfcitemrathalosshell, 2);
		}
		for (var4 = 0; var4 < 8; ++var4){
			dropItemRand(MHFCRegItem.mhfcitemflamesac, 2);
			dropItemRand(MHFCRegItem.mhfcitemrathaloswebbing, 2);
			dropItemRand(MHFCRegItem.mhfcitemrathaloswing, 1);
		}
		for (var4 = 0; var4 < 1; ++var4){
			dropItemRand(MHFCRegItem.mhfcitemwyvernmarrow, 1);
		}	dropItemRand(MHFCRegItem.mhfcitemrathalosplate, 1);
		    
	}
	

}
