package mhfc.net.common.ai.entity.nonboss.gagua;

import mhfc.net.common.ai.general.actions.AIGeneralIdle;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityGagua;
import net.minecraft.entity.Entity;

public class GaguaSleep extends AIGeneralIdle<EntityGagua> {

	private static String ANIMATION = "mhfc:models/Gagua/GaguaSleep.mcanm";
	private static int LAST_FRAME = 1250;

	private static final IWeightProvider<EntityGagua> WEIGHT_PROVIDER = new IWeightProvider.RandomWeightAdapter<>(3);
	public GaguaSleep() {
		
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
	public float getWeight(EntityGagua entity, Entity target) {
		if(!entity.worldObj.isDaytime() && entity.getAttackTarget() == null){
			return WEIGHT_PROVIDER.getWeight(entity, target);
		}
		return DONT_SELECT;
	}
}
