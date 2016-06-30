package mhfc.net.common.ai.general.provider.simple;

import java.util.Objects;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.util.world.WorldHelper;
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
			Vec3 targetDir = WorldHelper.getEntityPositionVector(actor).subtract(waypoint).normalize();
			if (targetDir.xCoord == 0 && targetDir.yCoord == 0) {
				return 0f;
			}
			float angle = AIUtils.getViewingAngle(actor, getCurrentWaypoint());
			if (Math.abs(angle) <= maxAngle) {
				return moveParameterProvider.getMoveSpeed();
			} else {
				return 0f;
			}
		}
	}
}
