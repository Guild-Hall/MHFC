package mhfc.net.common.ai.general.provider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.Vec3;

public interface IPathProvider<EntityT extends EntityLiving> {

	/**
	 * Resets the path provider and initializes it with the information about
	 * the actor
	 */
	public void initialize(EntityT actor, Entity target);

	public Vec3 getCurrentWaypoint(EntityT actor, Entity target);

	public boolean hasWaypointReached(EntityT actor, Entity target);

	public void onWaypointReached(EntityT actor, Entity target);

}
