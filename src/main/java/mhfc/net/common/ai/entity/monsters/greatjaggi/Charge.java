package mhfc.net.common.ai.entity.monsters.greatjaggi;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.DamagingAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityGreatJaggi;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Charge extends DamagingAction<EntityGreatJaggi> implements IHasAttackProvider {
	private static final int MOVEMENT_START_LOOP = 10;
	private static final int MOVEMENT_FINISH_LOOP = 33;
	private static final int AI_END = 85;
	private static final String ANIMATION_LOCATION = "mhfc:models/GreatJaggi/GreatJaggiRun.mcanm";
	private static final float TURN_RATE_INITIAL = 8.5f;
	private static final float TURN_RATE_DURING_RUN = 2.05f;
	private static final float MAX_RUN_DISTANCE = 30f;
	private static final int MAX_RUN_FRAMES = 85;

	private static final double RUN_SPEED = 0.25D;
	private static final double STOP_SPEED = 0.3D;
	private static final double REQUIRED_TARGET_MAX_DIST = 3f;
	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(35f, 50f, 9999999f);

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
				monster.getTurnHelper().updateTurnSpeed(TURN_RATE_INITIAL);
				attk.getEntity().getTurnHelper().updateTargetPoint(attk.target);
			}

			@Override
			public void update(Charge attk) {
				attk.getEntity().getTurnHelper().forceUpdate();
			}

			@Override
			public AttackPhase next(Charge attk) {
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
			public void onPhaseStart(Charge attk) {
				attk.getEntity().getTurnHelper().updateTurnSpeed(TURN_RATE_DURING_RUN);
				attk.framesRunning = 0;
			}

			@Override
			public void update(Charge attk) {
				EntityGreatJaggi monster = attk.getEntity();
				Vec3d entityPos = monster.getPositionVector();
				Vec3d vecToTarget = entityPos.subtract(attk.target.getPositionVector());
				monster.getTurnHelper().updateTargetPoint(attk.target);
				monster.moveForward(RUN_SPEED, true);
				Vec3d look = monster.getLookVec();
				boolean tarBeh = vecToTarget.normalize().dotProduct(look) < 0;
				boolean ranLongEnough = attk.runStartPoint.subtract(entityPos).lengthVector() > MAX_RUN_DISTANCE
						|| attk.framesRunning > MAX_RUN_FRAMES;
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
				int looping = MOVEMENT_FINISH_LOOP - MOVEMENT_START_LOOP;
				if (attk.hasPassed == PastEntityEnum.PASSED && (curr + 1 >= MOVEMENT_FINISH_LOOP)) {
					attk.hasPassed = PastEntityEnum.LOOP_FINISHED;
				}
				return MOVEMENT_START_LOOP + (curr + 1 - MOVEMENT_START_LOOP) % looping;
			}
		},

		STOPPING(true) {

			@Override
			public void update(Charge attk) {
				EntityGreatJaggi e = attk.getEntity();
				e.moveForward(STOP_SPEED, false);
			}

			@Override
			public AttackPhase next(Charge attk) {
				if (AI_END < attk.getCurrentFrame()) {
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
	private final IAttackProvider ATTACK;
	{
		final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, 0);
		ATTACK = new AttackAdapter(ANIMATION, new DamageAdapter(DAMAGE_CALC));
	}

	public Charge() {}

	@Override
	public float computeSelectionWeight() {
		EntityGreatJaggi monster = this.getEntity();
		target = monster.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}

		Vec3d toTarget = WorldHelper.getVectorToTarget(monster, target);
		double dist = toTarget.lengthVector();
		if (dist < REQUIRED_TARGET_MAX_DIST) {
			return DONT_SELECT;
		}
		return (float) Math.log(dist / 5f + 1);
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		EntityGreatJaggi entity = getEntity();
		target = entity.getAttackTarget();
		currentPhase = AttackPhase.START;
		hasPassed = PastEntityEnum.NOT_PASSED;
		runCycles = 0;
		entity.playLivingSound();
		framesRunning = 0;
		currentPhase.onPhaseStart(this);
		runStartPoint = entity.getPositionVector();
	}

	@Override
	public void onUpdate() {
		currentPhase.update(this);
		if (currentPhase.isDamaging) {
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
		return ATTACK;
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
