package mhfc.net.common.ai.general.provider.composite;

import java.util.Objects;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.provider.simple.IAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IMovementProvider;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public interface MovementActionProvider<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
		IAnimatedActionProvider<EntityT>,
		IContinuationPredicate<EntityT>,
		IMovementProvider<EntityT> {

	class MovementActionAdapter<EntityT extends EntityMHFCBase<? super EntityT>>
			implements
			MovementActionProvider<EntityT> {
		private IAnimationProvider animationProvider;
		private ISelectionPredicate<EntityT> selectionPredicate;
		private IContinuationPredicate<EntityT> continuationPredicate;
		private IWeightProvider<EntityT> weightProvider;
		private IMovementProvider<EntityT> movementProvider;

		public MovementActionAdapter(
				IAnimationProvider animationProvider,
				ISelectionPredicate<EntityT> selectionPredicate,
				IContinuationPredicate<EntityT> continuationPredicate,
				IWeightProvider<EntityT> weightProvider,
				IMovementProvider<EntityT> movementProvider) {
			this.animationProvider = Objects.requireNonNull(animationProvider);
			this.selectionPredicate = Objects.requireNonNull(selectionPredicate);
			this.continuationPredicate = Objects.requireNonNull(continuationPredicate);
			this.weightProvider = Objects.requireNonNull(weightProvider);
			this.movementProvider = Objects.requireNonNull(movementProvider);
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
			return selectionPredicate.shouldSelectAttack(attack, actor, target);
		}

		@Override
		public boolean shouldContinueAction(IExecutableAction<? super EntityT> attack, EntityT actor) {
			return continuationPredicate.shouldContinueAction(attack, actor);
		}

		@Override
		public float getWeight(EntityT entity, Entity target) {
			return weightProvider.getWeight(entity, target);
		}

		@Override
		public float getTurnRate() {
			return movementProvider.getTurnRate();
		}

		@Override
		public float getMoveSpeed() {
			return movementProvider.getMoveSpeed();
		}

		@Override
		public void initialize(EntityT actor) {
			movementProvider.initialize(actor);
		}

		@Override
		public Vec3 getCurrentWaypoint() {
			return movementProvider.getCurrentWaypoint();
		}

		@Override
		public boolean hasWaypointReached() {
			return movementProvider.hasWaypointReached();
		}

		@Override
		public void onWaypointReached() {
			movementProvider.onWaypointReached();
		}

	}
}
