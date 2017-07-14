package mhfc.net.common.ai.entity;

import mhfc.net.common.ai.general.actions.IdleAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;

@SuppressWarnings("rawtypes")
public class AIIdle extends IdleAction<EntityMHFCBase> implements IHasAnimationProvider {

	protected EntityMHFCBase entity;
	protected String animationLocation;
	protected int animationLength;
	protected float weight;

	public AIIdle(EntityMHFCBase entity, String animationLocation, int animationLength, float weight) {
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
		return weight;
	}

	
	

}
