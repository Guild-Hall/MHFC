package mhfc.net.common.ai.entity.monsters.gargwa;

import mhfc.net.common.ai.general.actions.WanderAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.CountLoopAdvancer;
import mhfc.net.common.ai.general.provider.adapters.MoveParameterAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.monster.EntityGargwa;

public class Wander extends WanderAction<EntityGargwa> implements IHasAnimationProvider {

	private static final int LAST_FRAME = 45;
	private static final String ANIMATION_LOCATION = "mhfc:models/Gagua/GaguaWalk.mcanm";

	private static final float WEIGHT = 1F;

	private static final IMoveParameterProvider MOVEMENT_PARAMETERS = new MoveParameterAdapter(1f, 0.2f);
	private final IAnimationProvider ANIMATION;

	public Wander() {
		ANIMATION = AnimationAdapter.builder().setAnimation(ANIMATION_LOCATION).setAnimationLength(LAST_FRAME)
				.setFrameAdvancer(new CountLoopAdvancer(0, 45, 10)).build(this);
	}

	@Override
	protected float computeWanderWeight() {
		return WEIGHT;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	public IMoveParameterProvider provideMoveParameters() {
		return MOVEMENT_PARAMETERS;
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return super.provideContinuationPredicate().and(IHasAnimationProvider.super.provideContinuationPredicate());
	}
}
