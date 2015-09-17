package mhfc.net.common.ai.general.actions;

import java.util.Objects;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.provider.*;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class AIGeneralJumpAttack<EntityT extends EntityMHFCBase<? super EntityT>>
	extends
		ActionAdapter<EntityT> {

	public static interface IJumpProvider<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
			IAnimationProvider,
			ISelectionPredicate<EntityT>,
			IWeightProvider<EntityT>,
			IDamageProvider,
			IJumpParamterProvider<EntityT> {
		/**
		 * Returns the frame at which the monster should perform the jump
		 */
		public int getJumpFrame();

		public float getTurnRate();
	}

	public static class JumpAdapter<EntityT extends EntityMHFCBase<? super EntityT>>
		implements
			IJumpProvider<EntityT> {
		private IAnimationProvider animationProvider;
		private ISelectionPredicate<EntityT> predicate;
		private IWeightProvider<EntityT> weightProvider;
		private IDamageProvider damageProvider;
		private IJumpParamterProvider<EntityT> jumpProvider;
		private int jumpFrame;
		private float turnRate;

		public JumpAdapter(IAnimationProvider animProvider,
			ISelectionPredicate<EntityT> predicate,
			IWeightProvider<EntityT> weightProvider,
			IDamageProvider damageProvider,
			IJumpParamterProvider<EntityT> jumpProvider, int jumpFrame,
			float turnRate) {
			this.animationProvider = Objects.requireNonNull(animProvider);
			this.predicate = Objects.requireNonNull(predicate);
			this.weightProvider = Objects.requireNonNull(weightProvider);
			this.damageProvider = Objects.requireNonNull(damageProvider);
			this.jumpProvider = Objects.requireNonNull(jumpProvider);

			this.jumpFrame = jumpFrame;
			this.turnRate = turnRate;
		}

		@Override
		public boolean shouldSelectAttack(
			IExecutableAction<? super EntityT> attack, EntityT actor,
			Entity target) {
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
		public int getJumpFrame() {
			return jumpFrame;
		}

		@Override
		public float getTurnRate() {
			return turnRate;
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

	}

	protected IJumpProvider<EntityT> provider;

	public AIGeneralJumpAttack(IJumpProvider<EntityT> provider) {
		this.provider = provider;
		dmgHelper.setDamageCalculator(provider.getDamageCalculator());
		setAnimation(provider.getAnimationLocation());
		setLastFrame(provider.getAnimationLength());
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
		getEntity().getTurnHelper().updateTurnSpeed(provider.getTurnRate());
	}

	@Override
	public void update() {

		Vec3 look = getEntity().getLookVec();
		int frame = getCurrentFrame();
		if (frame < provider.getJumpFrame()) {
			getEntity().getTurnHelper().updateTargetPoint(
				getEntity().getAttackTarget());
		} else if (frame == provider.getJumpFrame()) {
			EntityT entity = getEntity();
			float upVelocity = provider.getInitialUpVelocity(entity);
			float forwardVelocity = provider.getForwardVelocity(entity);
			upVelocity = Math.min(upVelocity, 20);
			forwardVelocity = Math.min(forwardVelocity, 100f);
			entity.motionX = look.xCoord * forwardVelocity;
			entity.motionY = upVelocity;
			entity.motionZ = look.zCoord * forwardVelocity;
			entity.isAirBorne = true;
		} else {
			AIUtils.damageCollidingEntities(getEntity(), dmgHelper
				.getCalculator());
		}
	}

}
