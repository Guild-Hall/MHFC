package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.general.actions.WanderAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.CountLoopAdvancer;
import mhfc.net.common.ai.general.provider.adapters.MoveParameterAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.monster.EntityBarroth;

public class Wander extends WanderAction<EntityBarroth> implements IHasAnimationProvider {


	protected float speed, turnrate;

	public Wander(float speed, float turnrate) {
		this.speed = speed;
		this.turnrate = turnrate;
		
	}

	@Override
	public IFrameAdvancer provideFrameAdvancer() {
		return new CountLoopAdvancer(0, 31, 1);
	}

	@Override
	protected float computeWanderWeight() {
		return 1F;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/Barroth/walknew.mcanm", 65);
	}

	@Override
	public IMoveParameterProvider provideMoveParameters() {
		return new MoveParameterAdapter(turnrate, speed);
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return super.provideContinuationPredicate().and(IHasAnimationProvider.super.provideContinuationPredicate());
	}

}
