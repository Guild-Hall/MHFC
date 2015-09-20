package mhfc.net.common.ai.general.actions;

import java.util.Objects;
import java.util.Random;

import mhfc.net.common.ai.general.provider.*;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.Vec3;

public class AIGeneralWander<EntityT extends EntityMHFCBase<? super EntityT>>
	extends
		AIGeneralMovement<EntityT> {

	public static class WanderAdapter<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
			MovementActionAdapter<EntityT> {

		private static <EntityT extends EntityMHFCBase<? super EntityT>> ISelectionPredicate<EntityT> SelectionPredicate() {
			return new ISelectionPredicate.SelectIdleAdapter<EntityT>();
		}

		private static <EntityT extends EntityMHFCBase<? super EntityT>> IContinuationPredicate<EntityT> ContinuationPredicate() {
			return new IContinuationPredicate.HasNoTargetAdapter<EntityT>();
		}

		private static <EntityT extends EntityMHFCBase<? super EntityT>> IMovementProvider<EntityT> MovementProvider(
			IMoveParameterProvider provider) {
			IPathProvider<EntityT> pathProvider = new RandomWanderProvider<EntityT>();
			return new IMovementProvider.TurnThenMoveAdapter<>(pathProvider,
				provider, 5f);
		}

		public WanderAdapter(IAnimationProvider animationProvider,
			IWeightProvider<EntityT> weightProvider,
			IMoveParameterProvider movementProvider) {
			super(animationProvider, WanderAdapter
				.<EntityT> SelectionPredicate(), WanderAdapter
				.<EntityT> ContinuationPredicate(), weightProvider,
				WanderAdapter.<EntityT> MovementProvider(movementProvider));
		}
	}

	public AIGeneralWander(IAnimationProvider animationProvider,
		IWeightProvider<EntityT> weightProvider,
		IMoveParameterProvider parameterProvider) {
		super(new WanderAdapter<EntityT>(animationProvider, weightProvider,
			parameterProvider));
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
