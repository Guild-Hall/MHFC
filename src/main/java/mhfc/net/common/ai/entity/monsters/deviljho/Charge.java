package mhfc.net.common.ai.entity.monsters.deviljho;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.creature.Deviljho;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Charge extends AnimatedAction<Deviljho> implements IHasAnimationProvider {

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
				Deviljho monster = attk.getEntity();
				monster.motionX = monster.motionY = monster.motionZ = 0f;
				if (attk.target != null) {
				monster.getTurnHelper().updateTurnSpeed(30.5F);
				monster.getTurnHelper().updateTargetPoint(attk.target);
				monster.getLookHelper().setLookPositionWithEntity(attk.target, 15, 15);
				}
			}

			@Override
			public void update(Charge attk) {
				Deviljho entity = attk.getEntity();
				entity.getTurnHelper().forceUpdate();
			}

			@Override
			public AttackPhase next(Charge attk) {
				if (attk.target == null) {
					return STOPPED;
				}
				if (attk.getCurrentFrame() < 5) {
					return START;
				}
				return RUNNING;
			}
		},
		RUNNING(true) {
			@Override
			public void onPhaseStart(Charge attk) {
				attk.getEntity().getTurnHelper().updateTurnSpeed(0F);
				attk.framesRunning = 0;
			}

			@Override
			public void update(Charge attk) {
				Deviljho monster = attk.getEntity();
				Vec3d mobPos = monster.getPositionVector();
				Vec3d vecToTarget = mobPos.subtract(attk.target.getPositionVector());
				monster.getTurnHelper().updateTargetPoint(attk.target);
				monster.moveForward(0.3, true);
				Vec3d look = monster.getLookVec();
				boolean tarBeh = vecToTarget.normalize().dotProduct(look) < 0;
				boolean ranLongEnough = attk.runStartPoint.subtract(mobPos).lengthVector() > 20F
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
				int looping = 44 - 5;
				if (attk.hasPassed == PastEntityEnum.PASSED && (curr + 1 >= 44)) {
					attk.hasPassed = PastEntityEnum.LOOP_FINISHED;
				}
				return 5 + (curr + 1 - 5) % looping;
			}
		},

		STOPPING(true) {

			@Override
			public void update(Charge attk) {
				Deviljho e = attk.getEntity();
				e.moveForward(0.2, false);
			}

			@Override
			public AttackPhase next(Charge attk) {
				if (40 < attk.getCurrentFrame()) {
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


	public Charge() {}

	@Override
	public float computeSelectionWeight() {
		Deviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		return 8F;
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		Deviljho mob = getEntity();
		target = mob.getAttackTarget();
		mob.playSound(MHFCSoundRegistry.getRegistry().deviljhoRoar, 1.0F, 1.0F);
		currentPhase = AttackPhase.START;
		hasPassed = PastEntityEnum.NOT_PASSED;
		setRunCycles(0);
		framesRunning = 0;

		currentPhase.onPhaseStart(this);
		runStartPoint = mob.getPositionVector();
	}

	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(100F, 500F, 8888f);

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AttackAdapter(
				new AnimationAdapter(this, "mhfc:models/Deviljho/walk.mcanm", 0),
				new DamageAdapter(DAMAGE_CALC));
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return () -> this.currentPhase != AttackPhase.STOPPED;
	}

	@Override
	public void onUpdate() {
		currentPhase.update(this);
		if (currentPhase.isDamaging) {
			AIUtils.damageCollidingEntities(getEntity(), DAMAGE_CALC);
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
