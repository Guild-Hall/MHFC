package mhfc.net.common.ai.entity.boss.greatjaggi;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityGreatJaggi;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class Run extends ActionAdapter<EntityGreatJaggi> {
	private static final int MOVEMENT_START_LOOP = 10;
	private static final int MOVEMENT_FINISH_LOOP = 33;
	private static final int AI_END = 85;
	private static final float TURN_RATE_INITIAL = 8.5f;
	private static final float TURN_RATE_DURING_RUN = 2.05f;
	private static final float MAX_RUN_DISTANCE = 30f;
	private static final int MAX_RUN_FRAMES = 85;

	private static final double RUN_SPEED = 0.25D;
	private static final double STOP_SPEED = 0.3D;
	private static final double REQUIRED_TARGET_MAX_DIST = 3f;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(35f, 50f, 9999999f);

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
				EntityGreatJaggi monster = attk.getEntity();
				monster.motionX = monster.motionY = monster.motionZ = 0f;
				monster.getTurnHelper().updateTurnSpeed(TURN_RATE_INITIAL);
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
				if (attk.getCurrentFrame() < MOVEMENT_START_LOOP) {
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
				EntityGreatJaggi monster = attk.getEntity();
				Vec3 entityPos = Vec3.createVectorHelper(monster.posX, monster.posY, monster.posZ);
				Vec3 vecToTarget = entityPos.subtract(WorldHelper.getEntityPositionVector(attk.target));
				monster.getTurnHelper().updateTargetPoint(attk.target);
				monster.moveForward(RUN_SPEED, true);
				Vec3 look = monster.getLookVec();
				boolean tarBeh = vecToTarget.normalize().dotProduct(look) < 0;
				boolean ranLongEnough = attk.runStartPoint.subtract(entityPos).lengthVector() > MAX_RUN_DISTANCE
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
				int looping = MOVEMENT_FINISH_LOOP - MOVEMENT_START_LOOP;
				if (attk.hasPassed == PastEntityEnum.PASSED && (curr + 1 >= MOVEMENT_FINISH_LOOP)) {
					attk.hasPassed = PastEntityEnum.LOOP_FINISHED;
				}
				return MOVEMENT_START_LOOP + (curr + 1 - MOVEMENT_START_LOOP) % looping;
			}
		},

		STOPPING(true) {

			@Override
			public void update(Run attk) {
				EntityGreatJaggi e = attk.getEntity();
				e.moveForward(STOP_SPEED, false);
			}

			@Override
			public AttackPhase next(Run attk) {
				if (AI_END < attk.getCurrentFrame()) {
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
		setAnimation("mhfc:models/GreatJaggi/GreatJaggiRun.mcanm");
	}

	@Override
	public float getWeight() {
		EntityGreatJaggi monster = this.getEntity();
		target = monster.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}

		Vec3 toTarget = WorldHelper.getVectorToTarget(monster, target);
		double dist = toTarget.lengthVector();
		if (dist < REQUIRED_TARGET_MAX_DIST) {
			return DONT_SELECT;
		}
		return (float) Math.log(dist / 5f + 1); 
	}

	@Override
	public void beginExecution() {

		EntityGreatJaggi entity = getEntity();
		target = entity.getAttackTarget();
		currentPhase = AttackPhase.START;
		hasPassed = PastEntityEnum.NOT_PASSED;
		runCycles = 0;
		entity.playLivingSound();
		framesRunning = 0;
		currentPhase.onPhaseStart(this);
		runStartPoint = Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ);
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
	public boolean shouldContinue() { 
		return this.currentPhase != AttackPhase.STOPPED;
	}

	@Override
	public void finishExecution() { 
	}

	@Override
	public byte mutexBits() { 
		return 1;
	}

	@Override
	public int setToNextFrame(int frame) { 
		return super.setToNextFrame(currentPhase.nextFrame(this, frame)); 
	}

}
