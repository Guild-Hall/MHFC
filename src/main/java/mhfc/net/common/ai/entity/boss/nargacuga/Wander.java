package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.general.IFrameAdvancer;
import mhfc.net.common.ai.general.actions.AIGeneralWander;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;
import net.minecraft.entity.Entity;

public class Wander extends AIGeneralWander<EntityNargacuga> {

	private static final String ANIMATION = "mhfc:models/Nargacuga/Wander.mcanm";
	private static final int LAST_FRAME = 70;
	private static final float WEIGHT = 0.3F;

	private static final IMoveParameterProvider parameterProvider = new IMoveParameterProvider.MoveParameterAdapter(
			3f,
			0.6f);

	public Wander() {
		super(parameterProvider);
		setFrameAdvancer(new IFrameAdvancer.CountLoopAdvancer(0, 70, -1));
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
	public float getWeight(EntityNargacuga entity, Entity target) {
		return WEIGHT;
	}

}
