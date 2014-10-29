package mhfc.heltrato.common.ai;

import mhfc.heltrato.common.interfaces.iMHFC;
import mhfc.heltrato.common.util.UtilStuff;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public abstract class AIAnimation extends EntityAIBase {
	
	public AIAnimation(iMHFC mob) {
		animatedEntity = mob;
		setMutexBits(7);
	}
	
	public abstract int getAnimID();
	
	public <T extends EntityLiving> T getEntity() {
		return (T)animatedEntity;
	}
	
	public abstract boolean isAutomatic();
	
	public abstract int getDuration();
	
	public boolean shouldAnimate() {
		return false;
	}
	
	public boolean shouldExecute() {
		if(isAutomatic()) return animatedEntity.getAnimID() == getAnimID();
		return shouldAnimate();
	}
	
	public void startExecuting() {
	  if(!isAutomatic()) UtilStuff.sendAnimPacket(animatedEntity, getAnimID());
		animatedEntity.setAnimTick(0);
	}
	
	public boolean continueExecuting() {
		return animatedEntity.getAnimTick() < getDuration();
	}
	
	public void resetTask() {
		UtilStuff.sendAnimPacket(animatedEntity, 0);
	}
	
	private iMHFC animatedEntity;
	
	// Experimenting for a while please abandone deleting this.
	public int getActiveSnaps(){
		return 2;
	}
	
}
