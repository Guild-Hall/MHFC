package mhfc.net.common.ai.entity.delex;

import mhfc.net.common.ai.general.actions.AIGeneralIdle;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityDelex;

public class DelexIdle extends AIGeneralIdle<EntityDelex> {

	private static final int LAST_FRAME = 60;
	private static final IAnimationProvider ANIM_PROVIDER = new IAnimationProvider.AnimationAdapter(
		"mhfc:models/Delex/delexidle.mcanm", LAST_FRAME);
	private static final IWeightProvider<EntityDelex> WEIGHT_PROVIDER = new IWeightProvider.RandomWeightAdapter<>(
		3);

	public DelexIdle() {
		super(ANIM_PROVIDER, WEIGHT_PROVIDER);
	}
}
