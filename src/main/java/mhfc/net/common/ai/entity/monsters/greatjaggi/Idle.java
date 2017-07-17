package mhfc.net.common.ai.entity.monsters.greatjaggi;

import mhfc.net.common.ai.general.actions.IdleAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityGreatJaggi;

public class Idle extends IdleAction<EntityGreatJaggi> implements IHasAnimationProvider {

	private static final String ANIM_LOC = "mhfc:models/GreatJaggi/GreatJaggiIdle.mcanm";
	private static final int ANIM_FRAME = 64;

	public static final float ANIM_WEIGHT = 6;

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIM_LOC, ANIM_FRAME);

	@Override
	protected float computeIdleWeight() {
		return ANIM_WEIGHT;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	public void onUpdate() {
		EntityGreatJaggi entity = this.getEntity();
		if (getCurrentFrame() == 30) {
			entity.playLivingSound();
		}
	}

}
