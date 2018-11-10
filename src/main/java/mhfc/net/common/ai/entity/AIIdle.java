package mhfc.net.common.ai.entity;

import mhfc.net.common.ai.general.actions.IdleAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.entity.CreatureAttributes;

public class AIIdle extends IdleAction<CreatureAttributes<?>> implements IHasAnimationProvider {

	protected CreatureAttributes<?> entity;
	protected String animationLocation;
	protected int animationLength;
	protected float weight;

	public AIIdle(CreatureAttributes<?> entity, String animationLocation, int animationLength, float weight) {
		this.entity = entity;
		this.animationLocation = animationLocation;
		this.animationLength = animationLength;
		this.weight = weight;

	}


	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, this.animationLocation, this.animationLength);
	}

	@Override
	protected float computeIdleWeight() {
		return this.weight;
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return super.provideContinuationPredicate().and(IHasAnimationProvider.super.provideContinuationPredicate());
	}

	
	

}
