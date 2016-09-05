package mhfc.net.common.util.world;

import java.util.List;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * This class should help with some general problems related with the World and Entities like path-finding,
 * target-finding and collision-detection.
 *
 * @author WorldSEnder
 */
public class WorldHelper {
	/**
	 * Returns a list of all entities that are colliding with the entity given or any sub-entities if there are any.
	 *
	 * @param entity
	 *            the entity to collide with
	 * @return all colliding entities
	 */
	public static List<Entity> collidingEntities(Entity entity) {
		return collidingEntities(entity, null);
	}

	/**
	 * Like {@link #collidingEntities(Entity)} but allows the entities to be filtered before being returned.
	 *
	 * @param entity
	 *            the entity to collide with
	 * @param filter
	 *            the filter to apply, <code>null</code> means no filtering
	 * @return all colliding entities that are not filtered
	 */
	public static List<Entity> collidingEntities(Entity entity, Predicate<? super Entity> filter) {
		World world = entity.worldObj;
		List<Entity> collidingEntities = world
				.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox(), filter);
		Entity[] subEntities = entity.getParts();
		if (subEntities == null) {
			return collidingEntities;
		}
		for (Entity subE : subEntities) {
			List<Entity> collidingEntitiesSub = world
					.getEntitiesInAABBexcluding(entity, subE.getEntityBoundingBox(), filter);
			for (Entity collidingE : collidingEntitiesSub) {
				if (!collidingEntities.contains(collidingE)) {
					collidingEntities.add(collidingE);
				}
			}
		}
		return collidingEntities;
	}

	/**
	 * Finds the vector that points from the entity to the target.
	 *
	 * @param entity
	 *            the entity from which the vector points from
	 * @param target
	 *            the entity to which the vector points to
	 * @return the vector between them
	 */
	public static Vec3d getVectorToTarget(Entity entity, Entity target) {
		Vec3d pos = getEntityPositionVector(entity);
		Vec3d entityToTarget = getEntityPositionVector(target);
		entityToTarget = pos.subtract(entityToTarget);
		return entityToTarget;
	}

	@Deprecated
	public static Vec3d getEntityPositionVector(Entity entity) {
		//FIXME: can deprecate that and just use entity.getPositionVector directly now
		return entity.getPositionVector();
	}

}
