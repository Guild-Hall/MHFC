package mhfc.net.common.ai.general.provider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.Vec3;

public interface IPathProvider<EntityT extends EntityLiving> {

	public void initialize(EntityT actor, Entity target);

	public Vec3 getCurrentWaypoint(EntityT actor, Entity target);

	/**
	 * Returns the maximum distance the entity can be away from the way point to
	 * be considered as having reached the way point.
	 */
	public double getProximityDistance(EntityT actor, Entity target);

	public void onWaypointReached(EntityT actor, Entity target);

}
