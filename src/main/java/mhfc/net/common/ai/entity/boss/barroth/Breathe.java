package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.general.WeightUtils;
import mhfc.net.common.ai.general.actions.BreatheAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityBarroth;

public class Breathe extends BreatheAction<EntityBarroth> implements IHasAnimationProvider {


	public Breathe() {}

	@Override
	protected float computeIdleWeight() {
		return WeightUtils.random(rng(), 3f);
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/Barroth/barrothidle.mcanm", 60);
	}

	@Override
	public void onUpdate() {
		EntityBarroth entity = this.getEntity();
		if (this.getCurrentFrame() == 50) {
			entity.playLivingSound();
		}
	}
}
