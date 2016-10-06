package mhfc.net.common.ai.general.provider.adapters;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

import com.github.worldsender.mcanm.common.CommonLoader;
import com.github.worldsender.mcanm.common.animation.IAnimation;
import com.google.common.base.Preconditions;

import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;
import net.minecraft.util.ResourceLocation;

public class AnimationAdapter implements IAnimationProvider {
	private IAnimation animation;
	private IContinuationPredicate continuation;
	private IFrameAdvancer frameAdvancer;

	public AnimationAdapter(AnimatedAction<?> action, String animationLocation, int animationLength) {
		this(action, builder().setAnimation(animationLocation).setAnimationLength(animationLength));
	}

	public AnimationAdapter(AnimatedAction<?> action, ResourceLocation animationLocation, int animLength) {
		this(action, builder().setAnimation(animationLocation).setAnimationLength(animLength));
	}

	public AnimationAdapter(AnimatedAction<?> action, Builder builder) {
		this.animation = builder.animation.get();
		this.continuation = IContinuationPredicate.untilFrame(action, builder.animLength.getAsInt());
		this.frameAdvancer = builder.frameAdvancer.get();
	}

	@Override
	public IAnimation getAnimation() {
		return animation;
	}

	@Override
	public boolean shouldContinueAction() {
		return continuation.shouldContinueAction();
	}

	@Override
	public int getFollowingFrame(int currentFrame) {
		return frameAdvancer.getFollowingFrame(currentFrame);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Supplier<IAnimation> animation;
		private IntSupplier animLength;
		private Supplier<IFrameAdvancer> frameAdvancer;

		private Builder() {
			this.animation = () -> {
				throw new IllegalStateException("needs an animation");
			};
			this.animLength = () -> {
				throw new IllegalStateException("needs an animLength");
			};
			this.frameAdvancer = LinearAdvancer::new;
		}

		public Builder setAnimation(String animLocation) {
			this.animation = () -> CommonLoader.loadAnimation(new ResourceLocation(animLocation));
			return this;
		}

		public Builder setAnimation(ResourceLocation resLocation) {
			this.animation = () -> CommonLoader.loadAnimation(resLocation);
			return this;
		}

		public Builder setAnimation(IAnimation animation) {
			this.animation = () -> animation;
			return this;
		}

		public Builder setAnimationLength(int animLength) {
			Preconditions.checkArgument(animLength >= 0);
			this.animLength = () -> animLength;
			return this;
		}

		public Builder setFrameAdvancer(IFrameAdvancer frameAdvancer) {
			this.frameAdvancer = () -> frameAdvancer;
			return this;
		}

		public AnimationAdapter build(AnimatedAction<?> action) {
			return new AnimationAdapter(action, this);
		}
	}
}
