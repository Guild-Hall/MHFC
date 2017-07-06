package mhfc.net.common.ai.general.actions;

import mhfc.net.common.entity.type.EntityMHFCBase;

public abstract class IdleAction<T extends EntityMHFCBase<?>> extends AnimatedAction<T> {

	public IdleAction() {

	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		getEntity().playLivingSound();
	}

	@Override
	protected float computeSelectionWeight() {
		if (getEntity().getAttackTarget() != null) {
			return DONT_SELECT;
		}
		return computeIdleWeight();
	}

	protected abstract float computeIdleWeight();

	@Override
	protected void onUpdate() {
		if (getEntity().getAttackTarget() != null) {
			this.finishAction();
		}

	} // do nothing, we idle, remember?

}
