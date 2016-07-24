package mhfc.net.common.ai.entity.nonboss.gargwa;

import mhfc.net.common.ai.general.actions.AIGeneralIdle;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityGargwa;
import net.minecraft.entity.Entity;

public class Idle2 extends AIGeneralIdle<EntityGargwa>{
	private static String ANIMATION = "mhfc:models/Gagua/GaguaIdleTwo.mcanm";
	private static int LAST_FRAME = 200;

	private static final IWeightProvider<EntityGargwa> WEIGHT_PROVIDER = new IWeightProvider.RandomWeightAdapter<>(2);
	
	
	public Idle2(){}
	
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