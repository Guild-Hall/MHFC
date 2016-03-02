package mhfc.net.common.ai.entity.rathalos;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.IFrameAdvancer.SwitchLoopAdvancer;
import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityRathalos;
import mhfc.net.common.entity.monster.EntityRathalos.Stances;
import net.minecraft.entity.Entity;

public class FlyLand extends AIAnimatedAction<EntityRathalos> {

	public static final float WEIGHT = 1.0f;
	public static final int FLAP_LAST_FRAME = 20;
	public static final int LAND_LAST_FRAME = 50;
	public static final String ANIMATION_LENGTH = "mhfc:models/Rathalos/RathalosFlightHover.mcanm";

	protected boolean hasTouchedDown;
	protected SwitchLoopAdvancer animationStep;

	public static FlyLand generate() {
		IAnimationProvider animationProvider = new IAnimationProvider.AnimationAdapter(
				ANIMATION_LENGTH,
				LAND_LAST_FRAME);
		ISelectionPredicate<EntityRathalos> selectionProvider = new ISelectionPredicate<EntityRathalos>() {
			@Override
			public boolean shouldSelectAttack(
					IExecutableAction<? super EntityRathalos> attack,
					EntityRathalos actor,
					Entity target) {
				return actor.getStance() == Stances.FLYING;
			}
		};
		IWeightProvider<EntityRathalos> weightProvider = new IWeightProvider.SimpleWeightAdapter<>(WEIGHT);
		AnimatedActionAdapter<EntityRathalos> actionAdapter = new AnimatedActionAdapter<>(
				animationProvider,
				selectionProvider,
				weightProvider);
		SwitchLoopAdvancer animationStep = new SwitchLoopAdvancer(0, FLAP_LAST_FRAME);
		return new FlyLand(actionAdapter, animationStep);
	}

	private FlyLand(AnimatedActionAdapter<EntityRathalos> actionAdapter, SwitchLoopAdvancer animationStep) {
		super(actionAdapter);
		this.animationStep = animationStep;
		setFrameAdvancer(animationStep);
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		hasTouchedDown = false;
	}

	@Override
	public void update() {
		if (!hasTouchedDown) {
			EntityRathalos entity = getEntity();
			entity.motionY = Math.max(entity.motionY, -1);
			if (!entity.isAirBorne) {
				hasTouchedDown = true;
				entity.setStance(Stances.GROUND);
				animationStep.setLoopActive(false);
			}
		}
	}

	@Override
	public boolean shouldContinue() {
		return !hasTouchedDown || super.shouldContinue();
	}

}
