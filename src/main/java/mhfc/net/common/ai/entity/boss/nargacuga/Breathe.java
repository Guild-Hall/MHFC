package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.general.actions.BreatheAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;

public class Breathe extends BreatheAction<EntityNargacuga> implements IHasAnimationProvider {


	public Breathe() {}

	@Override
	protected float computeIdleWeight() {
		return 5F;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/nargacuga/nargacugaidle.mcanm", 60);
	}

}
