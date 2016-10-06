package mhfc.net.common.ai.entity.boss.rathalos;

import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityRathalos;

public class Fireball extends AnimatedAction<EntityRathalos> implements IHasAnimationProvider {

	private static final int LAST_FRAME = 60;
	private static final String ANIMATION_LOCATION = "mhfc:models/Rathalos/RathalosFireBlast.mcanm";

	private static final double MAX_DISTANCE = 15f;
	private static final float WEIGHT = 6F;

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);

	public Fireball() {}

	private boolean shouldSelect() {
		return SelectionUtils.isInDistance(0, MAX_DISTANCE, getEntity(), target);
	}

	@Override
	protected float computeSelectionWeight() {
		return shouldSelect() ? WEIGHT : DONT_SELECT;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

}
