package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.entity.boss.tigrex.living.Idle;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;

public class Fatigue extends Idle {
	private static final String ANIMATION_LOCATION = "mhfc:models/Tigrex/idle_fatigue.mcanm";
	private static final int LAST_FRAME = 65;

	private static final int WEIGHT = 15;

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);

	public Fatigue() {}

	@Override
	protected float computeIdleWeight() {
		if (getEntity().getHealth() > 2500) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}
}
