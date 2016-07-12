package mhfc.net.common.ai.general.provider.simple;

import java.util.Objects;

import mhfc.net.common.ai.general.AIUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.Vec3;

public interface IMovementProvider<EntityT extends EntityLiving>
		extends
		IMoveParameterProvider,
		IPathProvider<EntityT> {

	public static class TurnThenMoveAdapter<EntityT extends EntityLiving> implements IMovementProvider<EntityT> {
		private IPathProvider<EntityT> underlyingPathProvider;
		private IMoveParameterProvider moveParameterProvider;

		private EntityT actor;
		private float maxAngle;

		public TurnThenMoveAdapter(
				IPathProvider<EntityT> underlyingPathProvider,
				IMoveParameterProvider moveParameterProvider,
				float allowedAngle) {
			this.underlyingPathProvider = Objects.requireNonNull(underlyingPathProvider);
			this.moveParameterProvider = Objects.requireNonNull(moveParameterProvider);
			maxAngle = allowedAngle;
		}

		@Override
		public void initialize(EntityT actor) {
			underlyingPathProvider.initialize(actor);
			this.actor = actor;
		}

		@Override
		public Vec3 getCurrentWaypoint() {
			return underlyingPathProvider.getCurrentWaypoint();
		}

		@Override
		public boolean hasWaypointReached() {
			return underlyingPathProvider.hasWaypointReached();
		}

		@Override
		public void onWaypointReached() {
			underlyingPathProvider.onWaypointReached();
		}

		@Override
		public float getTurnRate() {
			return moveParameterProvider.getTurnRate();
		}

		@Override
		public float getMoveSpeed() {
			Vec3 waypoint = getCurrentWaypoint();
			float angle = AIUtils.getViewingAngle(actor, waypoint);
			if (Math.abs(angle) <= maxAngle) {
				return moveParameterProvider.getMoveSpeed();
			} else {
				return 0f;
			}
		}
	}
}
