package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.general.actions.WanderAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.CountLoopAdvancer;
import mhfc.net.common.ai.general.provider.adapters.MoveParameterAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.monster.EntityDeviljho;

public class Wander extends WanderAction<EntityDeviljho> implements IHasAnimationProvider {

	private static final int LAST_FRAME = 55;
	private static final String ANIMATION_LOCATION = "mhfc:models/Deviljho/DeviljhoWalk.mcanm";
	private static final IMoveParameterProvider MOVEMENT_PARAMS = new MoveParameterAdapter(3f, 1f);

	private static final float WEIGHT = 0.5F;

	private final IAnimationProvider ANIMATION;

	public Wander() {
		ANIMATION = AnimationAdapter.builder().setAnimation(ANIMATION_LOCATION).setAnimationLength(LAST_FRAME)
				.setFrameAdvancer(new CountLoopAdvancer(0, 55, -1)).build(this);
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

	@Override
	protected void beginExecution() {
		super.beginExecution();
	}

}
