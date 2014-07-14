package mhfc.heltrato.common.ai.tigrex;

import java.util.List;

import mhfc.heltrato.common.ai.AIAnimation;
import mhfc.heltrato.common.entity.mob.EntityTigrex;
import mhfc.heltrato.common.entity.type.EntityWyvernHostile;
import mhfc.heltrato.common.interfaces.iMHFC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class AITigrexSpin extends AIAnimation{
	
	private EntityTigrex tigrex;
	private EntityLivingBase target;
	
	public AITigrexSpin(EntityTigrex entity) {
		super(entity);
		tigrex = entity;
		target = null;
	}

	public boolean shouldAnimate(){
		target = tigrex.getAttackTarget();
		if(target == null){
			return false;
		}else
			return tigrex.getAnimID() == 3;
	}
	
	public void startExecuting(){
		super.startExecuting();
		target = tigrex.getAttackTarget();
	}
	
	public void resetTask(){
		super.resetTask();
	}

	
	public int getAnimID() {
		return 3;
	}

	public boolean isAutomatic() {
		return true;
	}

	public int getDuration() {
		return 40;
	}
	
	public void updateTask(){
		List list = tigrex.worldObj.getEntitiesWithinAABBExcludingEntity(tigrex, tigrex.boundingBox.expand(4D, 2.0D, 4D));
		list.remove(tigrex);
		
		for (int i = 0; i < list.size(); i++) {
			Entity entity = (Entity)list.get(i);
			
		if(tigrex.getAnimTick() == 5){
			if(entity instanceof EntityPlayer || entity instanceof EntityWyvernHostile){
			tigrex.getLookHelper().setLookPositionWithEntity(target, 30F, 30F);
			entity.attackEntityFrom(DamageSource.causeMobDamage(tigrex), 14F);
			target.motionY = 1.2D;
			
		}else{
			entity.attackEntityFrom(DamageSource.causeMobDamage(tigrex), 49F * 4);
			target.motionY = 1.2D;
		
		}

		 }
		
	
		}

	}
}