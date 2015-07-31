package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.Vec3;

public class RunAttack extends AttackAdapter<EntityTigrex> {
	private static final int runningStarts = 21;
	private static final int runningEnds = 60;
	private static final int attackEnd = 75;
	private static final float TURN_RATE_INITIAL = 12;
	private static final float TURN_RATE_DURING_RUN = 2;
	private static final float MAX_RUN_DISTANCE = 40f;
	private static final int MAX_RUN_FRAMES = 200;
	private static final IDamageCalculator damageCalc = AIUtils
		.defaultDamageCalc(16f, 62f, 400f);

	private static enum PastEntityEnum {
		NOT_PASSED,
		PASSED,
		LOOP_FINISHED,
		TURNING;
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
				if (attk.getCurrentFrame() < runningStarts) {
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
				attk.framesRunning = 0;
			}

			@Override
			public void update(RunAttack attk) {
				EntityTigrex e = attk.getEntity();
				Vec3 tigPos = Vec3.createVectorHelper(e.posX, e.posY, e.posZ);
				Vec3 vecToTarget = tigPos.subtract(attk.target
					.getPosition(1.0f));
				e.getTurnHelper().updateTargetPoint(attk.target);
				Vec3 look = e.getLookVec();
				e.getMoveHelper().setMoveTo(e.posX + 3 * look.xCoord,
					e.posY + 3 * look.yCoord, e.posZ + 3 * look.zCoord, 1.4);
				boolean tarBeh = vecToTarget.normalize().dotProduct(look) < 0;
				boolean ranLongEnough = attk.runStartPoint.subtract(tigPos)
					.lengthVector() > MAX_RUN_DISTANCE
					|| attk.framesRunning > MAX_RUN_FRAMES;
				if ((tarBeh || ranLongEnough)
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
				attk.framesRunning++;
				int looping = runningEnds - runningStarts;
				if (attk.hasPassed == PastEntityEnum.PASSED
					&& (curr + 1 >= runningEnds)) {
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
					e.posY + 3 * look.yCoord, e.posZ + 3 * look.zCoord, 0.8);
			}

			@Override
			public AttackPhase next(RunAttack attk) {
				if (attackEnd < attk.getCurrentFrame())
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
	private Vec3 runStartPoint;
	private int framesRunning;
	private int runCycles;

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
		return (float) Math.log(dist / 5f + 1); // More likely the farer away
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		EntityTigrex tig = getEntity();
		target = tig.getAttackTarget();

		currentPhase = AttackPhase.START;
		hasPassed = PastEntityEnum.NOT_PASSED;
		runCycles = 0;
		framesRunning = 0;

		currentPhase.onPhaseStart(this);
		runStartPoint = Vec3.createVectorHelper(tig.posX, tig.posY, tig.posZ);
	}

	@Override
	public void update() {
		super.update();
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
		super.finishExecution();
		this.getEntity().setTarget(null);
	}

	@Override
	public byte mutexBits() { // Well known mutex bits
		return 1;
	}

	@Override
	public int setToNextFrame(int frame) { // For the animation
		return super.setToNextFrame(currentPhase.nextFrame(this, frame)); // Notify
																			// the
																			// adapter
	}
}
