package mhfc.net.common.ai.entity.monsters.deviljho;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityDeviljho;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Charge extends AnimatedAction<EntityDeviljho> implements IHasAnimationProvider {
	private static final String ANIMATION_LOCATION = "mhfc:models/Deviljho/DeviljhoWalk.mcanm";
	private static final int MOVEMENT_START = 5;
	private static final int MOVEMENT_FINISH = 40;
	private static final int AI_END = 40;
	private static final float TURN_RATE_INITIAL = 20.5f;
	private static final float TURN_RATE_DURING_RUN = 20.05f;
	private static final float MAX_RUN_DISTANCE = 20f;
	private static final int MAX_RUN_FRAMES = 200;

	private static final double RUN_SPEED = 0.7;
	private static final double STOP_SPEED = 0.4;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(125f, 50f, 9999999f);

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
				EntityDeviljho monster = attk.getEntity();
				monster.motionX = monster.motionY = monster.motionZ = 0f;
				monster.getTurnHelper().updateTurnSpeed(TURN_RATE_INITIAL);
				attk.getEntity().getTurnHelper().updateTargetPoint(attk.target);
			}

			@Override
			public void update(Charge attk) {
				EntityDeviljho entity = attk.getEntity();
				entity.getTurnHelper().forceUpdate();
			}

			@Override
			public AttackPhase next(Charge attk) {
				if (attk.target == null) {
					return STOPPED;
				}
				if (attk.getCurrentFrame() < MOVEMENT_START) {
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
				EntityDeviljho monster = attk.getEntity();
				Vec3d mobPos = monster.getPositionVector();
				Vec3d vecToTarget = mobPos.subtract(attk.target.getPositionVector());
				monster.getTurnHelper().updateTargetPoint(attk.target);
				monster.moveForward(RUN_SPEED, true);
				Vec3d look = monster.getLookVec();
				boolean tarBeh = vecToTarget.normalize().dotProduct(look) < 0;
				boolean ranLongEnough = attk.runStartPoint.subtract(mobPos).lengthVector() > MAX_RUN_DISTANCE
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
				int looping = MOVEMENT_FINISH - MOVEMENT_START;
				if (attk.hasPassed == PastEntityEnum.PASSED && (curr + 1 >= MOVEMENT_FINISH)) {
					attk.hasPassed = PastEntityEnum.LOOP_FINISHED;
				}
				return MOVEMENT_START + (curr + 1 - MOVEMENT_START) % looping;
			}
		},

		STOPPING(true) {

			@Override
			public void update(Charge attk) {
				EntityDeviljho e = attk.getEntity();
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
	private int runCycles;

	private final IAttackProvider ATTACK;
	{
		final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, 0);
		ATTACK = new AttackAdapter(ANIMATION, new DamageAdapter(damageCalc));
	}

	public Charge() {}

	@Override
	public float computeSelectionWeight() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		return 8F;
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		EntityDeviljho mob = getEntity();
		target = mob.getAttackTarget();
		mob.playSound(MHFCSoundRegistry.getRegistry().deviljhoRoar, 1.0F, 1.0F);
		currentPhase = AttackPhase.START;
		hasPassed = PastEntityEnum.NOT_PASSED;
		setRunCycles(0);
		framesRunning = 0;

		currentPhase.onPhaseStart(this);
		runStartPoint = mob.getPositionVector();
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ATTACK;
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return () -> this.currentPhase != AttackPhase.STOPPED;
	}

	@Override
	public void onUpdate() {
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

	public int getRunCycles() {
		return runCycles;
	}

	public void setRunCycles(int runCycles) {
		this.runCycles = runCycles;
	}

}
