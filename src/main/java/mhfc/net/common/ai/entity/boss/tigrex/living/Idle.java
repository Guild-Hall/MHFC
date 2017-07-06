package mhfc.net.common.ai.entity.boss.tigrex.living;

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
		IDLE_1 ("mhfc:models/Tigrex/idle.mcanm"	,		 160, 1F),
		IDLE_2 ("mhfc:models/Tigrex/idle2.mcanm" , 		 160,1F),
		IDLE_3 ("mhfc:models/Tigrex/idle3.mcanm" ,		 260,1F),
		IDLE_4 ("mhfc:models/Tigrex/idle4.mcanm",		 200, 1F),
		IDLE_5 ("mhfc:models/Tigrex/idle5.mcanm"  ,		 260, 1F);
		
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

	private static final float WEIGHT = 1.0F;

	private Variant variant;
	private IAnimationProvider animation;
	private static final List<Variant> DEFAULT_VARIANT = Arrays.asList(Variant.values());
	public Idle() {}

	@Override
	protected float computeIdleWeight() {
		return WEIGHT;
	}

	@Override
	protected void initializeExecutionRandomness() {
		super.initializeExecutionRandomness();
		this.variant = WeightedPick.pickRandom(DEFAULT_VARIANT);
		this.animation = variant.getAnimation(this);
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return animation;
	}

}
