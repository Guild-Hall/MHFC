package mhfc.net.common.ai.general;

import mhfc.net.common.ai.IActionRecorder;
import mhfc.net.common.ai.IExecutableAction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class SelectionUtils {
	private SelectionUtils() {}

	public static boolean isInViewAngle(float minAngle, float maxAngle, EntityLiving actor, Entity target) {
		if (target == null) {
			return false;
		}
		float angle = AIUtils.getViewingAngle(actor, target);
		if (minAngle <= maxAngle) {
			return angle >= minAngle && angle <= maxAngle;
		}
		return angle <= maxAngle || angle >= minAngle;
	}

	public static boolean isInDistance(double minDistance, double maxDistance, EntityLiving actor, Entity target) {
		if (target == null) {
			return false;
		}
		double distance = actor.getDistanceToEntity(target);
		return distance >= minDistance && distance <= maxDistance;
	}

	public static boolean isAfterAttack(IActionRecorder<?> actor, IExecutableAction<?> actionToTrack) {
		return actor.getLastAction() == actionToTrack;
	}

	public static boolean isIdle(EntityLiving actor) {
		return !actor.isDead && actor.getAttackTarget() == null;
	}

	public static boolean selectAlways() {
		return true;
	}
}
