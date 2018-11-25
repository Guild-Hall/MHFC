package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.provider.adapters.HasNoTargetAdapter;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.entity.CreatureAttributes;

public abstract class IdleAction<T extends CreatureAttributes<?>> extends AnimatedAction<T> {

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
		if (!SelectionUtils.isIdle(getEntity())) {
			return DONT_SELECT;
		}
		return computeIdleWeight();
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return new HasNoTargetAdapter(getEntity());
	}

	protected abstract float computeIdleWeight();

	@Override
	protected void onUpdate() {
		if (getEntity().getAttackTarget() != null) {
			this.finishAction();
		}
		this.getEntity().getLookHelper().setLookPosition(getEntity().posX + lookX, getEntity().posY + getEntity().getEyeHeight(), getEntity().posZ + lookZ, getEntity().getHorizontalFaceSpeed(), getEntity().getVerticalFaceSpeed());

	} // do nothing, we idle, remember?

}
