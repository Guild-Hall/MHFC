package mhfc.net.common.ai.entity.boss.tigrex.living;

import mhfc.net.common.ai.general.actions.WanderAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.CountLoopAdvancer;
import mhfc.net.common.ai.general.provider.adapters.MoveParameterAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.monster.EntityTigrex;

public class Wander extends WanderAction<EntityTigrex> implements IHasAnimationProvider {

	protected float speed;

	public Wander(float speed) {
		this.speed = speed;
	}

	@Override
	protected float computeWanderWeight() {
		return 1F;
	}

	@Override
	public IFrameAdvancer provideFrameAdvancer() {

		return new CountLoopAdvancer(19, 85, -1);
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/Tigrex/walk.mcanm", 122);
	}

	@Override
	public IMoveParameterProvider provideMoveParameters() {
		return new MoveParameterAdapter(1.2f, speed);
	}
	

}
