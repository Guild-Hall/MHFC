package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.general.provider.composite.MovementActionProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.util.Vec3;

public abstract class AIGeneralMovement<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
		AIAnimatedAction<EntityT> implements MovementActionProvider<EntityT> {

	@Override
	protected void beginExecution() {
		super.beginExecution();
		initialize(getEntity());
		getEntity().playLivingSound();
	}

	@Override
	protected void update() {
		EntityT actor = getEntity();
		
		if (hasWaypointReached()) {
			onWaypointReached();
		} else {
			Vec3 checkPoint = getCurrentWaypoint();
			actor.getTurnHelper().updateTurnSpeed(getTurnRate());
			actor.getTurnHelper().updateTargetPoint(checkPoint);
			actor.moveForward(getMoveSpeed(), true);
		}
	}
	

	@Override
	public boolean shouldContinue() {
		return shouldContinueAction(this, getEntity());
	}

}
