package mhfc.net.common.ai.entity.tigrex;

import mhfc.net.common.ai.general.actions.AIGeneralIdle;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.mob.EntityTigrex;

public class IdleAnim extends AIGeneralIdle<EntityTigrex> {

	private static final int LAST_FRAME = 160;
	private static final IAnimationProvider ANIM_PROVIDER = new IAnimationProvider.AnimationAdapter(
		"mhfc:models/Tigrex/idle.mcanm", LAST_FRAME);
	private static final IWeightProvider<EntityTigrex> WEIGHT_PROVIDER = new IWeightProvider.RandomWeightAdapter<>(
		3);

	public IdleAnim() {
		super(ANIM_PROVIDER, WEIGHT_PROVIDER);
	}
}
