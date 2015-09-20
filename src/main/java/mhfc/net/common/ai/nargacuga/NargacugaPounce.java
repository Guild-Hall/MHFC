package mhfc.net.common.ai.nargacuga;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralJumpAttack;
import mhfc.net.common.ai.general.provider.*;
import mhfc.net.common.entity.mob.EntityNargacuga;

public final class NargacugaPounce extends AIGeneralJumpAttack<EntityNargacuga> {

	private static final int TURN_SLOW = 2;
	private static final int TURN_FAST = 10;

	private static final IDamageCalculator dmgCalculator = AIUtils
		.defaultDamageCalc(28, 300, 5000);

	private static class NargaJumpAdapter extends JumpAdapter<EntityNargacuga> {

		public NargaJumpAdapter(IAnimationProvider animProvider,
			ISelectionPredicate<EntityNargacuga> predicate,
			IWeightProvider<EntityNargacuga> weightProvider,
			IDamageProvider damageProvider,
			IJumpParamterProvider<EntityNargacuga> jumpProvider) {
			super(animProvider, predicate, weightProvider, damageProvider,
				jumpProvider, new JumpTimingAdapter<EntityNargacuga>(0, 0));
		}

		public void setDynamicTimingAdapter(NargaJumpTiming params) {
			this.jumpTiming = params;
		}

	}

	public static NargacugaPounce createNargaPounce(ProwlerStance prowler) {
		IAnimationProvider animation = new IAnimationProvider.AnimationAdapter(
			"", 5); // We provide it dynamically
		ISelectionPredicate<EntityNargacuga> select = new ISelectionPredicate.SpecificLastActionAdapter<EntityNargacuga>(
			prowler);
		IDamageProvider damage = new IDamageProvider.DamageAdapter(
			dmgCalculator);
		NargaJumpAdapter adapter = new NargaJumpAdapter(animation, select,
			null, damage, null);
		NargacugaPounce pounce = new NargacugaPounce(adapter);
		adapter.setDynamicTimingAdapter(pounce.new NargaJumpTiming());
		return pounce;

	}

	private class NargaJumpTiming
		implements
			IJumpTimingProvider<EntityNargacuga> {

		@Override
		public boolean isJumpFrame(EntityNargacuga entity, int frame) {
			return variation.isJumpFrame(entity, frame);
		}

		@Override
		public boolean isDamageFrame(EntityNargacuga entity, int frame) {
			return variation.isDamageFrame(entity, frame);
		}

		@Override
		public float getTurnRate(EntityNargacuga entity, int frame) {
			return variation.getTurnRate(entity, frame);
		}

	}

	@SuppressWarnings("unused")
	private enum JumpVariation implements IJumpTimingProvider<EntityNargacuga> {
		TwoJumps() {
			private final int JUMP_TIME = 10;
			private final int JUMP1_FRAME = 28;
			private final int LAND1_FRAME = 38;
			private final int JUMP2_FRAME = 47;
			private final int LAND2_FRAME = 57;

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
					return TURN_SLOW;
				else if (frame > LAND1_FRAME && frame < JUMP2_FRAME)
					return TURN_SLOW;
				else
					return TURN_FAST;
			}

			@Override
			public String getAnimation() {
				return "mhfc:models/Nargacuga/Pounce.mcanm";
			}

		},
		ThreeJumps() {
			@Override
			public boolean isJumpFrame(EntityNargacuga entity, int frame) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isDamageFrame(EntityNargacuga entity, int frame) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public float getTurnRate(EntityNargacuga entity, int frame) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getAnimation() {
				// TODO Auto-generated method stub
				return null;
			}
		},
		FourJumps() {
			@Override
			public boolean isJumpFrame(EntityNargacuga entity, int frame) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isDamageFrame(EntityNargacuga entity, int frame) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public float getTurnRate(EntityNargacuga entity, int frame) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getAnimation() {
				// TODO Auto-generated method stub
				return null;
			}
		};

		public abstract String getAnimation();

	}

	// How many jumps Nargacuga want to make
	private JumpVariation variation;

	private NargacugaPounce(IJumpProvider<EntityNargacuga> provider) {
		super(provider);
	}

	private void chooseAttackIterations() {
		// FIXME choose the right variation
		if (getEntity().isEnraged()) {
			variation = JumpVariation.FourJumps;
		} else {
			variation = JumpVariation.TwoJumps;
		}
		setAnimation(variation.getAnimation());
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		chooseAttackIterations();
	}

}
