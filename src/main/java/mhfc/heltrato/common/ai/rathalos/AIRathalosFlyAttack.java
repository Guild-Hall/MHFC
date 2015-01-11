package mhfc.heltrato.common.ai.rathalos;

import java.util.Random;

import mhfc.heltrato.common.ai.AIAnimation;
import mhfc.heltrato.common.entity.mob.EntityRathalos;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class AIRathalosFlyAttack extends AIAnimation {

	private EntityRathalos rath;
	private EntityLivingBase target;
	private Random rand;
	
	public AIRathalosFlyAttack(EntityRathalos mob) {
		super(mob);
		rath = mob;
		target = null;
		rand = rath.getRNG();
	}
	
	public boolean shouldAnimate(){
		target = rath.getAttackTarget();
		if(target == null){
			return false;
		}if(!rath.onGround){
			return false;
		}else
			return rath.currentAttackID == 2;
	}

	public int getAnimID() {
		return 2;
	}

	public boolean isAutomatic() {
		return false;
	}

	public int getDuration() {
		return 20;
	}
	
	public void startExecuting() {
		super.startExecuting();
	} 
	
	public void updateTask() {
		if(rath.getAnimTick() < 10) {
			rath.getLookHelper().setLookPositionWithEntity(target, 30f, 30f);
			rath.motionY = 0.8D;
			target.motionY = 0.8D;
			target.attackEntityFrom(DamageSource.causeMobDamage(rath), 6 + rand.nextInt(18));
			System.out.println("Rath is now Flying");
		}
	}

}
