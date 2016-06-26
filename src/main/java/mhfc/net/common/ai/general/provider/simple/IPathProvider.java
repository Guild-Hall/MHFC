package mhfc.net.common.ai.general.provider.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.Vec3;

public interface IPathProvider<EntityT extends EntityLiving> {

	/**
	 * Resets the path provider and initializes it with the information about the actor
	 */
	public void initialize(EntityT actor);

	public Vec3 getCurrentWaypoint();

	public boolean hasWaypointReached();

	public void onWaypointReached();

	public static class PathListAdapter<EntityT extends EntityLiving> implements IPathProvider<EntityT> {

		public static final double DEFAULT_MAX_DISTANCE = 0.25f;

		private EntityT actor;
		protected List<Vec3> path;
		protected int currentIndex;
		protected double maxDistance;

		public PathListAdapter(List<Vec3> path, double allowedDistance) {
			Objects.requireNonNull(path);
			this.path = new ArrayList<>(path.size());
			this.path.addAll(path);
			this.currentIndex = 0;
			this.maxDistance = allowedDistance;
		}

		public PathListAdapter(List<Vec3> path) {
			this(path, DEFAULT_MAX_DISTANCE);
		}

		public PathListAdapter(Vec3 nodes[], double allowedDistance) {
			Objects.requireNonNull(nodes);
			path = Arrays.asList(nodes);
			this.currentIndex = 0;
			this.maxDistance = allowedDistance;
		}

		public PathListAdapter(Vec3 nodes[]) {
			this(nodes, DEFAULT_MAX_DISTANCE);
		}

		@Override
		public void initialize(EntityT actor) {
			this.actor = Objects.requireNonNull(actor);
			currentIndex = 0;
		}

		@Override
		public Vec3 getCurrentWaypoint() {
			if (currentIndex < path.size())
				return path.get(currentIndex);
			else
				return null;
		}

		@Override
		public boolean hasWaypointReached() {
			Vec3 position = WorldHelper.getEntityPositionVector(actor);
			return position.subtract(getCurrentWaypoint()).lengthVector() < maxDistance;
		}

		@Override
		public void onWaypointReached() {
			currentIndex++;
		}

	}

	public static class PathCircleAdapter<EntityT extends EntityLiving> extends PathListAdapter<EntityT> {

		public PathCircleAdapter(List<Vec3> path, double allowedDistance) {
			super(path, allowedDistance);
		}

		public PathCircleAdapter(List<Vec3> path) {
			super(path);
		}

		public PathCircleAdapter(Vec3[] nodes, double allowedDistance) {
			super(nodes, allowedDistance);
		}

		public PathCircleAdapter(Vec3[] nodes) {
			super(nodes);
		}

		@Override
		public void onWaypointReached() {
			super.onWaypointReached();
			if (currentIndex == path.size())
				currentIndex = 0;
		}

	}
}
