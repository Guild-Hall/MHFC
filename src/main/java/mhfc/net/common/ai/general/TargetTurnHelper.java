package mhfc.net.common.ai.general;

import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class TargetTurnHelper {

	private EntityLivingBase entity;
	private Vec3 targetPoint;
	private float maxTurnSpeed;
	private boolean isUpdating;

	public TargetTurnHelper(EntityLivingBase controlledEntity) {
		this.entity = controlledEntity;
	}

	/**
	 * Sets the target point for turns to the position given by the vector
	 */
	public void updateTargetPoint(Vec3 vector) {
		if (vector == null)
			return;
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
		if (entity == null)
			return;
		isUpdating = true;
		this.targetPoint = Vec3.createVectorHelper(entity.posX, entity.posY,
			entity.posZ);
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
	 * Performs the update and resets the update status to false. This means you
	 * have to set something (target point, speed) or call
	 * {@link TargetTurnHelper#forceUpdate()} each entity update. This then gets
	 * called last from EntityMHFCBase in {@link EntityMHFCBase#updateAITick()},
	 * so before the Minecraft default helpers are called.
	 */
	public void onUpdateTurn() {
		if (!isUpdating) {
			return;
		} else {
			isUpdating = false;
			if (targetPoint == null || entity == null) {
				return;
			}
			Vec3 vecToTarget = Vec3.createVectorHelper(entity.posX,
				entity.posY, entity.posZ).subtract(targetPoint);
			entity.rotationYaw = AIUtils.modifyYaw(entity.getLookVec(),
				vecToTarget, maxTurnSpeed);
		}
	}

}
