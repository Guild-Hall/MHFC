package mhfc.net.common.ai.entity.monsters.rathalos;

import mhfc.net.common.ai.general.actions.IdleAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityRathalos;

public class Idle extends IdleAction<EntityRathalos> implements IHasAnimationProvider {

	private static final int LAST_FRAME = 65;
	private static final String ANIMATION_LOCATION = "mhfc:models/Rathalos/RathalosIdle.mcanm";
	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);

	private static final float WEIGHT = 1f;

	public Idle() {}

	@Override
	protected float computeIdleWeight() {
		return WEIGHT;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

}
