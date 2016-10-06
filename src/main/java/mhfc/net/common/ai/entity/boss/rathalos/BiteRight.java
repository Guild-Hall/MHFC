package mhfc.net.common.ai.entity.boss.rathalos;

import com.github.worldsender.mcanm.common.animation.IAnimation;

import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;

public class BiteRight extends BiteLeft {

	private static final int ANIM_FRAME = 50;
	private static final String ANIMATION_LOCATION = "mhfc:models/Rathalos/RathalosBiteRight.mcanm";

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, ANIM_FRAME);

	public BiteRight() {}

	@Override
	public IAnimation provideAnimation() {
		return ANIMATION.getAnimation();
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return ANIMATION;
	}

	@Override
	public IFrameAdvancer provideFrameAdvancer() {
		return ANIMATION;
	}

}
