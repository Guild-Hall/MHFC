package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.general.IFrameAdvancer;
import mhfc.net.common.ai.general.actions.AIGeneralWander;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.monster.EntityTigrex;
import net.minecraft.entity.Entity;

public class Wander extends AIGeneralWander<EntityTigrex> {

	private static final String ANIMATION = "mhfc:models/Tigrex/walk.mcanm";
	private static final int ANIMATION_LENGTH = 90;
	private static final float WEIGHT = 0.5F;
	private static final float TURN_SPEED = 4f;
	private static final float MOVE_SPEED = 0.2f;
	private static final IMoveParameterProvider parameterProvider;

	static {
		parameterProvider = new IMoveParameterProvider.MoveParameterAdapter(TURN_SPEED, MOVE_SPEED);
	}

	public Wander() {
		super(parameterProvider);
		setFrameAdvancer(new IFrameAdvancer.CountLoopAdvancer(10, ANIMATION_LENGTH, -1));
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
		return ANIMATION_LENGTH;
	}

	@Override
	public float getWeight(EntityTigrex entity, Entity target) {
		return WEIGHT;
	}

}
