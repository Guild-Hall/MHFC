package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.general.actions.WanderAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.MoveParameterAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.monster.EntityBarroth;

public class Wander extends WanderAction<EntityBarroth> implements IHasAnimationProvider {

	private static final String ANIMATION_LOCATION = "mhfc:models/Barroth/walknew.mcanm";
	private static final int LAST_FRAME = 64;

	private static final float WEIGHT = 0.7F;

	private static final IMoveParameterProvider MOVEMENT_PARAMETERS = new MoveParameterAdapter(0.7f, 0.5f);

	private IAnimationProvider ANIMATION;

	public Wander() {
		
		ANIMATION = AnimationAdapter.builder().setAnimation(ANIMATION_LOCATION).setAnimationLength(LAST_FRAME)
				.build(this);
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
