package mhfc.net.common.ai.general.actions;

import mhfc.net.common.entity.type.EntityMHFCBase;

public abstract class IdleAction<T extends EntityMHFCBase<?>> extends AnimatedAction<T> {

	protected double lookX;
	protected double lookZ;

	public IdleAction() {

	}

	@Override
	protected void beginExecution() {
		super.beginExecution();

		getEntity().playLivingSound();
		double pos = (Math.PI * 2D) * this.getEntity().getRNG().nextDouble();
		lookX = Math.cos(pos);
		lookZ = Math.sin(pos);
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
