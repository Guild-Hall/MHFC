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
		getEntity().getLookHelper().setLookPosition(
				this.getEntity().posX + this.lookX,
				this.getEntity().posY + this.getEntity().getEyeHeight(),
				this.getEntity().posZ + this.lookZ,
				this.getEntity().getHorizontalFaceSpeed(),
				this.getEntity().getVerticalFaceSpeed());
		if (getEntity().getAttackTarget() != null) {
			this.finishAction();
		}

	} // do nothing, we idle, remember?

}
