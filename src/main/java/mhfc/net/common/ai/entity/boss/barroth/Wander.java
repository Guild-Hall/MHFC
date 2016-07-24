package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.general.IFrameAdvancer;
import mhfc.net.common.ai.general.actions.AIGeneralWander;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.monster.EntityBarroth;
import net.minecraft.entity.Entity;

public class Wander extends AIGeneralWander<EntityBarroth> {

	private static final String ANIMATION = "mhfc:models/Barroth/BarrothWalk.mcanm";
	private static final int LAST_FRAME = 140;
	private static final float WEIGHT = 0.5F;

	private static final IMoveParameterProvider parameterProvider = new IMoveParameterProvider.MoveParameterAdapter(
			5f,
			0.7f);

	public Wander() {
		super(parameterProvider);
		setFrameAdvancer(new IFrameAdvancer.CountLoopAdvancer(0, 80, -1));
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
	public float getWeight(EntityBarroth entity, Entity target) {
		return WEIGHT;
	}

}
