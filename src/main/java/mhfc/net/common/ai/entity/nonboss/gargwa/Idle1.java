package mhfc.net.common.ai.entity.nonboss.gargwa;

import mhfc.net.common.ai.general.actions.AIGeneralIdle;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityGargwa;
import net.minecraft.entity.Entity;

public class Idle1 extends AIGeneralIdle<EntityGargwa> {

	private static String ANIMATION = "mhfc:models/Gagua/GaguaIdleOne.mcanm";
	private static int LAST_FRAME =80;

	private static final IWeightProvider<EntityGargwa> WEIGHT_PROVIDER = new IWeightProvider.RandomWeightAdapter<>(1);
	
	public Idle1() {
	}

	@Override
	public String getAnimationLocation() {
		return ANIMATION;
	}

	@Override
	public int getAnimationLength() {
		return LAST_FRAME;
	}

	@Override
	public float getWeight(EntityGargwa entity, Entity target) {
		return WEIGHT_PROVIDER.getWeight(entity, target);
	}
}
