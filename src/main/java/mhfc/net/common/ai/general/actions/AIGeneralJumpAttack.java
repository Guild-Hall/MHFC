package mhfc.net.common.ai.general.actions;

import java.util.Objects;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class AIGeneralJumpAttack<EntityT extends EntityMHFCBase<? super EntityT>>
	extends
		ActionAdapter<EntityT> {

	public static interface IJumpProvider<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
			IAnimationProvider,
			ISelectionPredicate<EntityT>,
			IWeightProvider<EntityT>,
			IDamageProvider {

		/**
		 * The velocity scale factor for the distance
		 */
		public float getDistanceScale();

		/**
		 * The maximum scale factor for the distance
		 */
		public float getMaxScale();

		/**
		 * Returns the velocity to add in y direction
		 */
		public float getJumpStrength();

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
		private float distanceScale;
		private float maxScale;
		private float jumpStrength;
		private int jumpFrame;
		private float turnRate;

		public JumpAdapter(IAnimationProvider animProvider,
			ISelectionPredicate<EntityT> predicate,
			IWeightProvider<EntityT> weightProvider,
			IDamageProvider damageProvider, float distanceScale,
			float maxScale, float jumpStrength, int jumpFrame, float turnRate) {
			this.animationProvider = Objects.requireNonNull(animProvider);
			this.predicate = Objects.requireNonNull(predicate);
			this.weightProvider = Objects.requireNonNull(weightProvider);
			this.damageProvider = Objects.requireNonNull(damageProvider);

			this.distanceScale = distanceScale;
			this.maxScale = maxScale;
			this.jumpStrength = jumpStrength;
			this.jumpFrame = jumpFrame;
			this.turnRate = turnRate;
		}

		@Override
		public boolean shouldSelectAttack(IExecutableAction<EntityT> attack,
			EntityT actor, Entity target) {
			return predicate.shouldSelectAttack(attack, actor, target);
		}

		@Override
		public float getDistanceScale() {
			return distanceScale;
		}

		@Override
		public float getMaxScale() {
			return maxScale;
		}

		@Override
		public float getJumpStrength() {
			return jumpStrength;
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
			EntityLivingBase trgt = getEntity().getAttackTarget();
			float scale = provider.getDistanceScale();
			if (trgt != null)
				scale *= getEntity().getDistanceToEntity(trgt);
			scale = Math.min(provider.getMaxScale(), scale);
			getEntity().addVelocity(look.xCoord * scale,
				provider.getJumpStrength(), look.zCoord * scale);
		} else {
			AIUtils.damageCollidingEntities(getEntity(), dmgHelper
				.getCalculator());
		}
	}

}
