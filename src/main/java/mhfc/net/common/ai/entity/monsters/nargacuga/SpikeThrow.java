package mhfc.net.common.ai.entity.monsters.nargacuga;

import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;

public class SpikeThrow extends AnimatedAction<EntityNargacuga> implements IHasAnimationProvider{

	@Override
	public IAnimationProvider getAnimProvider() {
		// TODO Auto-generated method stub
		return new AnimationAdapter(this, "mhfc:models/Nargacuga/spikethrow.mcanm", 55);
	}

	@Override
	protected float computeSelectionWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
