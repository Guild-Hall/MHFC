package mhfc.net.common.ai.entity.monsters.rathalos;

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
import mhfc.net.common.entity.monster.EntityRathalos;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Rush extends DamagingAction<EntityRathalos> implements IHasAttackProvider {

	private static enum PastEntityEnum {
		NOT_PASSED,
		PASSED,
		LOOP_FINISHED,
		TURNING;
	}

	private static enum AttackPhase {
		START(false) {

			@Override
			public void onPhaseStart(Rush attk) {
				EntityRathalos entity = attk.getEntity();
				entity.motionX = entity.motionY = entity.motionZ = 0f;
				if (attk.target != null) {
					entity.getTurnHelper().updateTurnSpeed(30.5F);
					entity.getTurnHelper().updateTargetPoint(attk.target);
					entity.getLookHelper().setLookPositionWithEntity(attk.target, 15, 15);
				}
			}

			@Override
			public void update(Rush attk) {
				EntityRathalos entity = attk.getEntity();
				entity.getTurnHelper().forceUpdate();
			}

			@Override
			public AttackPhase next(Rush attk) {
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
			public void onPhaseStart(Rush attk) {
				attk.getEntity().getTurnHelper().updateTurnSpeed(0.12F);
				attk.framesRunning = 0;
			}

			@Override
			public void update(Rush attk) {

				/** Variables **/
				EntityRathalos entity = attk.getEntity();
				Vec3d entPos = entity.getPositionVector();
				Vec3d trgtPos = attk.target.getPositionVector();
				Vec3d vecToTarget = trgtPos.subtract(entPos);

				/** Processing **/
				entity.getTurnHelper().updateTargetPoint(trgtPos);
				entity.moveForward(0.8, true);
				Vec3d look = entity.getLookVec();

				boolean tarBeh = vecToTarget.normalize().dotProduct(look) < 0;

				boolean ranLongEnough = attk.runStartPoint.subtract(entPos).lengthVector() > 50f
						|| attk.framesRunning > 200;

				if ((tarBeh || ranLongEnough) && attk.hasPassed == PastEntityEnum.NOT_PASSED) {

					attk.hasPassed = PastEntityEnum.PASSED;
				}
			}

			@Override
			public AttackPhase next(Rush attk) {
				if (attk.hasPassed == PastEntityEnum.LOOP_FINISHED) {
					return STOPPING;
				}
				return RUNNING;
			}

			@Override
			public int nextFrame(Rush attk, int curr) {
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
			public void update(Rush attk) {
				EntityRathalos e = attk.getEntity();
				e.moveForward(0.4, false);
			}

			@Override
			public AttackPhase next(Rush attk) {
				if (75 < attk.getCurrentFrame()) {
					return STOPPED;
				}
				return STOPPING;
			}
		},
		STOPPED(false) {
			@Override
			public void onPhaseStart(Rush attk) {
				Entity entity = attk.getEntity();
				entity.motionX = entity.motionY = entity.motionZ = 0f;
			}
		};
		public final boolean isDamaging;

		private AttackPhase(boolean isDamaging) {
			this.isDamaging = isDamaging;
		}

		public void onPhaseStart(Rush attk) {}

		public void update(Rush attk) {}

		public AttackPhase next(Rush attk) {
			return this;
		}

		public int nextFrame(Rush attk, int curr) {
			return ++curr;
		}
	}

	private AttackPhase currentPhase;
	private PastEntityEnum hasPassed;
	private Vec3d runStartPoint;
	private int framesRunning;
	@SuppressWarnings("unused")
	private int runCycles;

	public Rush() {}

	@Override
	public float computeSelectionWeight() {
		EntityRathalos entity = getEntity();
		target = entity.getAttackingEntity();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		return 4F;
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		EntityRathalos e = getEntity();
		e.playSound(MHFCSoundRegistry.getRegistry().tigrexCharge, 2.0F, 1.0F);
		currentPhase = AttackPhase.START;
		hasPassed = PastEntityEnum.NOT_PASSED;
		runCycles = 0;
		framesRunning = 0;

		currentPhase.onPhaseStart(this);
		runStartPoint = e.getPositionVector();
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return new AttackAdapter(
				new AnimationAdapter(this, "mhfc:models/Rathalos/rathalosrush.mcanm", 65),
				new DamageAdapter(AIUtils.defaultDamageCalc(85F, 50F, 99999F)));
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return () -> this.currentPhase != AttackPhase.STOPPED;
	}

	@Override
	public void onUpdate() {
		currentPhase.update(this);
		EntityRathalos e = getEntity();
		if (currentPhase.isDamaging) {
			this.updateTurnHelper(e, 8F);
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
