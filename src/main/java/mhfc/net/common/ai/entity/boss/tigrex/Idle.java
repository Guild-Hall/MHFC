package mhfc.net.common.ai.entity.boss.tigrex;

import java.util.Arrays;
import java.util.List;

import mhfc.net.common.ai.general.WeightedPick;
import mhfc.net.common.ai.general.WeightedPick.WeightedItem;
import mhfc.net.common.ai.general.actions.IdleAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityTigrex;

public class Idle extends IdleAction<EntityTigrex> implements IHasAnimationProvider {

	private enum Variant implements WeightedItem {
		IDLE_1("mhfc:models/Tigrex/idle.mcanm", 160, 0.8F),
		IDLE_2("mhfc:models/Tigrex/idle2.mcanm", 160, 0.7F),
		IDLE_3("mhfc:models/Tigrex/idle3.mcanm", 160, 0.6F);
		private float weight;
		private String animLocation;
		private int animLength;

		private Variant(String animLocation, int animLength, float weight) {
			this.animLocation = animLocation;
			this.animLength = animLength;
			this.weight = weight;
		}

		public IAnimationProvider getAnimation(Idle idle) {
			return new AnimationAdapter(idle, animLocation, animLength);
		}

		@Override
		public boolean forceSelection() {
			return false;
		}

		@Override
		public float getWeight() {
			return weight;
		}
	}

	private static final List<Variant> ALL_VARIANTS = Arrays.asList(Variant.values());
	private static final float WEIGHT = 3;

	private Variant variant;
	private IAnimationProvider animation;

	public Idle() {}

	@Override
	protected float computeIdleWeight() {
		return WEIGHT;
	}

	@Override
	protected void initializeExecutionRandomness() {
		super.initializeExecutionRandomness();
		this.variant = WeightedPick.pickRandom(ALL_VARIANTS);
		this.animation = variant.getAnimation(this);
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return animation;
	}

}
