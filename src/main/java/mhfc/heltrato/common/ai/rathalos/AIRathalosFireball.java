package mhfc.heltrato.common.ai.rathalos;

import mhfc.heltrato.common.ai.AIAnimation;
import mhfc.heltrato.common.entity.mob.EntityRathalos;
import mhfc.heltrato.common.entity.type.EntityWyvernHostile;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.DamageSource;

public class AIRathalosFireball extends AIAnimation {
	
	private EntityRathalos rath;
	private EntityLivingBase target;

	public AIRathalosFireball(EntityRathalos mob) {
		super(mob);
		rath = mob;
		target = null;
	}
	
	public boolean shouldAnimate() {
		target = rath.getAttackTarget();
		if(target == null) {
			return false;
		}
		
		return rath.currentAttackID == 1;
	}
	
	public void startExecuting() {
		super.startExecuting();
	}

	public int getAnimID() {
		return 1;
	}

	public boolean isAutomatic() {
		return false;
	}

	public int getDuration() {
		return 30;
	}
	
	public void updateTask() {
		
		double d0 = target.posX - rath.posX;
        double d1 = target.boundingBox.minY + (double)(target.height / 2.0F) - (rath.posY + (double)(rath.height / 2.0F));
        double d2 = target.posZ - rath.posZ;
        
		if(rath.getAnimTick() > 10){
			for(int i = 0; i < 1; i++){
                 EntitySmallFireball entitysmallfireball = new EntitySmallFireball(rath.worldObj, rath, d0, d1, d2);
                 entitysmallfireball.posY = rath.posY + (double)(rath.height / 2.0F);
                 entitysmallfireball.posX = rath.posX;
                 entitysmallfireball.posZ = rath.posZ;
                 rath.worldObj.spawnEntityInWorld(entitysmallfireball);
                 if(target instanceof EntityPlayer || target instanceof EntityWyvernHostile){
     				target.attackEntityFrom(DamageSource.causeFireballDamage(entitysmallfireball, rath), 14);
     			}
     			else{
     				target.attackEntityFrom(DamageSource.causeMobDamage(rath), 90F * 5);
     			}
     			
             }
			
		}
	}
}