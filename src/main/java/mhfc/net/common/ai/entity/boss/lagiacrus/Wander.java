package mhfc.net.common.ai.entity.boss.lagiacrus;

import mhfc.net.common.ai.general.IFrameAdvancer;
import mhfc.net.common.ai.general.actions.AIGeneralWander;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.monster.EntityLagiacrus;
import net.minecraft.entity.Entity;

public class Wander extends AIGeneralWander<EntityLagiacrus> {

	private static final String ANIMATION = "mhfc:models/Lagiacrus/LagiacrusWalk.mcanm";
	private static final int LAST_FRAME = 100;
	private static final float WEIGHT = 0.5F;

	private static final IMoveParameterProvider parameterProvider = new IMoveParameterProvider.MoveParameterAdapter(
			3f,
			0.52f);

	public Wander() {
		super(parameterProvider);
		setFrameAdvancer(new IFrameAdvancer.CountLoopAdvancer(10, 80, -1));
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
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
		return WEIGHT;
	}

}
