package mhfc.net.common.ai.general.provider.adapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import mhfc.net.common.ai.general.provider.simple.IPathProvider;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.Vec3d;

public class PathListAdapter implements IPathProvider {

	public static final double DEFAULT_MAX_DISTANCE = 0.25f;

	private EntityLiving actor;
	protected List<Vec3d> path;
	protected int currentIndex;
	protected double maxDistance;

	public PathListAdapter(EntityLiving actor, List<Vec3d> path, double allowedDistance) {
		Objects.requireNonNull(path);
		this.actor = Objects.requireNonNull(actor);
		this.path = new ArrayList<>(path);
		this.currentIndex = 0;
		this.maxDistance = allowedDistance;
	}

	public PathListAdapter(EntityLiving actor, List<Vec3d> path) {
		this(actor, path, DEFAULT_MAX_DISTANCE);
	}

	public PathListAdapter(EntityLiving actor, Vec3d nodes[], double allowedDistance) {
		this(actor, Arrays.asList(nodes), allowedDistance);
	}

	public PathListAdapter(EntityLiving actor, Vec3d nodes[]) {
		this(actor, nodes, DEFAULT_MAX_DISTANCE);
	}

	@Override
	public Vec3d getCurrentWaypoint() {
		if (currentIndex < path.size()) {
			return path.get(currentIndex);
		}
		return null;
	}

	@Override
	public boolean hasWaypointReached() {
		Vec3d position = actor.getPositionVector();
		return position.subtract(getCurrentWaypoint()).lengthVector() < maxDistance;
	}

	@Override
	public void onWaypointReached() {
		currentIndex++;
	}

}
