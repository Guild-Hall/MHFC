package mhfc.net.common.ai.entity.boss.lagiacrus;

import mhfc.net.common.ai.general.actions.AIGeneralIdle;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityLagiacrus;
import net.minecraft.entity.Entity;

public class Idle extends AIGeneralIdle<EntityLagiacrus> {

	private static String ANIMATION = "mhfc:models/Lagiacrus/LagiacrusIdle.mcanm";
	private static int LAST_FRAME = 6;

	private static final IWeightProvider<EntityLagiacrus> WEIGHT_PROVIDER = new IWeightProvider.RandomWeightAdapter<>(1);
	
	public Idle() {
		
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
	public float getWeight(EntityLagiacrus entity, Entity target) {
		return WEIGHT_PROVIDER.getWeight(entity, target);
	}
}
