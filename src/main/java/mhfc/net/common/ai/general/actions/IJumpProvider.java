package mhfc.net.common.ai.general.actions;

import java.util.Objects;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.simple.IAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpParamterProvider;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public interface IJumpProvider<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
		IAttackProvider<EntityT>,
		IJumpParamterProvider<EntityT>,
		IJumpTimingProvider<EntityT> {

	class JumpAdapter<EntityT extends EntityMHFCBase<? super EntityT>> implements IJumpProvider<EntityT> {
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
		public Vec3 getJumpVector(EntityT entity) {
			return jumpProvider.getJumpVector(entity);
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
}
