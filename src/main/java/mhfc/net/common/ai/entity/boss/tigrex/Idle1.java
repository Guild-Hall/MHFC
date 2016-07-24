package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.general.actions.AIGeneralIdle;
import mhfc.net.common.ai.general.provider.simple.IAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityTigrex;
import net.minecraft.entity.Entity;

public class Idle1 extends AIGeneralIdle<EntityTigrex> {

	private static final int LAST_FRAME = 160;
	private static final IAnimationProvider ANIM_PROVIDER = new IAnimationProvider.AnimationAdapter(
			"mhfc:models/Tigrex/idle.mcanm",
			LAST_FRAME);
	private static final IWeightProvider<EntityTigrex> WEIGHT_PROVIDER = new IWeightProvider.RandomWeightAdapter<>(6);

	public Idle1() {}

	@Override
	public String getAnimationLocation() {
		return ANIM_PROVIDER.getAnimationLocation();
	}

	@Override
	public int getAnimationLength() {
		return ANIM_PROVIDER.getAnimationLength();
	}

	@Override
	public float getWeight(EntityTigrex entity, Entity target) {
		return WEIGHT_PROVIDER.getWeight(entity, target);
	}
}
