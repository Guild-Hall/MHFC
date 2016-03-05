package mhfc.net.common.ai.entity.deviljho;

import mhfc.net.common.ai.general.IFrameAdvancer;
import mhfc.net.common.ai.general.actions.AIGeneralWander;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IMoveParameterProvider;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityDeviljho;

public class DeviljhoWander extends AIGeneralWander<EntityDeviljho> {

	private static final IAnimationProvider animationProvider = new IAnimationProvider.AnimationAdapter("mhfc:models/Deviljho/DeviljhoWalk.mcanm",55);
	private static final IWeightProvider<EntityDeviljho> weightProvider = new IWeightProvider.SimpleWeightAdapter<EntityDeviljho>(3F);
	private static final IMoveParameterProvider parameterProvider = new IMoveParameterProvider.MoveParameterAdapter(3f,	1f);

	public DeviljhoWander() {
		super(animationProvider, weightProvider, parameterProvider);
		setFrameAdvancer(new IFrameAdvancer.CountLoopAdvancer(0, 55, -1));
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
	}

}
