package mhfc.net.common.ai.general.provider.impl;

import com.github.worldsender.mcanm.common.animation.IAnimation;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.requirements.INeedsAnimations;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;

/**
 * Interface that can be implemented when providing the animations through an {@link IFrameAdvancer}.
 *
 * @author WorldSEnder
 *
 */
public interface IHasAnimationProvider extends INeedsAnimations {

	IAnimationProvider getAnimProvider();

	@Override
	default IFrameAdvancer provideFrameAdvancer() {
		return getAnimProvider();
	}

	@Override
	default IAnimation provideAnimation() {
		return getAnimProvider().getAnimation();
	}

	@Override
	default IContinuationPredicate provideContinuationPredicate() {
		return getAnimProvider();
	}
}
