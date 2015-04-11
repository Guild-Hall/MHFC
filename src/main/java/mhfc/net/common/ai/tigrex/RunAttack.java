package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.AIUtils;
import mhfc.net.common.ai.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.Vec3;

public class RunAttack extends AttackAdapter<EntityTigrex> {
	private static final int runningStarts = 21;
	private static final int runningEnds = 56;
	private static final int attackEnd = 75;
	private static final float TURN_RATE_INITIAL = 7;
	private static final float TURN_RATE_DURING_RUN = 1;
	private static final IDamageCalculator damageCalc = AIUtils
		.defaultDamageCalc(16f, 62f, 400f);

	private static enum PastEntityEnum {
		NOT_PASSED,
		PASSED,
		LOOP_FINISHED;
	}

	private static enum AttackPhase {
		START(false) {

			@Override
			public void onPhaseStart(RunAttack attk) {
				EntityCreature entity = attk.getEntity();
				entity.motionX = entity.motionY = entity.motionZ = 0f;
				attk.getEntity().getTurnHelper().updateTurnSpeed(
					TURN_RATE_INITIAL);
			}

			@Override
			public void update(RunAttack attk) {
				Entity target = attk.target;
				attk.getEntity().getTurnHelper().updateTargetPoint(target);
			}

			@Override
			public AttackPhase next(RunAttack attk) {
				if (attk.target == null)
					return STOPPED;
				if (attk.getRecentFrame() < runningStarts) {
					return START;
				}
				return RUNNING;
			}
		},
		RUNNING(true) {
			@Override
			public void onPhaseStart(RunAttack attk) {
				attk.getEntity().getTurnHelper().updateTurnSpeed(
					TURN_RATE_DURING_RUN);
			}

			@Override
			public void update(RunAttack attk) {
				EntityTigrex e = attk.getEntity();
				Vec3 vecToTarget = Vec3.createVectorHelper(e.posX, e.posY,
					e.posZ).subtract(attk.target.getPosition(1.0f));
				e.getTurnHelper().updateTargetPoint(attk.target);
				Vec3 look = e.getLookVec();
				e.getMoveHelper().setMoveTo(e.posX + 3 * look.xCoord,
					e.posY + 3 * look.yCoord, e.posZ + 3 * look.zCoord, 1.4);
				if (vecToTarget.normalize().dotProduct(look) > 0
					&& attk.hasPassed == PastEntityEnum.NOT_PASSED) {
					attk.hasPassed = PastEntityEnum.PASSED;
				}
			}

			@Override
			public AttackPhase next(RunAttack attk) {
				if (attk.hasPassed == PastEntityEnum.LOOP_FINISHED) {
					return STOPPING;
				}
				return RUNNING;
			}

			@Override
			public int nextFrame(RunAttack attk, int curr) {
				int looping = runningEnds - runningStarts;
				if (attk.hasPassed == PastEntityEnum.PASSED
					&& (curr + 1 - runningStarts) >= looping) {
					attk.hasPassed = PastEntityEnum.LOOP_FINISHED;
				}
				return runningStarts + (curr + 1 - runningStarts) % looping;
			}
		},
		STOPPING(true) {
			@Override
			public void update(RunAttack attk) {
				EntityCreature e = attk.getEntity();
				Vec3 look = e.getLookVec();
				e.getMoveHelper().setMoveTo(e.posX + 3 * look.xCoord,
					e.posY + 3 * look.yCoord, e.posZ + 3 * look.zCoord, 0.5);
			}

			@Override
			public AttackPhase next(RunAttack attk) {
				if (attackEnd < attk.getRecentFrame())
					return STOPPED;
				return STOPPING;
			}
		},
		STOPPED(false) {
			@Override
			public void onPhaseStart(RunAttack attk) {
				Entity entity = attk.getEntity();
				entity.motionX = entity.motionY = entity.motionZ = 0f;
			}
		};
		public final boolean isDamaging;

		private AttackPhase(boolean isDamaging) {
			this.isDamaging = isDamaging;
		}

		public void onPhaseStart(RunAttack attk) {
		}

		public void update(RunAttack attk) {
		}

		public AttackPhase next(RunAttack attk) {
			return this;
		}

		public int nextFrame(RunAttack attk, int curr) {
			return ++curr;
		}
	}

	private AttackPhase currentPhase;
	private PastEntityEnum hasPassed;

	public RunAttack() {
		setAnimation("mhfc:models/Tigrex/run_new.mcanm");
	}

	@Override
	public float getWeight() {
		EntityTigrex tigrex = this.getEntity();
		target = tigrex.getAttackTarget();
		if (target == null)
			return DONT_SELECT;
		Vec3 toTarget = WorldHelper.getVectorToTarget(tigrex, target);
		double dist = toTarget.lengthVector();
		return (float) Math.log(dist); // More likely the farer away
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		target = getEntity().getAttackTarget();
		currentPhase = AttackPhase.START;
		hasPassed = PastEntityEnum.NOT_PASSED;
		currentPhase.onPhaseStart(this);
	}

	@Override
	public void update() {
		currentPhase.update(this);
		if (currentPhase.isDamaging) {
			AIUtils.damageCollidingEntities(getEntity(), damageCalc);
		}
		AttackPhase nextPhase = currentPhase.next(this);
		if (currentPhase != nextPhase) {
			nextPhase.onPhaseStart(this);
			currentPhase = nextPhase;
		}
	}

	@Override
	public boolean shouldContinue() { // To determine if to cancel
		return this.currentPhase != AttackPhase.STOPPED;
	}

	@Override
	public void finishExecution() { // When finished
		this.getEntity().setTarget(null);
	}

	@Override
	public byte mutexBits() { // Well known mutex bits
		return 1;
	}

	@Override
	public int getNextFrame(int frame) { // For the animation
		super.getNextFrame(frame); // Notify the adapter
		return currentPhase.nextFrame(this, frame);
	}
}
