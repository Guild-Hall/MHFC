package mhfc.net.common.entity;

import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.MathHelper;

public class VanillaBodyHelper extends EntityBodyHelper {

	private static final float MaximumRotation = 75;
	private static final int MovementHistoryCap = 10;
	private final EntityLiving TheEntity;
	private int RotationTime;
	private float TargetYawHead;
	private double[] historyPositionX = new double[MovementHistoryCap];
	private double[] historyPositionZ = new double[MovementHistoryCap];

	public VanillaBodyHelper(EntityLiving entity) {
		super(entity);
		this.TheEntity = entity;
	}

	@Override
	public void updateRenderAngles() {
		for (int i = historyPositionX.length - 1; i > 0; i--) {
			historyPositionX[i] = historyPositionX[i - 1];
			historyPositionZ[i] = historyPositionZ[i - 1];
		}
		historyPositionX[0] = TheEntity.posX;
		historyPositionZ[0] = TheEntity.posZ;
		// change in x pos and change in z pos. i dont know about that Jacobian method
		double dx = delta(historyPositionX);
		double dz = delta(historyPositionZ);
		double distSq = dx * dx + dz * dz;
		double moveAngle = TheEntity.renderYawOffset;
		if (distSq > 2.5e-7) {
			moveAngle = (float) MathHelper.atan2(dz, dx) * (180 / (float) Math.PI) - 90;
			TheEntity.renderYawOffset += MathHelper.wrapDegrees(moveAngle - TheEntity.renderYawOffset) * 0.6F;
			if (!TheEntity.getNavigator().noPath()) {
				TheEntity.rotationYaw += MathHelper.wrapDegrees(moveAngle - TheEntity.rotationYaw) * 0.4F;
			}
		} else if (TheEntity.getPassengers().isEmpty() || !(TheEntity.getPassengers().get(0) instanceof EntityLiving)) {
			float limit = MaximumRotation;
			if (Math.abs(TheEntity.rotationYawHead - TargetYawHead) > 15) {
				RotationTime = 0;
				TargetYawHead = TheEntity.rotationYawHead;
			} else {
				RotationTime++;
				final int speed = 10;
				if (RotationTime > speed) {
					limit = Math.max(1 - (RotationTime - speed) / (float) speed, 0) * MaximumRotation;
				}
			}
			TheEntity.renderYawOffset = approachFloat(TheEntity.rotationYawHead, TheEntity.renderYawOffset, limit);
		}
	}

	private double delta(double[] arr) {
		return mean(arr, 0) - mean(arr, MovementHistoryCap / 2);
	}

	private double mean(double[] arr, int start) {
		double mean = 0;
		for (int i = 0; i < MovementHistoryCap / 2; i++) {
			mean += arr[i + start];
		}
		return mean / arr.length;
	}

	public static float approachFloat(float target, float current, float limit) {
		float delta = MathHelper.wrapDegrees(current - target);
		if (delta < -limit) {
			delta = -limit;
		} else if (delta >= limit) {
			delta = limit;
		}
		return target + delta * 0.55F;
	}
}