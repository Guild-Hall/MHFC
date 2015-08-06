package mhfc.net.common.ai.general.actions;

import java.util.Objects;
import java.util.Random;

import mhfc.net.common.ai.general.provider.*;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.Vec3;

public class WanderAction<EntityT extends EntityMHFCBase<? super EntityT>>
	extends
		MovementAction<EntityT> {

	public WanderAction(IAnimationProvider animationProvider,
		IWeightProvider<EntityT> weightProvider,
		IMoveParameterProvider parameterProvider) {
		super(generateProvider(animationProvider, weightProvider,
			parameterProvider));
	}

	private static <EntityT extends EntityMHFCBase<? super EntityT>> MovementActionProvider<EntityT> generateProvider(
		IAnimationProvider animationProvider,
		IWeightProvider<EntityT> weightProvider,
		IMoveParameterProvider parameterProvider) {
		ISelectionPredicate<EntityT> selectionPredicate = new ISelectionPredicate.SelectIdleAdapter<>();
		IContinuationPredicate<EntityT> continuationPredicate = new IContinuationPredicate.HasNoTargetAdapter<>();
		IPathProvider<EntityT> pathProvider = new RandomWanderProvider<EntityT>();
		IMovementProvider<EntityT> movementProvider = new IMovementProvider.TurnThenMoveAdapter<EntityT>(
			pathProvider, parameterProvider, 5f);
		return new MovementActionAdapter<EntityT>(animationProvider,
			selectionPredicate, continuationPredicate, weightProvider,
			movementProvider);
	}

	public static class RandomWanderProvider<EntityT extends EntityLiving>
		implements
			IPathProvider<EntityT> {

		public static final int DEFAULT_WANDER_DISTANCE = 20;

		public RandomWanderProvider() {
			this(DEFAULT_WANDER_DISTANCE);
		}

		public RandomWanderProvider(int maxWanderDistance) {
			if (maxWanderDistance < 0)
				throw new IllegalArgumentException(
					"The wander distance must not be negative");
			this.wanderDistance = maxWanderDistance;
		}

		private Random random = new Random();
		private int wanderDistance;

		protected EntityT actor;
		protected Vec3 startingPosition;
		protected Vec3 waypoint;
		protected float acceptedDistance;

		@Override
		public void initialize(EntityT actor) {
			this.actor = Objects.requireNonNull(actor);
			startingPosition = actor.getPosition(1f);
			onWaypointReached();
		}

		@Override
		public Vec3 getCurrentWaypoint() {
			return waypoint;
		}

		@Override
		public boolean hasWaypointReached() {
			Vec3 position = actor.getPosition(1f);
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

		private Vec3 generateNewRandomPoint() {
			int randomX = (int) (startingPosition.xCoord + random
				.nextInt(wanderDistance));
			int randomZ = (int) (startingPosition.zCoord + random
				.nextInt(wanderDistance));
			return Vec3.createVectorHelper(randomX, startingPosition.yCoord,
				randomZ);
		}
	}

}
