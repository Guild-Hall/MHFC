package mhfc.net.common.ai.entity.deviljho;

import mhfc.net.common.ai.general.actions.AIGeneralIdle;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityDeviljho;

public class DeviljhoIdle extends AIGeneralIdle<EntityDeviljho> {

	private static final int LAST_FRAME = 100;
	public static final String ANIMATION = "mhfc:models/Deviljho/DeviljhoIdle.mcanm";
	public static final float WEIGHT = 3;

	public DeviljhoIdle() {
		super(
				new IAnimationProvider.AnimationAdapter(ANIMATION, LAST_FRAME),
				new IWeightProvider.RandomWeightAdapter<>(WEIGHT));
	}

	@Override
	public void update() {
		EntityDeviljho entity = this.getEntity();
		if (this.getCurrentFrame() == 50) {
			entity.playLivingSound();
			// just a copy from roar the update method. nothing else
		}
	}
}
