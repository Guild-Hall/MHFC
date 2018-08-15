package mhfc.net.common.ai.entity.monsters.greatjaggi;

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
import mhfc.net.common.entity.monster.wip.EntityGreatJaggi;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Charge extends DamagingAction<EntityGreatJaggi> implements IHasAttackProvider {

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
				EntityGreatJaggi monster = attk.getEntity();
				monster.motionX = monster.motionY = monster.motionZ = 0f;
				if (attk.target != null) {
					monster.getTurnHelper().updateTurnSpeed(50F);
					monster.getTurnHelper().updateTargetPoint(attk.target);
					monster.getLookHelper().setLookPositionWithEntity(attk.target, 15, 15);
				}
			}

			@Override
			public void update(Charge attk) {
				EntityGreatJaggi monster = attk.getEntity();
				monster.getTurnHelper().forceUpdate();
			}

			@Override
			public AttackPhase next(Charge attk) {
				if (attk.target == null) {
					return STOPPED;
				}
				if (attk.getCurrentFrame() < 10) {
					return START;
				}
				return RUNNING;
			}
		},
		RUNNING(true) {
			@Override
			public void onPhaseStart(Charge attk) {
				attk.getEntity().getTurnHelper().updateTurnSpeed(0.14F);
				attk.framesRunning = 0;
			}

			@Override
			public void update(Charge attk) {
				/** Variables **/
				EntityGreatJaggi monster = attk.getEntity();
				Vec3d entityPos = monster.getPositionVector();
				Vec3d trgtPos = attk.target.getPositionVector();
				Vec3d vecToTarget = trgtPos.subtract(entityPos);

				/** Processing **/
				monster.getTurnHelper().updateTargetPoint(trgtPos);
				monster.moveForward(0.3D, true);
				Vec3d look = monster.getLookVec();

				boolean tarBeh = vecToTarget.normalize().dotProduct(look) < 0;
				boolean ranLongEnough = attk.runStartPoint.subtract(entityPos).lengthVector() > 20F
						|| attk.framesRunning > 85;
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
				int looping = 33 - 10;
				if (attk.hasPassed == PastEntityEnum.PASSED && (curr + 1 >= 33)) {
					attk.hasPassed = PastEntityEnum.LOOP_FINISHED;
				}
				return 10 + (curr + 1 - 10) % looping;
			}
		},

		STOPPING(true) {

			@Override
			public void update(Charge attk) {
				EntityGreatJaggi e = attk.getEntity();
				e.moveForward(0.3, false);
			}

			@Override
			public AttackPhase next(Charge attk) {
				if (85 < attk.getCurrentFrame()) {
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
		EntityGreatJaggi monster = this.getEntity();
		target = monster.getAttackTarget();
		if (SelectionUtils.isIdle(monster)) {
			return DONT_SELECT;
		}
		return 4F;
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		EntityGreatJaggi entity = getEntity();
		entity.playSound(MHFCSoundRegistry.getRegistry().greatJaggiBite, 2F, 1F);
		currentPhase = AttackPhase.START;
		hasPassed = PastEntityEnum.NOT_PASSED;
		runCycles = 0;
		framesRunning = 0;
		currentPhase.onPhaseStart(this);
		runStartPoint = entity.getPositionVector();
	}

	@Override
	public void onUpdate() {
		currentPhase.update(this);
		EntityGreatJaggi entity = getEntity();
		if (currentPhase.isDamaging) {
			this.updateTurnHelper(entity, 0.03F);
			damageCollidingEntities();
		}
		AttackPhase nextPhase = currentPhase.next(this);
		if (currentPhase != nextPhase) {
			nextPhase.onPhaseStart(this);
			currentPhase = nextPhase;
		}
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return new AttackAdapter(
				new AnimationAdapter(this, "mhfc:models/GreatJaggi/GreatJaggiRun.mcanm", 0),
				new DamageAdapter(AIUtils.defaultDamageCalc(25f, 50f, 9999999f)));

	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return () -> this.currentPhase != AttackPhase.STOPPED;
	}

	@Override
	public void finishExecution() {}

	@Override
	public byte mutexBits() {
		return 1;
	}

	@Override
	public int forceNextFrame(int frame) {
		return super.forceNextFrame(currentPhase.nextFrame(this, frame));
	}

}
