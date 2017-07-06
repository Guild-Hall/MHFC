package mhfc.net.common.ai.entity.boss.tigrex.living;

import mhfc.net.common.ai.general.WeightUtils;
import mhfc.net.common.ai.general.actions.BreatheAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityTigrex;

public class Breathe extends BreatheAction<EntityTigrex> implements IHasAnimationProvider {

	private static final String ANIMATION_LOCATION = "mhfc:models/Tigrex/breathe.mcanm";
	private static final int LAST_FRAME = 60;
	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);

	public Breathe() {
	}

	@Override
	protected float computeIdleWeight() {
		return WeightUtils.random(rng(), 5f);
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
	}
}
