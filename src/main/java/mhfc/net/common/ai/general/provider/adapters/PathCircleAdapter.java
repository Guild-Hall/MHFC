package mhfc.net.common.ai.general.provider.adapters;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class PathCircleAdapter extends PathListAdapter {

	public PathCircleAdapter(EntityLiving actor, List<Vec3d> path, double allowedDistance) {
		super(actor, path, allowedDistance);
	}

	public PathCircleAdapter(EntityLiving actor, List<Vec3d> path) {
		super(actor, path);
	}

	public PathCircleAdapter(EntityLiving actor, Vec3d[] nodes, double allowedDistance) {
		super(actor, nodes, allowedDistance);
	}

	public PathCircleAdapter(EntityLiving actor, Vec3d[] nodes) {
		super(actor, nodes);
	}

	@Override
	public void onWaypointReached() {
		super.onWaypointReached();
		if (currentIndex == path.size()) {
			currentIndex = 0;
		}
	}

}
