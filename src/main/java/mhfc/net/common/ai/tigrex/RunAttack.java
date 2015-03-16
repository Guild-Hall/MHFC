package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.AIUtils;
import mhfc.net.common.ai.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.Vec3;

public class RunAttack extends AttackAdapter<EntityTigrex> {
	private static final int runningStarts = 21;
	private static final int runningEnds = 56;
	private static final int attackEnd = 75;
	private static final IDamageCalculator damageCalc = AIUtils
			.defaultDamageCalc(2.2f, 62f, 400f);

	private static enum PastEntityEnum {
		NOT_PASSED, PASSED, LOOP_FINISHED;
	}
	private static enum AttackPhase {
		START(false) {
			@Override
			public void onPhaseStart(RunAttack attk) {
				Entity entity = attk.getEntity();
				entity.motionX = entity.motionY = entity.motionZ = 0f;
			}
			@Override
			public void update(RunAttack attk) {
				// TODO: gradually update rotation
				attk.getEntity().rotationYaw = AIUtils
						.lookVecToYaw(attk.targetDir);
			}
			@Override
			public AttackPhase next(RunAttack attk) {
				if (attk.getRecentFrame() < runningStarts)
					return START;
				return RUNNING;
			}
		},
		RUNNING(true) {
			@Override
			public void update(RunAttack attk) {
				EntityLivingBase e = attk.getEntity();
				double speed = e.getEntityAttribute(
						SharedMonsterAttributes.movementSpeed)
						.getAttributeValue();
				Vec3 dir = attk.targetDir;
				e.motionX = dir.xCoord * speed;
				e.motionZ = dir.zCoord * speed;
				// TODO: update motion
				Entity t = attk.target;
				Vec3 eTot = Vec3.createVectorHelper(t.posX, t.posY, t.posZ);
				eTot = eTot.subtract(
						Vec3.createVectorHelper(e.posX, e.posY, e.posZ))
						.normalize();
				if (eTot.dotProduct(dir) > 0
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
		public void onPhaseStart(RunAttack attk) {}
		public void update(RunAttack attk) {}
		public AttackPhase next(RunAttack attk) {
			return this;
		}
		public int nextFrame(RunAttack attk, int curr) {
			return ++curr;
		}
	}
	private AttackPhase currentPhase;
	private PastEntityEnum hasPassed;
	private Vec3 startingDir;
	private Vec3 targetDir;

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
		this.targetDir = toTarget.normalize();
		double dist = toTarget.lengthVector();
		return (float) Math.log(dist); // More likely the farer away
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		currentPhase = AttackPhase.START;
		hasPassed = PastEntityEnum.NOT_PASSED;
		startingDir = getEntity().getLookVec();
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
