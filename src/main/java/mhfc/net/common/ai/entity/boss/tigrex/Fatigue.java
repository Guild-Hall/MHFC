package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.general.provider.simple.IAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityTigrex;
import net.minecraft.entity.Entity;

public class Fatigue extends Idle1{
	private static final int LAST_FRAME = 65;
	private static final IAnimationProvider ANIM_PROVIDER = new IAnimationProvider.AnimationAdapter(
			"mhfc:models/Tigrex/idle_fatigue.mcanm",
			LAST_FRAME);
	private static final IWeightProvider<EntityTigrex> WEIGHT_PROVIDER = new IWeightProvider.RandomWeightAdapter<>(15);

	public Fatigue() {}

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
		if(entity.getHealth() > 2500){
		return DONT_SELECT;
	}
		return WEIGHT_PROVIDER.getWeight(entity, target);
		
	}
}
