package mhfc.net.common.ai.entity.monsters.delex;

import mhfc.net.common.ai.general.actions.WanderAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.CountLoopAdvancer;
import mhfc.net.common.ai.general.provider.adapters.MoveParameterAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.monster.EntityDelex;

public class Wander extends WanderAction<EntityDelex> implements IHasAnimationProvider {

	protected float speed;

	public Wander(float speed) {
		this.speed = speed;
	}

	@Override
	public IMoveParameterProvider provideMoveParameters() {
		return new MoveParameterAdapter(1.2f, speed);
	}

	@Override
	public IFrameAdvancer provideFrameAdvancer() {
		return new CountLoopAdvancer(9, 55, 3);
	}


	@Override
	protected float computeWanderWeight() {
		return 1f;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/delex/delexmovetotarget.mcanm", 100);
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return super.provideContinuationPredicate().and(IHasAnimationProvider.super.provideContinuationPredicate());
	}

}
