package mhfc.net.common.ai.general.actions;

import mhfc.net.common.entity.type.EntityMHFCBase;

public abstract class BreatheAction<T extends EntityMHFCBase<?>> extends AnimatedAction<T> {

	protected double lookX;
	protected double lookZ;

	public BreatheAction() {
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		double pos = (Math.PI * 2D) * this.getEntity().getRNG().nextDouble();
		lookX = Math.cos(pos);
		lookZ = Math.sin(pos);

		getEntity().playLivingSound();
	}

	@Override
	protected float computeSelectionWeight() {
		return computeIdleWeight();
	}

	protected abstract float computeIdleWeight();

	@Override
	protected void onUpdate() {
		getEntity().getLookHelper().setLookPosition(
				this.getEntity().posX + this.lookX,
				this.getEntity().posY + this.getEntity().getEyeHeight(),
				this.getEntity().posZ + this.lookZ,
				this.getEntity().getHorizontalFaceSpeed() - 9.1f,
				this.getEntity().getVerticalFaceSpeed());

	}
}
