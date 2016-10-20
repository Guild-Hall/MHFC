package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.general.actions.IdleAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityDeviljho;

public class Idle extends IdleAction<EntityDeviljho> implements IHasAnimationProvider {

	private static final int LAST_FRAME = 100;
	private static final String ANIMATION_LOCATION = "mhfc:models/Deviljho/DeviljhoIdle.mcanm";
	public static final float WEIGHT = 6;

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);

	public Idle() {}

	@Override
	public void onUpdate() {
		EntityDeviljho entity = this.getEntity();
		if (this.getCurrentFrame() == 50) {
			entity.playSound(MHFCSoundRegistry.getRegistry().deviljhoIdle, 2.0F, 1.0F);
			entity.playLivingSound();
			// just a copy from roar the update method. nothing else
		}
	}

	@Override
	protected float computeIdleWeight() {
		return WEIGHT;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}
}
