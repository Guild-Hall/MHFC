package mhfc.net.common.ai.entity.boss.lagiacrus;

import mhfc.net.common.ai.general.actions.WanderAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.CountLoopAdvancer;
import mhfc.net.common.ai.general.provider.adapters.MoveParameterAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.monster.EntityLagiacrus;

public class Wander extends WanderAction<EntityLagiacrus> implements IHasAnimationProvider {

	private static final int LAST_FRAME = 100;
	private static final String ANIMATION_LOCATION = "mhfc:models/Lagiacrus/LagiacrusWalk.mcanm";

	private static final float WEIGHT = 0.5F;

	private static final IMoveParameterProvider MOVEMENT_PARAMS = new MoveParameterAdapter(3f, 0.52f);
	private final IAnimationProvider ANIMATION;

	public Wander() {
		ANIMATION = AnimationAdapter.builder().setAnimation(ANIMATION_LOCATION).setAnimationLength(LAST_FRAME)
				.setFrameAdvancer(new CountLoopAdvancer(10, 80, -1)).build(this);
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
