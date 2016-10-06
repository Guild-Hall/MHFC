package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.entity.type.EntityMHFCBase;

public abstract class IdleAction<T extends EntityMHFCBase<?>> extends AnimatedAction<T> {

	public IdleAction() {}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		if (target != null) {
			getEntity().getTurnHelper().updateTargetPoint(target);
		}
		getEntity().getTurnHelper().updateTurnSpeed(2.0F);
		getEntity().playLivingSound();
	}

	@Override
	protected float computeSelectionWeight() {
		return SelectionUtils.isIdle(getEntity()) ? computeIdleWeight() : DONT_SELECT;
	}

	protected abstract float computeIdleWeight();

	@Override
	protected void onUpdate() {} // do nothing, we idle, remember?

}
