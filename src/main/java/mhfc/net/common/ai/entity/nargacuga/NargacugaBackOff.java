package mhfc.net.common.ai.entity.nargacuga;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralJumpAttack;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.IJumpParamterProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;

public class NargacugaBackOff extends AIGeneralJumpAttack<EntityNargacuga> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/JumpBack.mcanm";
	private static final int ANIMATION_LENGTH = 50;
	private static final float WEIGHT = 1;
	private static final IDamageCalculator CALCULATOR = AIUtils.defaultDamageCalc(10, 250, 70);
	private static final float JUMP_TIME = 12;
	private static final float ANGLE = 40;
	private static final int JUMP_FRAME = 23;
	private static final float TURN_RATE = 2.5f;
	private static final float TURN_RATE_AIR = 1.5f;

	public NargacugaBackOff() {
		super(generateProvider());
	}

	private static IJumpProvider<EntityNargacuga> generateProvider() {
		IAnimationProvider animProvider = new IAnimationProvider.AnimationAdapter(ANIMATION_LOCATION, ANIMATION_LENGTH);
		ISelectionPredicate<EntityNargacuga> predicate = new ISelectionPredicate.AngleAdapter<>(ANGLE, -ANGLE);
		IWeightProvider<EntityNargacuga> weightProvider = new IWeightProvider.SimpleWeightAdapter<EntityNargacuga>(
				WEIGHT);
		IDamageProvider damageProvider = new IDamageProvider.DamageAdapter(CALCULATOR);
		IJumpParamterProvider<EntityNargacuga> jumpProvider = new IJumpParamterProvider.AttackTargetAdapter<EntityNargacuga>(
				JUMP_TIME) {
			@Override
			public float getForwardVelocity(EntityNargacuga entity) {
				return -2.2f;
			}
		};
		IJumpTimingProvider<EntityNargacuga> jumpTiming = new IJumpTimingProvider.JumpTimingAdapter<EntityNargacuga>(
				JUMP_FRAME,
				TURN_RATE,
				TURN_RATE_AIR);
		return new JumpAdapter<EntityNargacuga>(
				animProvider,
				predicate,
				weightProvider,
				damageProvider,
				jumpProvider,
				jumpTiming);
	}

}
