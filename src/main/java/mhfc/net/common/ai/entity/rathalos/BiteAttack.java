package mhfc.net.common.ai.entity.rathalos;

import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityRathalos;

public class BiteAttack extends AIAnimatedAction<EntityRathalos> {

	public static final String ANIMATION = "mhfc:models/Rathalos/RathalosBiteLeft.mcanm";
	public static final int LAST_FRAME = 40;
	public static final float WEIGHT = 3.0f;
	public static final float ANGLE = 20f;
	public static final float MAX_DISTANCE = 5f;

	public BiteAttack() {
		super(generateProvider());
		setAnimation(ANIMATION);
		setLastFrame(LAST_FRAME);
	}

	private static IAnimatedActionProvider<EntityRathalos> generateProvider() {
		IAnimationProvider animationProvider = new IAnimationProvider.AnimationAdapter(ANIMATION, LAST_FRAME);
		ISelectionPredicate<EntityRathalos> selectionProvider = new ISelectionPredicate.SelectionAdapter<>(
				-ANGLE,
				ANGLE,
				0,
				MAX_DISTANCE);
		IWeightProvider<EntityRathalos> weightProvider = new IWeightProvider.SimpleWeightAdapter<>(WEIGHT);
		return new AnimatedActionAdapter<EntityRathalos>(animationProvider, selectionProvider, weightProvider);
	}

	@Override
	public void update() {}

}
