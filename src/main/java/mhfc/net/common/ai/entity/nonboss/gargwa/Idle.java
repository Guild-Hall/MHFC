package mhfc.net.common.ai.entity.nonboss.gargwa;

import java.util.Arrays;
import java.util.List;

import mhfc.net.common.ai.general.WeightedPick;
import mhfc.net.common.ai.general.WeightedPick.WeightedItem;
import mhfc.net.common.ai.general.actions.IdleAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityGargwa;

public class Idle extends IdleAction<EntityGargwa> implements IHasAnimationProvider {

	private static enum Variant implements WeightedItem {
		IDLE_1("mhfc:models/Gagua/GaguaIdleOne.mcanm", 80) {
			@Override
			public float getWeight() {
				return 2f;
			}
		},
		IDLE_2("mhfc:models/Gagua/GaguaIdleTwo.mcanm", 200) {
			@Override
			public float getWeight() {
				return 1f;
			}
		};

		private final String animationLocation;
		private final int frameCount;

		private Variant(String animationLocation, int frameCount) {
			this.animationLocation = animationLocation;
			this.frameCount = frameCount;
		}

		public IAnimationProvider getAnimation(Idle action) {
			return new AnimationAdapter(action, animationLocation, frameCount);
		}

		@Override
		public boolean forceSelection() {
			return false;
		}

		@Override
		public abstract float getWeight();
	}

	private static final List<Variant> ALL_VARIANTS = Arrays.asList(Variant.values());

	private Variant variant;
	private IAnimationProvider animation;

	public Idle() {}

	@Override
	protected float computeIdleWeight() {
		return 1f;
	}

	@Override
	protected void initializeExecutionRandomness() {
		super.initializeExecutionRandomness();
		variant = WeightedPick.pickRandom(ALL_VARIANTS);
		animation = variant.getAnimation(this);
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return animation;
	}
}
