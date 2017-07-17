package mhfc.net.common.ai.entity.monsters.nargacuga;

import mhfc.net.common.ai.general.actions.WanderAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.CountLoopAdvancer;
import mhfc.net.common.ai.general.provider.adapters.MoveParameterAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;

public class Wander extends WanderAction<EntityNargacuga> implements IHasAnimationProvider {


	protected float speed;
	protected float turnRate;

	public Wander(float turnRate, float speed) {
		this.turnRate = turnRate;
		this.speed = speed;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/nargacuga/wander.mcanm", 120);
	}

	@Override
	public IFrameAdvancer provideFrameAdvancer() {
		return new CountLoopAdvancer(10, 94, 2);
	}

	@Override
	public IMoveParameterProvider provideMoveParameters() {
		return new MoveParameterAdapter(this.turnRate, this.speed);
	}

	@Override
	protected float computeWanderWeight() {
		return 1f;
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return super.provideContinuationPredicate().and(IHasAnimationProvider.super.provideContinuationPredicate());
	}

}
