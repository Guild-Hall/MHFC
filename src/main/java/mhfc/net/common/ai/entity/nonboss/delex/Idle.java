package mhfc.net.common.ai.entity.nonboss.delex;

import mhfc.net.common.ai.general.actions.AIGeneralIdle;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityDelex;
import net.minecraft.entity.Entity;

public class Idle extends AIGeneralIdle<EntityDelex> {

	private static final String ANIMATION = "mhfc:models/Delex/delexidle.mcanm";
	private static final int LAST_FRAME = 60;

	private static final IWeightProvider<EntityDelex> WEIGHT_PROVIDER = new IWeightProvider.RandomWeightAdapter<>(3);

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
	public float getWeight(EntityDelex entity, Entity target) {
		return WEIGHT_PROVIDER.getWeight(entity, target);
	}
}
