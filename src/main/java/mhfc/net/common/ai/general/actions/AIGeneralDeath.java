package mhfc.net.common.ai.general.actions;

import mhfc.net.common.entity.type.EntityMHFCBase;

public abstract class AIGeneralDeath<EntityT extends EntityMHFCBase<? super EntityT>> extends AIAnimatedAction<EntityT> {
	
	/**
	 * TODO: Trying to create a general AI Death for the monsters;
	 * */
	protected EntityT deathActor;

	public AIGeneralDeath() {
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
	}


	@Override
	protected void update() {} // do nothing, we idle, remember?

}
