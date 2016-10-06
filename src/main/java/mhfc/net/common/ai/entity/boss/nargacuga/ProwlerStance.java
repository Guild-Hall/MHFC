package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;

public class ProwlerStance extends AnimatedAction<EntityNargacuga> implements IHasAnimationProvider {

	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/Pounce.mcanm";
	private static final int ANIMATION_LENGTH = 18;

	private static final float MAX_ANGLE = 40;
	private static final float MAX_DISTANCE = 40;
	private static final float WEIGHT = 15;

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, ANIMATION_LENGTH);

	public ProwlerStance() {}

	private boolean shouldSelect() {
		return SelectionUtils.isInDistance(0, MAX_DISTANCE, getEntity(), target)
				&& SelectionUtils.isInViewAngle(-MAX_ANGLE, MAX_ANGLE, getEntity(), target);
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
