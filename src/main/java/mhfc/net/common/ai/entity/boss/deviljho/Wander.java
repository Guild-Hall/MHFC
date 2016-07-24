package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.general.IFrameAdvancer;
import mhfc.net.common.ai.general.actions.AIGeneralWander;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.monster.EntityDeviljho;
import net.minecraft.entity.Entity;

public class Wander extends AIGeneralWander<EntityDeviljho> {

	private static final String ANIMATION = "mhfc:models/Deviljho/DeviljhoWalk.mcanm";
	private static final int LAST_FRAME = 55;
	private static final float WEIGHT = 0.5F;

	private static final IMoveParameterProvider parameterProvider = new IMoveParameterProvider.MoveParameterAdapter(
			3f,
			1f);

	public Wander() {
		super(parameterProvider);
		setFrameAdvancer(new IFrameAdvancer.CountLoopAdvancer(0, 55, -1));
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
	public float getWeight(EntityDeviljho entity, Entity target) {
		return WEIGHT;
	}

}
