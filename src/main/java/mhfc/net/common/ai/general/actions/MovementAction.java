package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.provider.*;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.util.Vec3;

public class MovementAction<EntityT extends EntityMHFCBase<? super EntityT>>
	extends
		ActionAdapter<EntityT> {

	public static interface MovementProvider<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
			IAnimationProvider,
			ISelectionPredicate<EntityT>,
			IContinuationPredicate<EntityT>,
			IWeightProvider<EntityT>,
			IMovementProvider<EntityT> {
	}

	MovementProvider<EntityT> movementProvider;

	@Override
	protected void update() {
		EntityT actor = getEntity();
		if (movementProvider.hasWaypointReached(actor, actor.getAITarget())) {
			movementProvider.onWaypointReached(actor, actor.getAITarget());
		} else {
			Vec3 checkPoint = movementProvider.getCurrentWaypoint(actor, actor
				.getAITarget());
			actor.getTurnHelper().updateTurnSpeed(
				movementProvider.getTurnRate());
			actor.getTurnHelper().updateTargetPoint(checkPoint);
			actor.moveForward(movementProvider.getMoveSpeed());
		}
	}

	@Override
	public float getWeight() {
		return movementProvider.getWeight(getEntity(), getEntity()
			.getAITarget());
	}

	@Override
	public boolean shouldContinue() {
		return movementProvider.shouldContinueAttack(this, getEntity());
	}

}
