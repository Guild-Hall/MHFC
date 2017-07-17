package mhfc.net.common.ai.entity.monsters.nargacuga;

import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackTargetAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;

abstract class BehaviourJump {

	static BehaviourJump createTwoJumps(AnimatedAction<?> action) {
		return new BehaviourJump() {
			private static final String ANIMATION = "mhfc:models/Nargacuga/Pounce.mcanm";
			private static final int ANIMATION_LENGTH = 68;
			private static final int JUMP_TIME = 10;
			private AttackTargetAdapter<EntityNargacuga> jumpParam = new AttackTargetAdapter<>(JUMP_TIME);
			private IAnimationProvider animationProvider = new AnimationAdapter(action, ANIMATION, ANIMATION_LENGTH);

			@Override
			IJumpTimingProvider<EntityNargacuga> getJumpTiming() {
				return new IJumpTimingProvider<EntityNargacuga>() {
					private final int JUMP1_FRAME = 28;
					private final int LAND1_FRAME = 38;
					private final int JUMP2_FRAME = 47;
					// private final int LAND2_FRAME = 57;

					@Override
					public boolean isJumpFrame(EntityNargacuga entity, int frame) {
						return frame == JUMP1_FRAME || frame == JUMP2_FRAME;
					}

					@Override
					public boolean isDamageFrame(EntityNargacuga entity, int frame) {
						return frame > JUMP1_FRAME;
					}

					@Override
					public float getTurnRate(EntityNargacuga entity, int frame) {
						if (frame < JUMP1_FRAME) {
							return TURN_FAST;
						} else if (frame < LAND1_FRAME) {
							return 0;
						} else if (frame < JUMP2_FRAME) {
							return TURN_SLOW;
						}
						return 0;
					}
				};
			}

			@Override
			IJumpParameterProvider<EntityNargacuga> getJumpParameters() {
				jumpParam.setSpeedInterval(0, 3.5f);
				return jumpParam;
			}

			@Override
			IAnimationProvider getAnimation() {
				return animationProvider;
			}
		};
	};

	static BehaviourJump createThreeJumps(AnimatedAction<?> action) {

		return new BehaviourJump() {
			private static final String ANIMATION = "mhfc:models/Nargacuga/Pounce.mcanm";
			private static final int ANIMATION_LENGTH = 74;
			private static final int JUMP_TIME = 13;
			private AttackTargetAdapter<EntityNargacuga> jumpParam = new AttackTargetAdapter<>(JUMP_TIME);
			private IAnimationProvider animationProvider = new AnimationAdapter(action, ANIMATION, ANIMATION_LENGTH);

			@Override
			IJumpTimingProvider<EntityNargacuga> getJumpTiming() {
				return new IJumpTimingProvider<EntityNargacuga>() {
					private final int JUMP1_FRAME = 28;
					private final int LAND1_FRAME = 41;
					private final int JUMP2_FRAME = 47;
					private final int LAND2_FRAME = 60;
					private final int JUMP3_FRAME = 68;

					@Override
					public boolean isJumpFrame(EntityNargacuga entity, int frame) {
						return frame == JUMP1_FRAME || frame == JUMP2_FRAME || frame == JUMP3_FRAME;
					}

					@Override
					public boolean isDamageFrame(EntityNargacuga entity, int frame) {
						return frame > JUMP1_FRAME && frame < LAND1_FRAME;
					}

					@Override
					public float getTurnRate(EntityNargacuga entity, int frame) {
						if (frame < JUMP1_FRAME) {
							return TURN_FAST;
						} else if (frame < LAND1_FRAME) {
							return 0;
						} else if (frame < JUMP2_FRAME) {
							return TURN_SLOW;
						} else if (frame < LAND2_FRAME) {
							return 0;
						} else if (frame < JUMP3_FRAME) {
							return TURN_SLOW;
						}
						return 0;
					}
				};
			}

			@Override
			IJumpParameterProvider<EntityNargacuga> getJumpParameters() {
				jumpParam.setSpeedInterval(0, 2.8f);
				return jumpParam;
			}

			@Override
			IAnimationProvider getAnimation() {
				return animationProvider;
			}

		};
	}
	// FIXME implement four jumps when the animation is there

	private static final int TURN_SLOW = 2;
	private static final int TURN_FAST = 5;

	abstract IJumpTimingProvider<EntityNargacuga> getJumpTiming();

	abstract IJumpParameterProvider<EntityNargacuga> getJumpParameters();

	abstract IAnimationProvider getAnimation();
}
