package mhfc.net.common.ai.general.provider;

import java.util.Objects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.Vec3;

public interface IMovementProvider<EntityT extends EntityLiving>
	extends
		IMoveParameterProvider,
		IPathProvider<EntityT> {

	public static class TurnThenMoveProvider<EntityT extends EntityLiving>
		implements
			IMoveParameterProvider,
			IPathProvider<EntityT> {
		IPathProvider<EntityLiving> underlyingPathProvider;
		IMoveParameterProvider moveParameterProvider;

		public TurnThenMoveProvider(
			IPathProvider<EntityLiving> underlyingPathProvider,
			IMoveParameterProvider moveParameterProvider, float allowedAngle) {
			this.underlyingPathProvider = Objects
				.requireNonNull(underlyingPathProvider);
			this.moveParameterProvider = Objects
				.requireNonNull(moveParameterProvider);
		}

		@Override
		public void initialize(EntityT actor, Entity target) {
			underlyingPathProvider.initialize(actor, target);
		}

		@Override
		public Vec3 getCurrentWaypoint(EntityT actor, Entity target) {
			return underlyingPathProvider.getCurrentWaypoint(actor, target);
		}

		@Override
		public boolean hasWaypointReached(EntityT actor, Entity target) {
			return underlyingPathProvider.hasWaypointReached(actor, target);
		}

		@Override
		public void onWaypointReached(EntityT actor, Entity target) {
			underlyingPathProvider.onWaypointReached(actor, target);
		}

		@Override
		public float getTurnRate() {
			return moveParameterProvider.getTurnRate();
		}

		@Override
		public float getMoveSpeed() {
			// FIXME implement this
			return 0;
		}
	}
}
