package mhfc.net.common.ai.entity.nargacuga;

import java.util.Objects;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralJumpAttack;
import mhfc.net.common.ai.general.provider.*;
import mhfc.net.common.ai.general.provider.IJumpParamterProvider.AttackTargetAdapter;
import mhfc.net.common.entity.monster.EntityNargacuga;

public final class NargacugaPounce extends AIGeneralJumpAttack<EntityNargacuga> {

	private static final int TURN_SLOW = 2;
	private static final int TURN_FAST = 10;

	private static final float WEIGHT = 3.0f;

	private static final IDamageCalculator dmgCalculator = AIUtils
		.defaultDamageCalc(28, 300, 5000);

	private static class NargaJumpAdapter extends JumpAdapter<EntityNargacuga> {

		public NargaJumpAdapter(IAnimationProvider animProvider,
			ISelectionPredicate<EntityNargacuga> predicate,
			IWeightProvider<EntityNargacuga> weightProvider,
			IDamageProvider damageProvider) {
			super(animProvider, predicate, weightProvider, damageProvider,
				new IJumpParamterProvider.AttackTargetAdapter<EntityNargacuga>(
					1.0f), new JumpTimingAdapter<EntityNargacuga>(0, 0));
		}

		public void setDynamicTimingAdapter(NargaJumpTiming timing) {
			this.jumpTiming = Objects.requireNonNull(timing);
		}

		public void setDynamicJumpParameterAdapter(NargaJumpParameter param) {
			this.jumpProvider = Objects.requireNonNull(param);
		}

	}

	public static NargacugaPounce createNargaPounce() {
		IAnimationProvider animation = new IAnimationProvider.AnimationAdapter(
			"", 5); // We provide it dynamically
		ISelectionPredicate<EntityNargacuga> select = new ISelectionPredicate.SelectAlways<>();
		IWeightProvider<EntityNargacuga> weight = new IWeightProvider.SimpleWeightAdapter<>(
			WEIGHT);
		IDamageProvider damage = new IDamageProvider.DamageAdapter(
			dmgCalculator);
		NargaJumpAdapter adapter = new NargaJumpAdapter(animation, select,
			weight, damage);
		NargacugaPounce pounce = new NargacugaPounce(adapter);
		adapter.setDynamicTimingAdapter(pounce.new NargaJumpTiming());
		adapter.setDynamicJumpParameterAdapter(pounce.new NargaJumpParameter());
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

	private class NargaJumpParameter
		extends
			AttackTargetAdapter<EntityNargacuga> {

		public NargaJumpParameter() {
			super(1.0f);
		}

		private void updateAirTime() {
			this.airTime = NargacugaPounce.this.variation
				.getAirTime(NargacugaPounce.this.getCurrentFrame());
		}

		@Override
		public float getForwardVelocity(EntityNargacuga entity) {
			updateAirTime();
			return super.getForwardVelocity(entity);
		}

		@Override
		public float getInitialUpVelocity(EntityNargacuga entity) {
			updateAirTime();
			return super.getInitialUpVelocity(entity);
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

			@Override
			public int getAirTime(int frame) {
				return JUMP_TIME;
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

			@Override
			public int getAirTime(int frame) {
				// TODO Auto-generated method stub
				return 0;
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

			@Override
			public int getAirTime(int frame) {
				// TODO Auto-generated method stub
				return 0;
			}
		};

		public abstract String getAnimation();

		/**
		 * Gets the air time for the next jump that occurs after the frame given
		 */
		public abstract int getAirTime(int frame);

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
		setToNextFrame(17);
	}

}
