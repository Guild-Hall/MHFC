package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.general.WeightUtils;
import mhfc.net.common.ai.general.actions.IdleAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityBarroth;

public class Idle extends IdleAction<EntityBarroth> implements IHasAnimationProvider {

	private static final String ANIMATION_LOCATION = "mhfc:models/Barroth/BarrothIdle.mcanm";
	private static final int LAST_FRAME = 60;
	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);

	public Idle() {}

	@Override
	protected float computeIdleWeight() {
		return WeightUtils.random(rng(), 6f);
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	public void onUpdate() {
		EntityBarroth entity = this.getEntity();
		if (this.getCurrentFrame() == 50) {
			entity.playLivingSound();
		}
	}
}
