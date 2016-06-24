package mhfc.net.common.ai.entity.nonboss.gagua;

import mhfc.net.common.ai.general.actions.AIGeneralIdle;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityGagua;
import net.minecraft.entity.Entity;

public class GaguaIdleLook extends AIGeneralIdle<EntityGagua>{
	private static String ANIMATION = "mhfc:models/Gagua/GaguaIdleTwo.mcanm";
	private static int LAST_FRAME = 200;

	private static final IWeightProvider<EntityGagua> WEIGHT_PROVIDER = new IWeightProvider.RandomWeightAdapter<>(2);
	
	
	public GaguaIdleLook(){}
	
	@Override
	public String getAnimationLocation() {
		return ANIMATION;
	}

	@Override
	public int getAnimationLength() {
		return LAST_FRAME;
	}

	@Override
	public float getWeight(EntityGagua entity, Entity target) {
		return WEIGHT_PROVIDER.getWeight(entity, target);
	}

}
