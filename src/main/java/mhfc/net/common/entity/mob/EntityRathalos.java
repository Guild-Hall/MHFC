package mhfc.net.common.entity.mob;

import mhfc.net.MHFCMain;
import mhfc.net.common.ai.AIWyvernAttackOnCollide;
import mhfc.net.common.ai.AIWyvernWander;
import mhfc.net.common.ai.rathalos.AIRathalosFireball;
import mhfc.net.common.core.registry.MHFCRegItem;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import mhfc.net.common.implement.iMHFC;
import mhfc.net.common.network.packet.PacketAIRathalos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
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
		tasks.addTask(0, new AIRathalosFireball(this));
		tasks.addTask(2, (new AIWyvernAttackOnCollide(this, EntityPlayer.class, 1f, false)).setMaxAttackTick(0));
		tasks.addTask(2, (new AIWyvernAttackOnCollide(this, 1f, true)).setMaxAttackTick(0));
		tasks.addTask(3, (new AIWyvernWander(this, 0.8F)));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityAnimal.class, 0, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityMob.class, 0, true));
	}
	
	public void onUpdate(){super.onUpdate();if(currentAttackID != 0)animTick++;}
	public void setAnimID(int id) {currentAttackID = id;}
	public void setAnimTick(int tick) {animTick = tick;}
	public int getAnimID() {return currentAttackID;}
	public int getAnimTick() {return animTick;}
	protected String getLivingSound(){return"mhfc:rathalos.say";}
	protected String getHurtSound(){return "mhfc:rathalos.hurt";}
	
	public void applyEntityAttributes(){
		super.applyEntityAttributes();
		applyMonsterAttributes(1.3D, 4600D, 6100D, 7200D, 35D, 0.3D);
	}
	
	public void sendAttackPacket(int id) {
		if(MHFCMain.isEffectiveClient()) return;
		currentAttackID = id;
		MHFCMain.packetPipeline.sendToAll(new PacketAIRathalos((byte)id, this));
	}
	
	public boolean attackEntityAsMob(Entity entity){
		if(!worldObj.isRemote) {
			if(currentAttackID == 0){
				sendAttackPacket(1);
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
