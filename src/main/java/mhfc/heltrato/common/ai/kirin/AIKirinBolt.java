package mhfc.heltrato.common.ai.kirin;

import mhfc.heltrato.common.ai.AIAnimation;
import mhfc.heltrato.common.entity.mob.EntityKirin;
import mhfc.heltrato.common.entity.projectile.EntityLightning;
import mhfc.heltrato.common.entity.type.EntityWyvernHostile;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class AIKirinBolt extends AIAnimation {
	
	private EntityKirin kirin;
	private EntityLivingBase target;

	public AIKirinBolt(EntityKirin entity) {
		super(entity);
		kirin = entity;
		target = null;
	}
	
	public boolean shouldAnimate() {
		target = kirin.getAttackTarget();
		if( (target == null)) {
			return false;
		}
		if(target.getDistanceSqToEntity(kirin) > 6D){
			return false;
		}
		
		return kirin.currentAttackID == 1;
	}

	public int getAnimID() {
		return 1;
	}
	
	public void startExecuting() {
		super.startExecuting();
		
	}

	public boolean isAutomatic() {
		return false;
	}

	public int getDuration() {
		return 20;
	}
	
	public void updateTask() {
		if( kirin.getAnimTick() == 10 ) {
			EntityLightning l = new EntityLightning(kirin.worldObj);
			
			
			if(target instanceof EntityPlayer || target instanceof EntityWyvernHostile){
				target.attackEntityFrom(DamageSource.causeMobDamage(kirin), 9F);
				target.motionX = 0.46D;
			}else{
				target.attackEntityFrom(DamageSource.causeMobDamage(kirin), 25F * 4);
			}
				l.setPosition(target.posX + target.getRNG().nextInt(2), target.posY, target.posZ + target.getRNG().nextInt(2));
				kirin.worldObj.spawnEntityInWorld(l);
				
		}
	}
	
	public void resetTask(){
		super.resetTask();
	}

}
