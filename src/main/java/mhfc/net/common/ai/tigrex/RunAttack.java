package mhfc.net.common.ai.tigrex;

import java.util.List;

import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import mhfc.net.common.entity.type.EntityWyvernPeaceful;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import com.github.worldsender.mcanm.client.model.mhfcmodel.animation.stored.AnimationRegistry;

public class RunAttack extends AttackAdapter<EntityTigrex> {
	private static final int runningStarts = 21;
	private static final int runningEnds = 56;
	private static final int attackEnd = 100;
	private static enum AttackPhase {
		START(false) {
			@Override
			public void onPhaseStart(RunAttack attk) {
				Entity entity = attk.getEntity();
				entity.motionX = entity.motionY = entity.motionZ = 0f;
			}
			@Override
			public AttackPhase next(RunAttack attk) {
				if (attk.lastFrame < runningStarts)
					return START;
				return RUNNING;
			}
		},
		RUNNING(true) {
			@Override
			public void update(RunAttack attk) {
				attk.getEntity().setMoveForward(
						(float) attk.getEntity().getMoveHelper().getSpeed());
				// TODO: update motion
			}
			@Override
			public AttackPhase next(RunAttack attk) {
				return RUNNING;
			}
			@Override
			public int nextFrame(RunAttack attk, int curr) {
				int looping = runningEnds - runningStarts;
				return runningStarts + (curr + 1 - runningStarts) % looping;
			}
		},
		STOPPING(true) {
			@Override
			public AttackPhase next(RunAttack attk) {
				if (attackEnd < attk.lastFrame)
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
		public final boolean isDamaing;
		private AttackPhase(boolean isDamaging) {
			this.isDamaing = isDamaging;
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
	private int lastFrame;

	public RunAttack() {
		setAnimation(AnimationRegistry.loadAnimation(new ResourceLocation(
				"mhfc:models/Tigrex/run.mcanm")));
	}

	@Override
	public float getWeight() { // The bigger the value the more likely to get
								// executed
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
		lastFrame = -1;
		currentPhase = AttackPhase.START;
	}

	@Override
	public void update() {
		currentPhase.update(this);
		if (currentPhase.isDamaing) {
			EntityTigrex tigrex = getEntity();
			List<Entity> collidingEntities = WorldHelper
					.collidingEntities(tigrex);
			for (Entity trgt : collidingEntities) {
				if (trgt instanceof EntityPlayer) {
					trgt.attackEntityFrom(DamageSource.causeMobDamage(tigrex),
							2.2F);
				} else if (trgt instanceof EntityWyvernHostile
						|| trgt instanceof EntityWyvernPeaceful) {
					trgt.attackEntityFrom(DamageSource.causeMobDamage(tigrex),
							62F);
				} else {
					trgt.attackEntityFrom(DamageSource.causeMobDamage(tigrex),
							400F);
				}
			}
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
		lastFrame = frame;
		return currentPhase.nextFrame(this, frame);
	}
}
