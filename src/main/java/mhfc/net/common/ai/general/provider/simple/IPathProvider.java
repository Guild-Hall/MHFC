package mhfc.net.common.ai.general.provider.simple;

import net.minecraft.util.math.Vec3d;

public interface IPathProvider {

	public Vec3d getCurrentWaypoint();

	public boolean hasWaypointReached();

	public void onWaypointReached();

}
