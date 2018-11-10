package mhfc.net.common.ai.entity.monsters.greatjaggi;

import mhfc.net.common.ai.general.actions.WanderAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.CountLoopAdvancer;
import mhfc.net.common.ai.general.provider.adapters.MoveParameterAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.creature.incomplete.GreatJaggi;

public class Wander extends WanderAction<GreatJaggi> implements IHasAnimationProvider {

	private static final int LAST_FRAME = 55;
	private static final String ANIMATION_LOCATION = "mhfc:models/GreatJaggi/GreatJaggiRun.mcanm";

	private static final float WEIGHT = 0.5F;

	private static final IMoveParameterProvider parameterProvider = new MoveParameterAdapter(3f, 0.4f);
	private final IAnimationProvider ANIMATION;

	public Wander() {
		ANIMATION = AnimationAdapter.builder().setAnimation(ANIMATION_LOCATION).setAnimationLength(LAST_FRAME)
				.setFrameAdvancer(new CountLoopAdvancer(0, 55, 3)).build(this);
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
		return parameterProvider;
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return super.provideContinuationPredicate().and(IHasAnimationProvider.super.provideContinuationPredicate());
	}
}
