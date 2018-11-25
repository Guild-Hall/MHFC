package mhfc.net.common.ai.general.actions;

import mhfc.net.common.entity.CreatureAttributes;
import net.minecraft.entity.EntityLivingBase;

public abstract class BreatheAction<T extends CreatureAttributes<?>> extends AnimatedAction<T> {

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
		EntityLivingBase entity = getEntity().getAttackTarget();
		
		// While Breathing now looks on targetjust like in game.
		if(entity != null) {
		getEntity().getLookHelper().setLookPositionWithEntity(entity, 15, 15);
		}
	}
}
