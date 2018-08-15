package mhfc.net.common.ai.general.actions;

import mhfc.net.common.entity.type.EntityMHFCBase;

public abstract class SleepAction<T extends EntityMHFCBase<?>> extends AnimatedAction<T> {
	
	private float sleepFrequency = 50F;

	public SleepAction() {}
	
	
	@Override
	protected void beginExecution() {
		super.beginExecution();
		//getEntity().playSleepSound();	
	}
	
	@Override
	protected void onUpdate() {}
	

	@Override
	protected float computeSelectionWeight() {
		if(getEntity().getAttackTarget() != null){
			return DONT_SELECT;
			
		}
		if(getEntity().world.isDaytime())
			return DONT_SELECT;
		return sleepFrequency;
	}
	
	

}
