package mhfc.net.common.ai.entity.deviljho;

import mhfc.net.common.ai.general.actions.AIGeneralWander;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IMoveParameterProvider;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.mob.EntityDeviljho;

public class DeviljhoWander extends AIGeneralWander<EntityDeviljho> {

	private static final IAnimationProvider animationProvider = new IAnimationProvider.AnimationAdapter(
		"mhfc:models/Deviljho/DeviljhoWalk.mcanm", 40);
	private static final IWeightProvider<EntityDeviljho> weightProvider = new IWeightProvider.SimpleWeightAdapter<EntityDeviljho>(
		50);
	private static final IMoveParameterProvider parameterProvider = new IMoveParameterProvider.MoveParameterAdapter(
		10f, 1f);

	public DeviljhoWander() {
		super(animationProvider, weightProvider, parameterProvider);
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		// MHFCMain.logger.info("Wander selected");
	}

}
