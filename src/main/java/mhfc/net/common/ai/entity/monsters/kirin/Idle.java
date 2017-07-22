package mhfc.net.common.ai.entity.monsters.kirin;

import mhfc.net.common.ai.general.WeightUtils;
import mhfc.net.common.ai.general.actions.IdleAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.wip.EntityKirin;

public class Idle extends IdleAction<EntityKirin> implements IHasAnimationProvider {

	private static final int LAST_FRAME = 160;
	private static final String ANIMATION_LOCATION = "mhfc:models/Kirin/KirinIdle.mcanm";
	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);

	public Idle() {}

	@Override
	protected float computeIdleWeight() {
		return WeightUtils.random(rng(), 3);
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	public void onUpdate() {
		EntityKirin entity = this.getEntity();
		if (this.getCurrentFrame() == 10) {
			entity.playLivingSound();
		}
	}
}
