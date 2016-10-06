package mhfc.net.common.ai.general.provider.adapters;

import java.util.Objects;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.provider.composite.IMovementProvider;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IPathProvider;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.Vec3d;

public class TurnThenMoveAdapter<T extends EntityLiving> implements IMovementProvider {
	private IPathProvider underlyingPathProvider;
	private IMoveParameterProvider moveParameterProvider;

	private final T actor;
	private final float maxAngle;

	public TurnThenMoveAdapter(
			T actor,
			IPathProvider underlyingPathProvider,
			IMoveParameterProvider moveParameterProvider,
			float allowedAngle) {
		this.actor = actor;
		this.underlyingPathProvider = Objects.requireNonNull(underlyingPathProvider);
		this.moveParameterProvider = Objects.requireNonNull(moveParameterProvider);
		maxAngle = allowedAngle;
	}

	@Override
	public Vec3d getCurrentWaypoint() {
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
		Vec3d waypoint = getCurrentWaypoint();
		float angle = AIUtils.getViewingAngle(actor, waypoint);
		if (Math.abs(angle) <= maxAngle) {
			return moveParameterProvider.getMoveSpeed();
		} else {
			return 0f;
		}
	}
}
