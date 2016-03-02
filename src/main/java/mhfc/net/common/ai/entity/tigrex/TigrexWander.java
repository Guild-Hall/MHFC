package mhfc.net.common.ai.entity.tigrex;

import mhfc.net.common.ai.general.IFrameAdvancer;
import mhfc.net.common.ai.general.actions.AIGeneralWander;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IMoveParameterProvider;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityTigrex;

public class TigrexWander extends AIGeneralWander<EntityTigrex> {

	public static final String ANIMATION = "mhfc:models/Tigrex/walk.mcanm";
	public static final int ANIMATION_LENGTH = 90;
	public static final float WEIGHT = 50;
	public static final float TURN_SPEED = 4f;
	public static final float MOVE_SPEED = 0.2f;
	private static final IAnimationProvider animationProvider = new IAnimationProvider.AnimationAdapter(ANIMATION, 40);
	private static final IWeightProvider<EntityTigrex> weightProvider = new IWeightProvider.SimpleWeightAdapter<EntityTigrex>(
			WEIGHT);
	private static final IMoveParameterProvider parameterProvider = new IMoveParameterProvider.MoveParameterAdapter(
			TURN_SPEED,
			MOVE_SPEED);

	public TigrexWander() {
		super(animationProvider, weightProvider, parameterProvider);
		setFrameAdvancer(new IFrameAdvancer.CountLoopAdvancer(10, ANIMATION_LENGTH, -1));
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
	}

}
