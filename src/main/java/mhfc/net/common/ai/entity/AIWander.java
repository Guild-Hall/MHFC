package mhfc.net.common.ai.entity;

import mhfc.net.common.ai.general.actions.WanderAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.CountLoopAdvancer;
import mhfc.net.common.ai.general.provider.adapters.MoveParameterAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;

public class AIWander<T extends EntityMHFCBase<? super T>> extends WanderAction<T> implements IHasAnimationProvider {

	protected EntityMHFCBase<?> entity;
	protected String animationLocation;
	protected int animationLength;
	protected float weight;
	protected float speed, turnrate;
	protected int loopStart, loopEnd, loopCount;

	public AIWander(
			EntityMHFCBase<?> entity,
			String animationLocation,
			int animationLength,
			float weight,
			float speed,
			float turnrate,
			int loopStart,
			int loopEnd,
			int loopCount) {
		this.entity = entity;
		this.animationLocation = animationLocation;
		this.animationLength = animationLength;
		this.weight = weight;
		this.speed = speed;
		this.turnrate = turnrate;
		this.loopStart = loopStart;
		this.loopEnd = loopEnd;
		this.loopCount = loopCount;
	}

	@Override
	public IFrameAdvancer provideFrameAdvancer() {
		return new CountLoopAdvancer(this.loopStart, this.loopEnd, this.loopCount);
	}

	@Override
	public IMoveParameterProvider provideMoveParameters() {
		return new MoveParameterAdapter(this.turnrate, this.speed);
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, this.animationLocation, this.animationLength);
	}

	@Override
	protected float computeWanderWeight() {
		return this.weight;
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return super.provideContinuationPredicate().and(IHasAnimationProvider.super.provideContinuationPredicate());
	}

}
