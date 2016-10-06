package mhfc.net.common.ai.general.actions;

import mhfc.net.common.entity.type.EntityMHFCBase;

public abstract class StunAction<T extends EntityMHFCBase<?>> extends AnimatedAction<T> {

	@Override
	protected float computeSelectionWeight() {
		return DONT_SELECT;
	}

	@Override
	public void beginExecution() {
		getEntity().playStunnedSound();
	}

}
