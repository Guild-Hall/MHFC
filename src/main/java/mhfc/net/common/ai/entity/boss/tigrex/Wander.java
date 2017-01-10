package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.general.actions.WanderAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.CountLoopAdvancer;
import mhfc.net.common.ai.general.provider.adapters.MoveParameterAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.monster.EntityTigrex;

public class Wander extends WanderAction<EntityTigrex> implements IHasAnimationProvider {

	private static final int ANIMATION_LENGTH = 90;
	private static final String ANIMATION_LOCATION = "mhfc:models/Tigrex/walk.mcanm";

	private static final float TURN_SPEED = 4f;
	private static final float MOVE_SPEED = 0.4f;
	private static final IMoveParameterProvider MOVEMENT_PARAMS = new MoveParameterAdapter(TURN_SPEED, MOVE_SPEED);

	private static final float WEIGHT = 1F;

	private final IAnimationProvider ANIMATION;

	public Wander() {
		ANIMATION = AnimationAdapter.builder().setAnimation(ANIMATION_LOCATION).setAnimationLength(ANIMATION_LENGTH).setFrameAdvancer(new CountLoopAdvancer(10, ANIMATION_LENGTH, -1)).build(this);
		
	}

	@Override
	protected float computeWanderWeight() {
		return WEIGHT;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	public IMoveParameterProvider provideMoveParameters() {
		return MOVEMENT_PARAMS;
	}

}
