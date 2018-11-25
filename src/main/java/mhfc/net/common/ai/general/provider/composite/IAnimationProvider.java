package mhfc.net.common.ai.general.provider.composite;

import com.github.worldsender.mcanm.common.animation.IAnimation;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;

public interface IAnimationProvider extends IContinuationPredicate, IFrameAdvancer {

	IAnimation getAnimation();
}
