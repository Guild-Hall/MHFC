package mhfc.net.common.ai.entity.nargacuga;

import mhfc.net.common.ai.general.actions.AIGeneralJumpAttack.IJumpTimingProvider;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IJumpParamterProvider;
import mhfc.net.common.ai.general.provider.IJumpParamterProvider.AttackTargetAdapter;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityNargacuga;

public enum NargaJumpBehaviour {

	TWO_JUMPS() {
		private static final String animation = "mhfc:models/Nargacuga/Pounce.mcanm";
		public static final int animationLength = 68;
		private final int JUMP_TIME = 10;
		private AttackTargetAdapter<EntityNargacuga> jumpParam = new AttackTargetAdapter<EntityNargacuga>(JUMP_TIME);

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
			jumpParam.setSpeedInterval(0, 5);
			return jumpParam;
		}

		@Override
		IAnimationProvider getAnimation() {
			return new IAnimationProvider.AnimationAdapter(animation, animationLength);
		}

		@Override
		ISelectionPredicate<EntityNargacuga> getSelectionPredicate() {
			return new ISelectionPredicate.SelectAlways<>();
		}

	},

	THREE_JUMPS() {

		private static final String animation = "mhfc:models/Nargacuga/Pounce.mcanm";
		public static final int animationLength = 74;
		private final int JUMP_TIME = 13;
		private AttackTargetAdapter<EntityNargacuga> jumpParam = new AttackTargetAdapter<EntityNargacuga>(JUMP_TIME);

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
			jumpParam.setSpeedInterval(0, 5);
			return jumpParam;
		}

		@Override
		IAnimationProvider getAnimation() {
			return new IAnimationProvider.AnimationAdapter(animation, animationLength);
		}

		@Override
		ISelectionPredicate<EntityNargacuga> getSelectionPredicate() {
			return new ISelectionPredicate.SelectAlways<>();
		}

	},
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
}
