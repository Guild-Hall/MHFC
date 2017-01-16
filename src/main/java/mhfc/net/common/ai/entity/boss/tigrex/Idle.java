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
	
	protected boolean canBeUseWhileEngage; //TODO: Make default breathing idle use in fight

	private enum Variant implements WeightedItem {
		DEFAULT("mhfc:models/Tigrex/idle.mcanm" ,		  60, 5F),
		IDLE_1 ("mhfc:models/Tigrex/idle.mcanm"	,		 160, 7F),
		IDLE_2 ("mhfc:models/Tigrex/idle2.mcanm" , 		 160,9F),
		IDLE_3 ("mhfc:models/Tigrex/idle3.mcanm" ,		 260,16F),
		IDLE_4 ("mhfc:models/Tigrex/idle4.mcanm",		 200, 12F),
		IDLE_5 ("mhfc:models/Tigrex/idle5.mcanm"  ,		 260, 20F);
		
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
	private static final List<Variant> DEFAULT_VARIANT = Arrays.asList(Variant.DEFAULT);
	private static final float WEIGHT = 2.5F;

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
		if(getEntity().getAttackTarget() != null){
		this.variant = WeightedPick.pickRandom(DEFAULT_VARIANT);
		}else{
		this.variant = WeightedPick.pickRandom(ALL_VARIANTS);
		}
		this.animation = variant.getAnimation(this);
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return animation;
	}

}
