package mhfc.net.common.ai.general.actions;

import mhfc.net.common.entity.CreatureAttributes;

public abstract class StunAction<T extends CreatureAttributes<?>> extends AnimatedAction<T> {

	@Override
	protected float computeSelectionWeight() {
		return DONT_SELECT;
	}

	@Override
	public void beginExecution() {
		getEntity().playStunnedSound();
	}

}
