package mhfc.net.common.ai.general.provider;

import mhfc.net.common.ai.general.AIUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public interface ISelectionPredicate<EntityT extends EntityLiving> {

	public boolean shouldSelectAttack(EntityT actor, Entity target);

	public static class SelectionAdapter<EntityT extends EntityLiving>
		implements
			ISelectionPredicate<EntityT> {

		private DistanceAdapter<EntityT> distanceAdapter;
		private AngleAdapter<EntityT> angleAdapter;

		/**
		 * The angles are in degrees, negative values refer to the left side,
		 * positive to the right side
		 */
		public SelectionAdapter(float minAngle, float maxAngle,
			double minDistance, double maxDistance) {
			distanceAdapter = new DistanceAdapter<>(minDistance, maxDistance);
			angleAdapter = new AngleAdapter<>(minAngle, maxAngle);
		}

		@Override
		public boolean shouldSelectAttack(EntityT actor, Entity target) {
			return distanceAdapter.shouldSelectAttack(actor, target)
				&& angleAdapter.shouldSelectAttack(actor, target);
		}
	}

	public static class DistanceAdapter<EntityT extends EntityLiving>
		implements
			ISelectionPredicate<EntityT> {

		private double minDistance, maxDistance;

		public DistanceAdapter(double minDistance, double maxDistance) {
			this.minDistance = minDistance;
			this.maxDistance = maxDistance;
		}

		@Override
		public boolean shouldSelectAttack(EntityT actor, Entity target) {
			if (target == null)
				return false;
			double distance = actor.getDistanceToEntity(target);
			return distance >= minDistance && distance <= maxDistance;
		}
	}

	public static class AngleAdapter<EntityT extends EntityLiving>
		implements
			ISelectionPredicate<EntityT> {

		private float minAngle, maxAngle;

		public AngleAdapter(float minAngle, float maxAngle) {
			this.minAngle = minAngle;
			this.maxAngle = maxAngle;
		}

		@Override
		public boolean shouldSelectAttack(EntityT actor, Entity target) {
			if (target == null)
				return false;
			float angle = AIUtils.getViewingAngle(actor, target);
			return angle >= minAngle && angle <= maxAngle;
		}

	}
}
