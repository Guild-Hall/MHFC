package mhfc.net.common.ai.entity.monsters.rathalos;

import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityRathalos;

public class Fly3Fireball extends AnimatedAction<EntityRathalos> implements IHasAnimationProvider {

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/Rathalos/flight3fireball.mcanm", 125);
	}

	@Override
	protected float computeSelectionWeight() {
		return 0;
	}

}
