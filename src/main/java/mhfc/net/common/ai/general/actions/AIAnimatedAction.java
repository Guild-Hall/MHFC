package mhfc.net.common.ai.general.actions;

import java.util.Objects;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;

public abstract class AIAnimatedAction<EntityT extends EntityCreature>
	extends
		ActionAdapter<EntityT> {

	public static interface IAnimatedActionProvider<EntityT extends EntityCreature>
		extends
			IAnimationProvider,
			ISelectionPredicate<EntityT>,
			IWeightProvider<EntityT> {
	}

	public static class AnimatedActionAdapter<EntityT extends EntityCreature>
		implements
			IAnimatedActionProvider<EntityT> {

		IAnimationProvider animationProvider;
		ISelectionPredicate<EntityT> selectionProvider;
		IWeightProvider<EntityT> weightProvider;

		public AnimatedActionAdapter(IAnimationProvider animationProvider,
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
		public boolean shouldSelectAttack(
			IExecutableAction<? super EntityT> attack, EntityT actor,
			Entity target) {
			return selectionProvider.shouldSelectAttack(attack, actor, target);
		}

		@Override
		public float getWeight(EntityT entity, Entity target) {
			return weightProvider.getWeight(entity, target);
		}

	}

	private IAnimatedActionProvider<EntityT> provider;

	public AIAnimatedAction(IAnimatedActionProvider<EntityT> provider) {
		this.provider = Objects.requireNonNull(provider);
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

}
