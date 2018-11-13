package mhfc.net.common.ai.general.actions;

import java.util.Objects;
import java.util.Random;

import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.provider.adapters.HasNoTargetAdapter;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IPathProvider;
import mhfc.net.common.entity.CreatureAttributes;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.Vec3d;

public abstract class WanderAction<T extends CreatureAttributes<? super T>> extends MovementAction<T> {

	//TODO Make monster rest after walking.
	public static class RandomWanderProvider<T extends EntityLiving> implements IPathProvider {


		public RandomWanderProvider(T actor) {
			this(actor, WanderAction.provideWanderDistance);
		}

		public RandomWanderProvider(T actor, int maxWanderDistance) {
			if (maxWanderDistance < 0) {
				throw new IllegalArgumentException("The wander distance must not be negative");
			}
			this.wanderDistance = maxWanderDistance;
			initialize(actor);
		}

		private Random random = new Random(0);
		private int wanderDistance;

		protected T actor;
		protected Vec3d startingPosition;
		protected Vec3d waypoint;
		protected float acceptedDistance;

		private void initialize(T actor) {
			this.actor = Objects.requireNonNull(actor);
			startingPosition = actor.getPositionVector();
			onWaypointReached();
		}

		@Override
		public Vec3d getCurrentWaypoint() {
			return waypoint;
		}

		@Override
		public boolean hasWaypointReached() {
			Vec3d position = actor.getPositionVector();
			if (waypoint.subtract(position).length() < acceptedDistance) {
				return true;
			}
			acceptedDistance += 0.01f * wanderDistance;
			return false;
		}

		@Override
		public void onWaypointReached() {
			acceptedDistance = 0.5f;
			waypoint = generateNewRandomPoint();
		}

		private Vec3d generateNewRandomPoint() {
			int randomAddX = random.nextInt(wanderDistance) - wanderDistance / 2;
			int randomAddZ = random.nextInt(wanderDistance) - wanderDistance / 2;
			int randomX = (int) (startingPosition.x + randomAddX);
			int randomZ = (int) (startingPosition.z + randomAddZ);
			return new Vec3d(randomX, startingPosition.y, randomZ);
		}
	}

	public WanderAction() {}

	public static int provideWanderDistance;

	@Override
	public IPathProvider providePath() {
		return new RandomWanderProvider<EntityLiving>(getEntity());
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return new HasNoTargetAdapter(getEntity());
	}


	protected abstract float computeWanderWeight();

	@Override
	protected float computeSelectionWeight() {
		if (!SelectionUtils.isIdle(getEntity())) {
			return DONT_SELECT;
		}
		return computeWanderWeight();
	}


}
