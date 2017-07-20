package mhfc.net.common.ai.entity;

import com.github.worldsender.mcanm.common.animation.IAnimation;

import mhfc.net.common.ai.general.actions.WanderAction;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;

@SuppressWarnings("rawtypes")
public class AIWander extends WanderAction implements IHasAnimationProvider {

	@Override
	public IFrameAdvancer provideFrameAdvancer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAnimation provideAnimation() {
		return null;
	}

	@Override
	public IMoveParameterProvider provideMoveParameters() {
		return null;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected float computeWanderWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
