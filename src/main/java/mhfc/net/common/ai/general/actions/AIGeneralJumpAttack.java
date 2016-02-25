package mhfc.net.common.ai.general.actions;

import java.util.Objects;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.IJumpParamterProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class AIGeneralJumpAttack<EntityT extends EntityMHFCBase<? super EntityT>> extends AIAnimatedAction<EntityT> {

	public static interface IJumpTimingProvider<EntityT extends EntityMHFCBase<? super EntityT>> {

		/**
		 * Returns the frame at which the monster should perform the jump
		 */
		public boolean isJumpFrame(EntityT entity, int frame);

		public boolean isDamageFrame(EntityT entity, int frame);

		public float getTurnRate(EntityT entity, int frame);

		public static class JumpTimingAdapter<EntityT extends EntityMHFCBase<? super EntityT>>
				implements
				IJumpTimingProvider<EntityT> {
			protected int jumpFrame;
			protected float turnRate, turnRateAir;

			public JumpTimingAdapter(int jumpFrame, float turnRate, float turnRateAir) {
				this.jumpFrame = jumpFrame;
				this.turnRate = turnRate;
				this.turnRateAir = turnRateAir;
			}

			@Override
			public boolean isJumpFrame(EntityT entity, int frame) {
				return frame == jumpFrame;
			}

			@Override
			public float getTurnRate(EntityT entity, int frame) {
				return frame > jumpFrame ? turnRateAir : turnRate;
			}

			@Override
			public boolean isDamageFrame(EntityT entity, int frame) {
				return frame > jumpFrame;
			}

		}
	}

	public static interface IJumpProvider<EntityT extends EntityMHFCBase<? super EntityT>>
			extends
			IAnimatedActionProvider<EntityT>,
			IDamageProvider,
			IJumpParamterProvider<EntityT>,
			IJumpTimingProvider<EntityT> {

	}

	public static class JumpAdapter<EntityT extends EntityMHFCBase<? super EntityT>> implements IJumpProvider<EntityT> {
		protected IAnimationProvider animationProvider;
		protected ISelectionPredicate<EntityT> predicate;
		protected IWeightProvider<EntityT> weightProvider;
		protected IDamageProvider damageProvider;
		protected IJumpParamterProvider<EntityT> jumpProvider;
		protected IJumpTimingProvider<EntityT> jumpTiming;

		public JumpAdapter(
				IAnimationProvider animProvider,
				ISelectionPredicate<EntityT> predicate,
				IWeightProvider<EntityT> weightProvider,
				IDamageProvider damageProvider,
				IJumpParamterProvider<EntityT> jumpProvider,
				IJumpTimingProvider<EntityT> jumpTiming) {
			this.animationProvider = Objects.requireNonNull(animProvider);
			this.predicate = Objects.requireNonNull(predicate);
			this.weightProvider = Objects.requireNonNull(weightProvider);
			this.damageProvider = Objects.requireNonNull(damageProvider);
			this.jumpProvider = Objects.requireNonNull(jumpProvider);
			this.jumpTiming = Objects.requireNonNull(jumpTiming);
		}

		@Override
		public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
			return predicate.shouldSelectAttack(attack, actor, target);
		}

		@Override
		public float getInitialUpVelocity(EntityT entity) {
			return jumpProvider.getInitialUpVelocity(entity);
		}

		@Override
		public float getForwardVelocity(EntityT entity) {
			return jumpProvider.getForwardVelocity(entity);
		}

		@Override
		public String getAnimationLocation() {
			return animationProvider.getAnimationLocation();
		}

		@Override
		public int getAnimationLength() {
			return animationProvider.getAnimationLength();
		}

		@Override
		public float getWeight(EntityT entity, Entity target) {
			return weightProvider.getWeight(entity, target);
		}

		@Override
		public IDamageCalculator getDamageCalculator() {
			return damageProvider.getDamageCalculator();
		}

		@Override
		public boolean isJumpFrame(EntityT entity, int frame) {
			return jumpTiming.isJumpFrame(entity, frame);
		}

		@Override
		public boolean isDamageFrame(EntityT entity, int frame) {
			return jumpTiming.isDamageFrame(entity, frame);
		}

		@Override
		public float getTurnRate(EntityT entity, int frame) {
			return jumpTiming.getTurnRate(entity, frame);
		}

	}

	protected IJumpProvider<EntityT> provider;

	public AIGeneralJumpAttack(IJumpProvider<EntityT> provider) {
		super(provider);
		this.provider = provider;
		dmgHelper.setDamageCalculator(provider.getDamageCalculator());
	}

	@Override
	public float getWeight() {
		EntityT entity = this.getEntity();
		target = entity.getAttackTarget();
		if (provider.shouldSelectAttack(this, entity, target)) {
			return provider.getWeight(entity, target);
		} else {
			return DONT_SELECT;
		}
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		getEntity().getTurnHelper().updateTurnSpeed(provider.getTurnRate(getEntity(), 0));
	}

	@Override
	public void update() {

		EntityT entity = getEntity();
		Vec3 look = entity.getLookVec();
		Vec3 forward = Vec3.createVectorHelper(look.xCoord, 0, look.zCoord).normalize();
		int frame = getCurrentFrame();
		float turnRate = provider.getTurnRate(entity, frame);
		if (turnRate > 0) {
			entity.getTurnHelper().updateTurnSpeed(turnRate);
			entity.getTurnHelper().updateTargetPoint(getEntity().getAttackTarget());
		}
		if (provider.isJumpFrame(entity, frame)) {
			float upVelocity = provider.getInitialUpVelocity(entity);
			float forwardVelocity = provider.getForwardVelocity(entity);
			upVelocity = Math.min(upVelocity, 20);
			forwardVelocity = Math.min(forwardVelocity, 20f);
			entity.motionX = forward.xCoord * forwardVelocity;
			entity.motionY = upVelocity;
			entity.motionZ = forward.zCoord * forwardVelocity;
			entity.isAirBorne = true;
		}
		if (provider.isDamageFrame(entity, frame)) {
			AIUtils.damageCollidingEntities(getEntity(), dmgHelper.getCalculator());
		}
	}

}
