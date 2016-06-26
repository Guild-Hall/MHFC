package mhfc.net.common.ai.general.provider.composite;

import java.util.Objects;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.provider.simple.IAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;

public interface IAnimatedActionProvider<EntityT extends EntityCreature>
		extends
		IAnimationProvider,
		ISelectionPredicate<EntityT>,
		IWeightProvider<EntityT> {
	public static class AnimatedActionAdapter<EntityT extends EntityCreature>
			implements
			IAnimatedActionProvider<EntityT> {

		IAnimationProvider animationProvider;
		ISelectionPredicate<EntityT> selectionProvider;
		IWeightProvider<EntityT> weightProvider;

		public AnimatedActionAdapter(
				IAnimationProvider animationProvider,
				ISelectionPredicate<EntityT> selectionProvider,
				IWeightProvider<EntityT> weightProvider) {
			this.animationProvider = Objects.requireNonNull(animationProvider);
			this.selectionProvider = Objects.requireNonNull(selectionProvider);
			this.weightProvider = Objects.requireNonNull(weightProvider);
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
			return selectionProvider.shouldSelectAttack(attack, actor, target);
		}

		@Override
		public float getWeight(EntityT entity, Entity target) {
			return weightProvider.getWeight(entity, target);
		}

	}

}
