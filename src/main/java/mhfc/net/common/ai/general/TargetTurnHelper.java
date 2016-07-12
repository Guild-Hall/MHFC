package mhfc.net.common.ai.general;

import java.util.Objects;

import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class TargetTurnHelper {

	private EntityMHFCBase<?> entity;
	private Vec3 targetPoint;
	private float maxTurnSpeed;
	private boolean isUpdating;

	public TargetTurnHelper(EntityMHFCBase<?> controlledEntity) {
		this.entity = Objects.requireNonNull(controlledEntity);
	}

	/**
	 * Sets the target point for turns to the position given by the vector
	 */
	public void updateTargetPoint(Vec3 vector) {
		if (vector == null) {
			return;
		}
		isUpdating = true;
		this.targetPoint = vector.addVector(0, 0, 0);
	}

	/**
	 * Sets the target point for turns to the given position
	 */
	public void updateTargetPoint(double x, double y, double z) {
		isUpdating = true;
		this.targetPoint = Vec3.createVectorHelper(x, y, z);
	}

	/**
	 * Sets the entity as a target for turning
	 */
	public void updateTargetPoint(Entity entity) {
		if (entity == null) {
			return;
		}
		isUpdating = true;
		this.targetPoint = Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ);
	}

	/**
	 * Controls the maximum turn rate. Negative values will get defaulted to 0
	 */
	public void updateTurnSpeed(float maxTurnSpeed) {
		isUpdating = true;
		this.maxTurnSpeed = maxTurnSpeed > 0 ? maxTurnSpeed : 0;
	}

	public void forceUpdate() {
		isUpdating = true;
	}

	public boolean isUpdating() {
		return isUpdating;
	}

	/**
	 * <b> Do not call this yourself. </b><br>
	 * Performs the update and resets the update status to false. This means you have to set something (target point,
	 * speed) or call {@link TargetTurnHelper#forceUpdate()} each entity update. This then gets called last from
	 * EntityMHFCBase in {@link EntityMHFCBase#updateAITick()}, so before the Minecraft default helpers are called.
	 */
	public void onUpdateTurn() {
		if (!isUpdating) {
			return;
		}
		isUpdating = false;
		if (targetPoint == null) {
			return;
		}
		Vec3 entityPos = WorldHelper.getEntityPositionVector(entity);
		Vec3 vecToTarget = entityPos.subtract(targetPoint);
		float newYaw = AIUtils.modifyYaw(entity.getLookVec(), vecToTarget.normalize(), maxTurnSpeed);
		if (!Float.isNaN(newYaw)) {
			entity.rotationYaw = newYaw;
		}
		// CLEANUP Figure out a way to send the updates to the client cleanly
		entity.addVelocity(10e-4, 0, 10e-4);
	}

}
