package mhfc.net.common.ai.entity.nonboss.delex;

import mhfc.net.common.ai.general.actions.IdleAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityDelex;

public class Idle extends IdleAction<EntityDelex> implements IHasAnimationProvider {

	private static final int LAST_FRAME = 60;
	private static final String ANIMATION_LOCATION = "mhfc:models/Delex/delexidle.mcanm";

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);

	public Idle() {}

	@Override
	protected float computeIdleWeight() {
		return 3f;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

}
