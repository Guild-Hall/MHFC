package mhfc.net.common.ai.entity;

import com.github.worldsender.mcanm.common.animation.IAnimation;

import mhfc.net.common.ai.general.actions.IdleAction;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;
import mhfc.net.common.entity.type.EntityMHFCBase;

@SuppressWarnings("rawtypes")
public class AIIdle extends IdleAction<EntityMHFCBase> implements IHasAnimationProvider {

	private double lookX;
	private double lookZ;

	@Override
	public IFrameAdvancer provideFrameAdvancer() {
		return null;
	}

	@Override
	public IAnimation provideAnimation() {
		return null;
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return null;
	}

	@Override
	protected float computeIdleWeight() {
		return 0;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return null;
	} 
	
	

}
