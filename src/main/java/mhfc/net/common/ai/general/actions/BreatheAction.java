package mhfc.net.common.ai.general.actions;

import mhfc.net.common.entity.type.EntityMHFCBase;

public abstract class BreatheAction<T extends EntityMHFCBase<?>> extends AnimatedAction<T> {

	public BreatheAction() {
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();


		getEntity().playLivingSound();
	}

	@Override
	protected float computeSelectionWeight() {
		return computeIdleWeight();
	}

	protected abstract float computeIdleWeight();

	@Override
	protected void onUpdate() {
		if (target != null) {
			getEntity().getTurnHelper().updateTargetPoint(target);
			getEntity().getTurnHelper().updateTurnSpeed(20.0F);
		}

	} // do nothing, we idle, remember?

}
