package mhfc.net.common.entity.mob;

import mhfc.net.MHFCMain;
import mhfc.net.common.ai.AIWyvernAttackOnCollide;
import mhfc.net.common.ai.AIWyvernWander;
import mhfc.net.common.ai.kirin.AIKirinAttack;
import mhfc.net.common.ai.kirin.AIKirinBolt;
import mhfc.net.common.ai.kirin.AIKirinJump;
import mhfc.net.common.core.registry.MHFCRegItem;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import mhfc.net.common.implement.iMHFC;
import mhfc.net.common.network.packet.PacketAIKirin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityKirin extends EntityWyvernHostile implements iMHFC{
	
	public int currentAttackID;
	public int animTick;
	public int deathTick;
	
	
	public EntityKirin(World par1World) {
		super(par1World);
		width = 3f;
		height = 3f;
		animTick = 0;
		getArmor = 15;
		isImmuneToFire = true;
		tasks.addTask(0, (new AIKirinAttack(this, 0.5f)));
		tasks.addTask(1, (new AIKirinJump(this, 1f)));
		tasks.addTask(1, (new AIKirinBolt(this)));
		tasks.addTask(2, (new AIWyvernAttackOnCollide(this, EntityPlayer.class, 1f, false)).setMaxAttackTick(0));
		tasks.addTask(2, (new AIWyvernAttackOnCollide(this, 1f, true)).setMaxAttackTick(0));
		tasks.addTask(3, (new AIWyvernWander(this, 0.8F)));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		
	}
	
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		applyMonsterAttributes(2.5D, 1700D, 2600D, 3500D, 0.32D, 25D);
	}
	   
	protected String getLivingSound(){
		return	"mhfc:kirin.say";
	}
	
	public void onUpdate(){
		super.onUpdate();
			if(currentAttackID != 0){
					animTick++;
				}	
				worldObj.spawnParticle("cloud", this.posX + this.rand.nextFloat() * 2.0F - 1.0D, this.posY + this.rand.nextFloat() * 3.0F + 1.0D, this.posZ + this.rand.nextFloat() * 2.0F - 1.0D, 0.0D, 0.0D, 0.0D);
	}
	
	
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
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
	
	public void attackEntityAtDistSq(EntityLivingBase living, float f){
        if(!worldObj.isRemote){
        		if(currentAttackID == 0 && onGround && rand.nextInt(20) == 0){
        				sendAttackPacket(1);
        		}
        	}
    	}
	
	
	
	public boolean attackEntityAsMob(Entity entity){
	    if(!worldObj.isRemote){
	    	if(currentAttackID == 0 && onGround){
	    		sendAttackPacket(1);
	    		}
	    }
	    return true;
		}
	
	
	public void sendAttackPacket(int id){
		if (MHFCMain.isEffectiveClient()) return;
		this.currentAttackID = id;
		MHFCMain.packetPipeline.sendToAll(new PacketAIKirin((byte) id, this));
    }
	
	protected void dropFewItems(boolean par1, int par2)
	{
	        int var4, i;
	        for( var4 = 0; var4< 16; ++var4)
	        {
	        	dropItemRand(MHFCRegItem.mhfcitemkirinmane, 2);
	        }
	        for (var4 = 0; var4 < 9; ++var4){
	            dropItemRand(MHFCRegItem.mhfcitemkiringem, 2);
	        }
	        for (var4 = 0; var4 < 5; ++var4){
	            dropItemRand(MHFCRegItem.mhfcitemkirinthundertail, 1);
	            dropItemRand(MHFCRegItem.mhfcitemlightcrystal, 1);
	        }
	        for (var4 = 0; var4 < 2; ++var4){
	        	dropItemRand(MHFCRegItem.mhfcitempurecrystal, 1);
	        	dropItemRand(MHFCRegItem.mhfcitemplatinummane, 1);
	        }
	    
	    }
	  
	
	

}
