package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.general.actions.AIGeneralIdle;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;
import net.minecraft.entity.Entity;

public class Idle extends AIGeneralIdle<EntityNargacuga> {

	private static final String ANIMATION = "mhfc:models/Nargacuga/NargacugaIdle.mcanm";
	private static final int LAST_FRAME = 140;

	private static final IWeightProvider<EntityNargacuga> WEIGHT_PROVIDER = new IWeightProvider.RandomWeightAdapter<>(6);

	public Idle() {}

	@Override
	public String getAnimationLocation() {
		return ANIMATION;
	}

	@Override
	public int getAnimationLength() {
		return LAST_FRAME;
	}

	@Override
	public float getWeight(EntityNargacuga entity, Entity target) {
		return WEIGHT_PROVIDER.getWeight(entity, target);
	}
}
