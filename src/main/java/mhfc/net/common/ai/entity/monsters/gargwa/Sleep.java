package mhfc.net.common.ai.entity.monsters.gargwa;

import mhfc.net.common.ai.general.actions.IdleAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityGargwa;

public class Sleep extends IdleAction<EntityGargwa> implements IHasAnimationProvider {

	private static final int LAST_FRAME = 1250;
	private static final String ANIMATION_LOCATION = "mhfc:models/Gagua/GaguaSleep.mcanm";
	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);

	public Sleep() {}

	private boolean shouldSelect() {
		return !getEntity().world.isDaytime();
	}

	@Override
	protected float computeIdleWeight() {
		return shouldSelect() ? 0.3f : DONT_SELECT;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}
}
