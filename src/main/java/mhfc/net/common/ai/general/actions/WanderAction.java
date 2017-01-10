package mhfc.net.common.ai.general.actions;

import java.util.Objects;
import java.util.Random;

import mhfc.net.common.ai.general.provider.adapters.HasNoTargetAdapter;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IPathProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.Vec3d;

public abstract class WanderAction<T extends EntityMHFCBase<? super T>> extends MovementAction<T> {

	public static class RandomWanderProvider<T extends EntityLiving> implements IPathProvider {

		public static int DEFAULT_WANDER_DISTANCE = 20;

		public RandomWanderProvider(T actor) {
			this(actor, DEFAULT_WANDER_DISTANCE);
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
			if (waypoint.subtract(position).lengthVector() < acceptedDistance) {
				return true;
			} else {
				acceptedDistance += 0.01f * wanderDistance;
				return false;
			}
		}

		@Override
		public void onWaypointReached() {
			acceptedDistance = 0.5f;
			waypoint = generateNewRandomPoint();
		}

		private Vec3d generateNewRandomPoint() {
			int randomAddX = random.nextInt(wanderDistance) - wanderDistance / 2;
			int randomAddZ = random.nextInt(wanderDistance) - wanderDistance / 2;
			int randomX = (int) (startingPosition.xCoord + randomAddX);
			int randomZ = (int) (startingPosition.zCoord + randomAddZ);
			return new Vec3d(randomX, startingPosition.yCoord, randomZ);
		}
	}

	public WanderAction() {}

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
		if(getEntity().getAttackTarget() != null){
			return DONT_SELECT;
		}
		return computeWanderWeight();
	}

}
