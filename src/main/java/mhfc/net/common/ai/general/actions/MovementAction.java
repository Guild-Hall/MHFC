package mhfc.net.common.ai.general.actions;

import java.util.Objects;

import mhfc.net.common.ai.general.provider.requirements.INeedsMovementParameters;
import mhfc.net.common.ai.general.provider.requirements.INeedsPath;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IPathProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.util.math.Vec3d;

public abstract class MovementAction<T extends EntityMHFCBase<? super T>> extends AnimatedAction<T>
		implements
		INeedsMovementParameters,
		INeedsPath {

	private IMoveParameterProvider movementParameters;
	private IPathProvider pathProvider;

	@Override
	protected void beginExecution() {
		super.beginExecution();
		movementParameters = Objects.requireNonNull(provideMoveParameters());
		pathProvider = Objects.requireNonNull(providePath());
		getEntity().playLivingSound();
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		T actor = getEntity();

		if (pathProvider.hasWaypointReached()) {
			pathProvider.onWaypointReached();
		} else {
			Vec3d checkPoint = pathProvider.getCurrentWaypoint();
			actor.getTurnHelper().updateTurnSpeed(movementParameters.getTurnRate());
			actor.getTurnHelper().updateTargetPoint(checkPoint);
			actor.moveForward(movementParameters.getMoveSpeed(), true);
		}
	}
	
	

}
