package mhfc.net.common.ai.entity.nargacuga;

import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.mob.EntityNargacuga;

public class ProwlerStance extends AIAnimatedAction<EntityNargacuga> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/Pounce.mcanm";
	private static final int ANIMATION_LENGTH = 18;
	private static final float MAX_ANGLE = 20;
	private static final float MAX_DISTANCE = 10;
	private static final float WEIGHT = 3;

	public ProwlerStance() {
		super(generateProvider());
	}

	private static IAnimatedActionProvider<EntityNargacuga> generateProvider() {
		IAnimationProvider anim = new IAnimationProvider.AnimationAdapter(
			ANIMATION_LOCATION, ANIMATION_LENGTH);
		ISelectionPredicate<EntityNargacuga> select = new ISelectionPredicate.SelectionAdapter<>(
			-MAX_ANGLE, MAX_ANGLE, 0, MAX_DISTANCE);
		IWeightProvider<EntityNargacuga> weight = new IWeightProvider.SimpleWeightAdapter<EntityNargacuga>(
			WEIGHT);

		AnimatedActionAdapter<EntityNargacuga> adapter = new AnimatedActionAdapter<>(
			anim, select, weight);
		return adapter;
	}

	@Override
	protected void update() {
	}

}
