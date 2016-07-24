package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.general.actions.IJumpTimingProvider;
import mhfc.net.common.ai.general.provider.simple.IAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpParamterProvider;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpParamterProvider.AttackTargetAdapter;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate.SelectAlways;
import mhfc.net.common.entity.monster.EntityNargacuga;

abstract class BehaviourJump {

	static BehaviourJump TWO_JUMPS = new BehaviourJump() {

		private static final String ANIMATION = "mhfc:models/Nargacuga/Pounce.mcanm";
		private static final int ANIMATION_LENGTH = 68;
		private static final int JUMP_TIME = 10;
		private static final float WEIGHT = 3f;
		private AttackTargetAdapter<EntityNargacuga> jumpParam = new AttackTargetAdapter<EntityNargacuga>(JUMP_TIME);
		private IAnimationProvider animationProvider = new IAnimationProvider.AnimationAdapter(
				ANIMATION,
				ANIMATION_LENGTH);
		private SelectAlways<EntityNargacuga> selectAlways = new ISelectionPredicate.SelectAlways<>();
		private IWeightProvider<EntityNargacuga> weightProvider = new IWeightProvider.SimpleWeightAdapter<>(WEIGHT);

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
					if (frame < JUMP1_FRAME)
						return TURN_FAST;
					else if (frame < LAND1_FRAME)
						return 0;
					else if (frame < JUMP2_FRAME)
						return TURN_SLOW;
					return 0;
				}
			};
		}

		@Override
		IJumpParamterProvider<EntityNargacuga> getJumpParameters() {
			jumpParam.setSpeedInterval(0, 3.5f);
			return jumpParam;
		}

		@Override
		IAnimationProvider getAnimation() {
			return animationProvider;
		}

		@Override
		ISelectionPredicate<EntityNargacuga> getSelectionPredicate() {
			return selectAlways;
		}

		@Override
		IWeightProvider<EntityNargacuga> getWeightProvider() {
			return weightProvider;
		}

	};

	static BehaviourJump THREE_JUMPS = new BehaviourJump() {

		private static final String ANIMATION = "mhfc:models/Nargacuga/Pounce.mcanm";
		private static final int ANIMATION_LENGTH = 74;
		private static final int JUMP_TIME = 13;
		private static final float WEIGHT = 1f;
		private AttackTargetAdapter<EntityNargacuga> jumpParam = new AttackTargetAdapter<EntityNargacuga>(JUMP_TIME);
		private IAnimationProvider animationProvider = new IAnimationProvider.AnimationAdapter(
				ANIMATION,
				ANIMATION_LENGTH);
		private SelectAlways<EntityNargacuga> selectAlways = new ISelectionPredicate.SelectAlways<>();
		private IWeightProvider<EntityNargacuga> weightProvider = new IWeightProvider.SimpleWeightAdapter<>(WEIGHT);

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
					if (frame < JUMP1_FRAME)
						return TURN_FAST;
					else if (frame < LAND1_FRAME)
						return 0;
					else if (frame < JUMP2_FRAME)
						return TURN_SLOW;
					else if (frame < LAND2_FRAME)
						return 0;
					else if (frame < JUMP3_FRAME)
						return TURN_SLOW;
					return 0;
				}
			};
		}

		@Override
		IJumpParamterProvider<EntityNargacuga> getJumpParameters() {
			jumpParam.setSpeedInterval(0, 2.8f);
			return jumpParam;
		}

		@Override
		IAnimationProvider getAnimation() {
			return animationProvider;
		}

		@Override
		ISelectionPredicate<EntityNargacuga> getSelectionPredicate() {
			return selectAlways;
		}

		@Override
		IWeightProvider<EntityNargacuga> getWeightProvider() {
			return weightProvider;
		}

	};
	// FIXME implement four jumps when the animation is there
	// FOUR_JUMPS() {
	// @Override
	// IJumpTimingProvider<EntityNargacuga> getJumpTiming() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// IJumpParamterProvider<EntityNargacuga> getJumpParameters() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// IAnimationProvider getAnimation() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// ISelectionPredicate<EntityNargacuga> getSelectionPredicate() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	// };
	;

	private static final int TURN_SLOW = 2;
	private static final int TURN_FAST = 5;

	abstract IJumpTimingProvider<EntityNargacuga> getJumpTiming();

	abstract IJumpParamterProvider<EntityNargacuga> getJumpParameters();

	abstract IAnimationProvider getAnimation();

	abstract ISelectionPredicate<EntityNargacuga> getSelectionPredicate();

	abstract IWeightProvider<EntityNargacuga> getWeightProvider();
}
