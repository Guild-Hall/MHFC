package mhfc.net.common.ai.general.actions;

import java.util.Objects;
import java.util.Random;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IMovementProvider;
import mhfc.net.common.ai.general.provider.simple.IPathProvider;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.Vec3;

public abstract class AIGeneralWander<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
		AIGeneralMovement<EntityT> {

	public static class RandomWanderProvider<EntityT extends EntityLiving> implements IPathProvider<EntityT> {

		public static int DEFAULT_WANDER_DISTANCE = 20;

		public RandomWanderProvider() {
			this(DEFAULT_WANDER_DISTANCE);
		}

		public RandomWanderProvider(int maxWanderDistance) {
			if (maxWanderDistance < 0)
				throw new IllegalArgumentException("The wander distance must not be negative");
			this.wanderDistance = maxWanderDistance;
		}
		

		private Random random = new Random(0);
		private int wanderDistance;

		protected EntityT actor;
		protected Vec3 startingPosition;
		protected Vec3 waypoint;
		protected float acceptedDistance;

		@Override
		public void initialize(EntityT actor) {
			this.actor = Objects.requireNonNull(actor);
			startingPosition = WorldHelper.getEntityPositionVector(actor);
			onWaypointReached();
		}

		@Override
		public Vec3 getCurrentWaypoint() {
			return waypoint;
		}

		@Override
		public boolean hasWaypointReached() {
			Vec3 position = WorldHelper.getEntityPositionVector(actor);
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
			int randomAddX = random.nextInt(wanderDistance) - wanderDistance / 2;
			int randomAddZ = random.nextInt(wanderDistance) - wanderDistance / 2;
			int randomX = (int) (startingPosition.xCoord + randomAddX);
			int randomZ = (int) (startingPosition.zCoord + randomAddZ);
			return Vec3.createVectorHelper(randomX, startingPosition.yCoord, randomZ);
		}
	}

	private ISelectionPredicate.SelectIdleAdapter<EntityT> selectIdleAdapter;
	private IContinuationPredicate.HasNoTargetAdapter<EntityT> hasNoTargetAdapter;
	private RandomWanderProvider<EntityT> pathProvider;
	private IMovementProvider.TurnThenMoveAdapter<EntityT> turnThenMoveAdapter;

	public AIGeneralWander(IMoveParameterProvider parameterProvider) {
		selectIdleAdapter = new ISelectionPredicate.SelectIdleAdapter<EntityT>();
		hasNoTargetAdapter = new IContinuationPredicate.HasNoTargetAdapter<EntityT>();
		pathProvider = new RandomWanderProvider<EntityT>();
		turnThenMoveAdapter = new IMovementProvider.TurnThenMoveAdapter<>(pathProvider, parameterProvider, 6f);
	}

	@Override
	public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
		return selectIdleAdapter.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public boolean shouldContinueAction(IExecutableAction<? super EntityT> attack, EntityT actor) {
		return hasNoTargetAdapter.shouldContinueAction(attack, actor);
	}

	@Override
	public void initialize(EntityT actor) {
		turnThenMoveAdapter.initialize(actor);
	}

	@Override
	public Vec3 getCurrentWaypoint() {
		return turnThenMoveAdapter.getCurrentWaypoint();
	}

	@Override
	public boolean hasWaypointReached() {
		return turnThenMoveAdapter.hasWaypointReached();
	}

	@Override
	public void onWaypointReached() {
		turnThenMoveAdapter.onWaypointReached();
	}

	@Override
	public float getTurnRate() {
		return turnThenMoveAdapter.getTurnRate();
	}

	@Override
	public float getMoveSpeed() {
		return turnThenMoveAdapter.getMoveSpeed();
	}

}
