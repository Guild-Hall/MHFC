package mhfc.net.common.ai.entity.boss.rathalos;

import mhfc.net.common.ai.general.actions.AIGeneralIdle;
import mhfc.net.common.ai.general.provider.simple.IAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityRathalos;
import net.minecraft.entity.Entity;

public class Idle extends AIGeneralIdle<EntityRathalos> {

	private static final int LAST_FRAME = 65;
	private static final IAnimationProvider ANIM_PROVIDER = new IAnimationProvider.AnimationAdapter(
			"mhfc:models/Rathalos/RathalosIdle.mcanm",
			LAST_FRAME);
	private static final IWeightProvider<EntityRathalos> WEIGHT_PROVIDER = new IWeightProvider.RandomWeightAdapter<>(1);

	public Idle() {}

	@Override
	public String getAnimationLocation() {
		return ANIM_PROVIDER.getAnimationLocation();
	}

	@Override
	public int getAnimationLength() {
		return ANIM_PROVIDER.getAnimationLength();
	}

	@Override
	public float getWeight(EntityRathalos entity, Entity target) {
		return WEIGHT_PROVIDER.getWeight(entity, target);
	}
}
