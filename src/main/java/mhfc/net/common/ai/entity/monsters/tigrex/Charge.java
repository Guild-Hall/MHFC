package mhfc.net.common.ai.entity.monsters.tigrex;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.DamagingAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityTigrex;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Charge extends DamagingAction<EntityTigrex> implements IHasAttackProvider {

	private static enum PastEntityEnum {
		NOT_PASSED,
		PASSED,
		LOOP_FINISHED,
		TURNING;
	}

	private static enum AttackPhase {
		START(false) {

			@Override
			public void onPhaseStart(Charge attk) {
				EntityTigrex tigrex = attk.getEntity();
				tigrex.motionX = tigrex.motionY = tigrex.motionZ = 0f;
				if (attk.target != null) {
					tigrex.getTurnHelper().updateTurnSpeed(30.5F);
					tigrex.getTurnHelper().updateTargetPoint(attk.target);
					tigrex.getLookHelper().setLookPositionWithEntity(attk.target, 15, 15);
				}
			}

			@Override
			public void update(Charge attk) {
				EntityTigrex tigrex = attk.getEntity();
				tigrex.getTurnHelper().forceUpdate();
			}

			@Override
			public AttackPhase next(Charge attk) {
				if (attk.target == null) {
					return STOPPED;
				}
				if (attk.getCurrentFrame() < 21) {
					return START;
				}
				return RUNNING;
			}
		},
		RUNNING(true) {
			@Override
			public void onPhaseStart(Charge attk) {
				attk.getEntity().getTurnHelper().updateTurnSpeed(0.12F);
				attk.framesRunning = 0;
			}

			@Override
			public void update(Charge attk) {

				/** Variables **/
				EntityTigrex tigrex = attk.getEntity();
				Vec3d tigPos = tigrex.getPositionVector();
				Vec3d trgtPos = attk.target.getPositionVector();
				Vec3d vecToTarget = trgtPos.subtract(tigPos);

				/** Processing **/
				tigrex.getTurnHelper().updateTargetPoint(trgtPos);
				tigrex.moveForward(0.8, true);
				Vec3d look = tigrex.getLookVec();

				boolean tarBeh = vecToTarget.normalize().dotProduct(look) < 0;

				boolean ranLongEnough = attk.runStartPoint.subtract(tigPos).lengthVector() > 50f
						|| attk.framesRunning > 200;

				if ((tarBeh || ranLongEnough) && attk.hasPassed == PastEntityEnum.NOT_PASSED) {

					attk.hasPassed = PastEntityEnum.PASSED;
				}
			}

			@Override
			public AttackPhase next(Charge attk) {
				if (attk.hasPassed == PastEntityEnum.LOOP_FINISHED) {
					return STOPPING;
				}
				return RUNNING;
			}

			@Override
			public int nextFrame(Charge attk, int curr) {
				attk.framesRunning++;
				int looping = 60 - 21;
				if (attk.hasPassed == PastEntityEnum.PASSED && (curr + 1 >= 60)) {
					attk.hasPassed = PastEntityEnum.LOOP_FINISHED;
				}
				return 21 + (curr + 1 - 21) % looping;
			}
		},
		STOPPING(true) {
			@Override
			public void update(Charge attk) {
				EntityTigrex e = attk.getEntity();
				e.moveForward(0.4, false);
			}

			@Override
			public AttackPhase next(Charge attk) {
				if (75 < attk.getCurrentFrame()) {
					return STOPPED;
				}
				return STOPPING;
			}
		},
		STOPPED(false) {
			@Override
			public void onPhaseStart(Charge attk) {
				Entity entity = attk.getEntity();
				entity.motionX = entity.motionY = entity.motionZ = 0f;
			}
		};
		public final boolean isDamaging;

		private AttackPhase(boolean isDamaging) {
			this.isDamaging = isDamaging;
		}

		public void onPhaseStart(Charge attk) {}

		public void update(Charge attk) {}

		public AttackPhase next(Charge attk) {
			return this;
		}

		public int nextFrame(Charge attk, int curr) {
			return ++curr;
		}
	}

	private AttackPhase currentPhase;
	private PastEntityEnum hasPassed;
	private Vec3d runStartPoint;
	private int framesRunning;
	@SuppressWarnings("unused")
	private int runCycles;

	public Charge() {}

	@Override
	public float computeSelectionWeight() {
		EntityTigrex entity = getEntity();
		target = entity.getAttackingEntity();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		return 4F;
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		EntityTigrex tig = getEntity();
		tig.playSound(MHFCSoundRegistry.getRegistry().tigrexCharge, 2.0F, 1.0F);
		currentPhase = AttackPhase.START;
		hasPassed = PastEntityEnum.NOT_PASSED;
		runCycles = 0;
		framesRunning = 0;

		currentPhase.onPhaseStart(this);
		runStartPoint = tig.getPositionVector();
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return new AttackAdapter(
				new AnimationAdapter(this, "mhfc:models/Tigrex/run.mcanm", 0),
				new DamageAdapter(AIUtils.defaultDamageCalc(85F, 50F, 99999F)));
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return () -> this.currentPhase != AttackPhase.STOPPED;
	}

	@Override
	public void onUpdate() {
		currentPhase.update(this);
		EntityTigrex tig = getEntity();
		if (currentPhase.isDamaging) {
			this.updateTurnHelper(tig, 8F);
			damageCollidingEntities();
		}
		AttackPhase nextPhase = currentPhase.next(this);
		if (currentPhase != nextPhase) {
			nextPhase.onPhaseStart(this);
			currentPhase = nextPhase;
		}
	}

	@Override
	public void finishExecution() { // When finished
	}

	@Override
	public byte mutexBits() { // Well known mutex bits
		return 1;
	}

	@Override
	public int forceNextFrame(int frame) { // For the animation
		return super.forceNextFrame(currentPhase.nextFrame(this, frame));
	}
}
