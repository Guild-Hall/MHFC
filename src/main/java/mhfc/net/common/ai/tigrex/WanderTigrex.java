package mhfc.net.common.ai.tigrex;

import mhfc.net.MHFCMain;
import mhfc.net.common.ai.general.actions.WanderAction;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IMoveParameterProvider;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.mob.EntityTigrex;

public class WanderTigrex extends WanderAction<EntityTigrex> {

	private static final IAnimationProvider animationProvider = new IAnimationProvider.AnimationAdapter(
		"mhfc:models/Tigrex/walk.mcanm", 40);
	private static final IWeightProvider<EntityTigrex> weightProvider = new IWeightProvider.SimpleWeightAdapter<EntityTigrex>(
		50);
	private static final IMoveParameterProvider parameterProvider = new IMoveParameterProvider.MoveParameterAdapter(
		10f, 1f);

	public WanderTigrex() {
		super(animationProvider, weightProvider, parameterProvider);
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		MHFCMain.logger.info("Wander selected");
	}

}
