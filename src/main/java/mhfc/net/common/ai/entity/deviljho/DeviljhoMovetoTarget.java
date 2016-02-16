package mhfc.net.common.ai.entity.deviljho;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class DeviljhoMovetoTarget extends ActionAdapter<EntityDeviljho> {
	private static final int runningStarts = 5;
	private static final int runningEnds = 40;
	private static final int attackEnd = 40;
	private static final float TURN_RATE_INITIAL = 20.5f;
	private static final float TURN_RATE_DURING_RUN = 1.05f;
	private static final float MAX_RUN_DISTANCE = 20f;
	private static final int MAX_RUN_FRAMES = 200;

	private static final double RUN_SPEED = 0.6;
	private static final double STOP_SPEED = 0.4;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(47f, 92f, 1000f);
	private static final double MAX_DIST = 3f;

	private static enum PastEntityEnum {
		NOT_PASSED,
		PASSED,
		LOOP_FINISHED,
		TURNING;
	}

	private static enum AttackPhase {
		START(false) {

			@Override
			public void onPhaseStart(DeviljhoMovetoTarget attk) {
				EntityDeviljho monster = attk.getEntity();
				monster.motionX = monster.motionY = monster.motionZ = 0f;
				monster.getTurnHelper().updateTurnSpeed(TURN_RATE_INITIAL);
				attk.getEntity().getTurnHelper().updateTargetPoint(attk.target);
			}

			@Override
			public void update(DeviljhoMovetoTarget attk) {
				attk.getEntity().getTurnHelper().forceUpdate();
			}

			@Override
			public AttackPhase next(DeviljhoMovetoTarget attk) {
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
			public void onPhaseStart(DeviljhoMovetoTarget attk) {
				attk.getEntity().getTurnHelper().updateTurnSpeed(TURN_RATE_DURING_RUN);
				attk.framesRunning = 0;
			}

			@Override
			public void update(DeviljhoMovetoTarget attk) {
				EntityDeviljho monster = attk.getEntity();
				Vec3 mobPos = Vec3.createVectorHelper(monster.posX, monster.posY, monster.posZ);
				Vec3 vecToTarget = mobPos.subtract(WorldHelper.getEntityVector(attk.target));
				monster.getTurnHelper().updateTargetPoint(attk.target);
				monster.moveForward(RUN_SPEED, true);
				Vec3 look = monster.getLookVec();
				boolean tarBeh = vecToTarget.normalize().dotProduct(look) < 0;
				boolean ranLongEnough = attk.runStartPoint.subtract(mobPos).lengthVector() > MAX_RUN_DISTANCE
						|| attk.framesRunning > MAX_RUN_FRAMES;
				if ((tarBeh || ranLongEnough) && attk.hasPassed == PastEntityEnum.NOT_PASSED) {
					attk.hasPassed = PastEntityEnum.PASSED;
				}
			}

			@Override
			public AttackPhase next(DeviljhoMovetoTarget attk) {
				if (attk.hasPassed == PastEntityEnum.LOOP_FINISHED) {
					return STOPPING;
				}
				return RUNNING;
			}

			@Override
			public int nextFrame(DeviljhoMovetoTarget attk, int curr) {
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
			public void update(DeviljhoMovetoTarget attk) {
				EntityDeviljho e = attk.getEntity();
				e.moveForward(STOP_SPEED, false);
			}

			@Override
			public AttackPhase next(DeviljhoMovetoTarget attk) {
				if (attackEnd < attk.getCurrentFrame()) {
					return STOPPED;
				}
				return STOPPING;
			}
		},
		STOPPED(false) {
			@Override
			public void onPhaseStart(DeviljhoMovetoTarget attk) {
				Entity entity = attk.getEntity();
				entity.motionX = entity.motionY = entity.motionZ = 0f;
			}
		};
		public final boolean isDamaging;

		private AttackPhase(boolean isDamaging) {
			this.isDamaging = isDamaging;
		}

		public void onPhaseStart(DeviljhoMovetoTarget attk) {}

		public void update(DeviljhoMovetoTarget attk) {}

		public AttackPhase next(DeviljhoMovetoTarget attk) {
			return this;
		}

		public int nextFrame(DeviljhoMovetoTarget attk, int curr) {
			return ++curr;
		}
	}

	private AttackPhase currentPhase;
	private PastEntityEnum hasPassed;
	private Vec3 runStartPoint;
	private int framesRunning;
	private int runCycles;

	public DeviljhoMovetoTarget() {
		setAnimation("mhfc:models/Deviljho/DeviljhoWalk.mcanm");
	}

	@Override
	public float getWeight() {
		EntityDeviljho monster = this.getEntity();
		target = monster.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}

		Vec3 toTarget = WorldHelper.getVectorToTarget(monster, target);
		double dist = toTarget.lengthVector();
		if (dist < MAX_DIST) {
			return DONT_SELECT;
		}
		return (float) Math.log(dist / 5f + 1); // More likely the further away
	}

	@Override
	public void beginExecution() {
		EntityDeviljho mob = getEntity();
		target = mob.getAttackTarget();
		mob.playSound("mhfc:deviljho.roar", 1.0F, 1.0F);
		currentPhase = AttackPhase.START;
		hasPassed = PastEntityEnum.NOT_PASSED;
		runCycles = 0;
		framesRunning = 0;

		currentPhase.onPhaseStart(this);
		runStartPoint = Vec3.createVectorHelper(mob.posX, mob.posY, mob.posZ);
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
