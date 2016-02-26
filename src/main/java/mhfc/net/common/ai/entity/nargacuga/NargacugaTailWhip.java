package mhfc.net.common.ai.entity.nargacuga;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralTailWhip;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;

public class NargacugaTailWhip extends AIGeneralTailWhip<EntityNargacuga> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/TailSwipeRight.mcanm";
	private static final int ANIMATION_LENGTH = 0;
	private static final float WEIGHT = 2;
	private static final IDamageCalculator CALCULATOR = AIUtils.defaultDamageCalc(20, 40, 200);
	private static final float MIN_ANGLE = 0;
	private static final float MAX_ANGLE = -150;
	private static final float MAX_DISTANCE = 4;

	public static ISpinProvider<EntityNargacuga> generateProvider() {

		IAnimationProvider anim = new IAnimationProvider.AnimationAdapter(ANIMATION_LOCATION, ANIMATION_LENGTH);
		IWeightProvider<EntityNargacuga> weight = new IWeightProvider.SimpleWeightAdapter<EntityNargacuga>(WEIGHT);
		IDamageProvider damage = new IDamageProvider.DamageAdapter(CALCULATOR);
		ISelectionPredicate<EntityNargacuga> predicate = new ISelectionPredicate.SelectionAdapter<>(
				MIN_ANGLE,
				MAX_ANGLE,
				0,
				MAX_DISTANCE);
		TailWhipAdapter<EntityNargacuga> adapter = new TailWhipAdapter<>(anim, weight, damage, predicate);
		return adapter;
	}

	public NargacugaTailWhip() {
		super(generateProvider());
	}

}
