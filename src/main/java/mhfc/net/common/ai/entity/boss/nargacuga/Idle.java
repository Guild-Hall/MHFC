package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.general.WeightUtils;
import mhfc.net.common.ai.general.actions.IdleAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;

public class Idle extends IdleAction<EntityNargacuga> implements IHasAnimationProvider {

	private static final int LAST_FRAME = 140;
	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/NargacugaIdle.mcanm";
	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);

	public Idle() {}

	@Override
	protected float computeIdleWeight() {
		return WeightUtils.random(rng(), 6);
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

}
