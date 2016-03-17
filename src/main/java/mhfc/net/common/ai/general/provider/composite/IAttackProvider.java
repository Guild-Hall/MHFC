package mhfc.net.common.ai.general.provider.composite;

import java.util.Objects;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.provider.simple.IAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageProvider;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;

public interface IAttackProvider<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
		IAnimatedActionProvider<EntityT>,
		IDamageProvider {

	class AttackAdapter<EntityT extends EntityMHFCBase<? super EntityT>> implements IAttackProvider<EntityT> {
		protected IAnimationProvider animationProvider;
		protected ISelectionPredicate<EntityT> predicate;
		protected IWeightProvider<EntityT> weightProvider;
		protected IDamageProvider damageProvider;

		public AttackAdapter(
				IAnimationProvider animProvider,
				IWeightProvider<EntityT> weightProvider,
				ISelectionPredicate<EntityT> predicate,
				IDamageProvider damageProvider) {
			this.animationProvider = Objects.requireNonNull(animProvider);
			this.damageProvider = Objects.requireNonNull(damageProvider);
			this.weightProvider = Objects.requireNonNull(weightProvider);
			this.predicate = Objects.requireNonNull(predicate);

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
		public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
			return predicate.shouldSelectAttack(attack, actor, target);
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

}
