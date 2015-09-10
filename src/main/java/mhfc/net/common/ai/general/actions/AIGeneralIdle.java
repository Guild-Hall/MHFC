package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.type.EntityMHFCBase;

public class AIGeneralIdle <EntityT extends EntityMHFCBase<? super EntityT>>
extends
ActionAdapter<EntityT> {
	{
}

	@Override
	protected void update() {
		
	}

	@Override
	public float getWeight() {
		return 0;
	}

}
