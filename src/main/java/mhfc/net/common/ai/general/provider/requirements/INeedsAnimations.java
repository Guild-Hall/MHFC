package mhfc.net.common.ai.general.provider.requirements;

import com.github.worldsender.mcanm.common.animation.IAnimation;
import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;

public interface INeedsAnimations extends INeedsContinuationPredicate {
	/**
	 * Called before execution starts to determine how to play the animation
	 *
	 * @return
	 */
	IFrameAdvancer provideFrameAdvancer();

	/**
	 * Called before execution to determine what animation to play
	 *
	 * @return
	 */
	IAnimation provideAnimation();
}
