package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityTigrex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class Run extends ActionAdapter<EntityTigrex> {
	private static final int runningStarts = 21;
	private static final int runningEnds = 60;
	private static final int attackEnd = 75;
	private static final float TURN_RATE_INITIAL = 10.5f;
	private static final float TURN_RATE_DURING_RUN = 0.17f;
	private static final float MAX_RUN_DISTANCE = 40f;
	private static final int MAX_RUN_FRAMES = 200;

	private static final double RUN_SPEED = 1.7;
	private static final double STOP_SPEED = 0.7;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(120f, 50F, 99999F);

	private static enum PastEntityEnum {
		NOT_PASSED,
		PASSED,
		LOOP_FINISHED,
		TURNING;
	}

	private static enum AttackPhase {
		START(false) {

			@Override
			public void onPhaseStart(Run attk) {
				EntityTigrex tigrex = attk.getEntity();
				tigrex.motionX = tigrex.motionY = tigrex.motionZ = 0f;
				tigrex.getTurnHelper().updateTurnSpeed(TURN_RATE_INITIAL);
				attk.getEntity().getTurnHelper().updateTargetPoint(attk.target);
			}

			@Override
			public void update(Run attk) {
				attk.getEntity().getTurnHelper().forceUpdate();
			}

			@Override
			public AttackPhase next(Run attk) {
				if (attk.target == null) {
					return STOPPED;
				}
				if (attk.getCurrentFrame() < runningStarts) {
					return START;
				}
				return RUNNING;
			}
		},
		RUNNING(true) {
			@Override
			public void onPhaseStart(Run attk) {
				attk.getEntity().getTurnHelper().updateTurnSpeed(TURN_RATE_DURING_RUN);
				attk.framesRunning = 0;
			}

			@Override
			public void update(Run attk) {
				EntityTigrex tigrex = attk.getEntity();
				Vec3 tigPos = WorldHelper.getEntityPositionVector(tigrex);
				Vec3 vecToTarget = tigPos.subtract(WorldHelper.getEntityPositionVector(attk.target));
				tigrex.getTurnHelper().updateTargetPoint(attk.target);
				tigrex.moveForward(RUN_SPEED, true);
				Vec3 look = tigrex.getLookVec();
				boolean tarBeh = vecToTarget.normalize().dotProduct(look) < 0;
				boolean ranLongEnough = attk.runStartPoint.subtract(tigPos).lengthVector() > MAX_RUN_DISTANCE
						|| attk.framesRunning > MAX_RUN_FRAMES;
				if ((tarBeh || ranLongEnough) && attk.hasPassed == PastEntityEnum.NOT_PASSED) {
					attk.hasPassed = PastEntityEnum.PASSED;
				}
			}

			@Override
			public AttackPhase next(Run attk) {
				if (attk.hasPassed == PastEntityEnum.LOOP_FINISHED) {
					return STOPPING;
				}
				return RUNNING;
			}

			@Override
			public int nextFrame(Run attk, int curr) {
				attk.framesRunning++;
				int looping = runningEnds - runningStarts;
				if (attk.hasPassed == PastEntityEnum.PASSED && (curr + 1 >= runningEnds)) {
					attk.hasPassed = PastEntityEnum.LOOP_FINISHED;
				}
				return runningStarts + (curr + 1 - runningStarts) % looping;
			}
		},
		STOPPING(true) {
			@Override
			public void update(Run attk) {
				EntityTigrex e = attk.getEntity();
				e.moveForward(STOP_SPEED, false);
			}

			@Override
			public AttackPhase next(Run attk) {
				if (attackEnd < attk.getCurrentFrame()) {
					return STOPPED;
				}
				return STOPPING;
			}
		},
		STOPPED(false) {
			@Override
			public void onPhaseStart(Run attk) {
				Entity entity = attk.getEntity();
				entity.motionX = entity.motionY = entity.motionZ = 0f;
			}
		};
		public final boolean isDamaging;

		private AttackPhase(boolean isDamaging) {
			this.isDamaging = isDamaging;
		}

		public void onPhaseStart(Run attk) {}

		public void update(Run attk) {}

		public AttackPhase next(Run attk) {
			return this;
		}

		public int nextFrame(Run attk, int curr) {
			return ++curr;
		}
	}

	private AttackPhase currentPhase;
	private PastEntityEnum hasPassed;
	private Vec3 runStartPoint;
	private int framesRunning;
	@SuppressWarnings("unused")
	private int runCycles;

	public Run() {
		setAnimation("mhfc:models/Tigrex/run.mcanm");
	}

	@Override
	public float getWeight() {
		EntityTigrex tigrex = this.getEntity();
		target = tigrex.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3 toTarget = WorldHelper.getVectorToTarget(tigrex, target);
		double dist = toTarget.lengthVector();
		return (float) Math.log(dist / 5f + 1); // More likely the
												// further away
	}

	@Override
	public void beginExecution() {
		EntityTigrex tig = getEntity();
		target = tig.getAttackTarget();
		tig.playSound("mhfc:tigrex.charge", 2.0F, 1.0F);
		currentPhase = AttackPhase.START;
		hasPassed = PastEntityEnum.NOT_PASSED;
		runCycles = 0;
		framesRunning = 0;

		currentPhase.onPhaseStart(this);
		runStartPoint = Vec3.createVectorHelper(tig.posX, tig.posY, tig.posZ);
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
