package mhfc.net.common.ai.entity.nonboss.gargwa;

import mhfc.net.common.ai.general.actions.AIGeneralIdle;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityGargwa;
import net.minecraft.entity.Entity;

public class Sleep extends AIGeneralIdle<EntityGargwa> {

	private static String ANIMATION = "mhfc:models/Gagua/GaguaSleep.mcanm";
	private static int LAST_FRAME = 1250;

	private static final IWeightProvider<EntityGargwa> WEIGHT_PROVIDER = new IWeightProvider.RandomWeightAdapter<>(3);
	public Sleep() {
		
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
		if(!entity.worldObj.isDaytime() && entity.getAttackTarget() == null){
			return WEIGHT_PROVIDER.getWeight(entity, target);
		}
		return DONT_SELECT;
	}
}
