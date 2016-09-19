package mhfc.net.common.ai.general.provider.simple;

import java.security.InvalidParameterException;
import java.util.Objects;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public interface IJumpParamterProvider<EntityT extends EntityLivingBase> {

	public default Vec3d getJumpVector(EntityT entity) {
		Vec3d lookVector = entity.getLookVec();
		return new Vec3d(lookVector.xCoord, 0, lookVector.zCoord);
	}

	/**
	 * Gets the speed upwards which the entity should have when jumping.
	 */
	public float getInitialUpVelocity(EntityT entity);

	/**
	 * Gets the forward speed that the entity should have when jumping.
	 */
	public float getForwardVelocity(EntityT entity);

	/**
	 * Tries to jump as exactly as possible onto the target coordinate
	 *
	 * @author Katora
	 *
	 * @param <EntityT>
	 */
	public static class ConstantAirTimeAdapter<EntityT extends EntityLiving> implements IJumpParamterProvider<EntityT> {

		public static interface ITargetResolver<EntityT extends EntityLiving> {
			Vec3d getJumpTarget(EntityT entity);
		}

		public static final float GRAVITATIONAL_C_LIVING = 0.08f; // blocks per
																	// tick^2
		protected float airTime;
		private float maxSpeed, minSpeed;
		private ITargetResolver<EntityT> targetResolver;

		public ConstantAirTimeAdapter(float jumpAirTimeInTicks, ITargetResolver<EntityT> targetResolver) {
			if (jumpAirTimeInTicks <= 0) {
				throw new InvalidParameterException("Jump time must be bigger than zero");
			}
			this.airTime = jumpAirTimeInTicks;
			this.targetResolver = Objects.requireNonNull(targetResolver);
			this.maxSpeed = 100;
			this.minSpeed = 0;
		}

		@Override
		public float getInitialUpVelocity(EntityT entity) {
			Vec3d target = Objects.requireNonNull(targetResolver.getJumpTarget(entity));
			float velocity = (float) (target.yCoord - entity.posY) / airTime + GRAVITATIONAL_C_LIVING * airTime / 2;
			return velocity;
		}

		@Override
		public float getForwardVelocity(EntityT entity) {
			Vec3d target = Objects.requireNonNull(targetResolver.getJumpTarget(entity));
			Vec3d position = entity.getPositionVector();
			float distance = (float) position.distanceTo(target);
			// CLEANUP why does a multiplication with 3 work so well here??
			// It should be v = s/t just straight up, not v = s/t*3.....
			float velocity = distance / airTime * 2.8f *
			// Correct minecraft slowdown
					(airTime * 0.02f) / (1 - (float) Math.pow(0.98, airTime));
			return Math.min(Math.max(velocity, minSpeed), maxSpeed);
		}

		public void setSpeedInterval(float newMinSpeed, float newMaxSpeed) {
			if (newMaxSpeed < newMinSpeed) {
				throw new InvalidParameterException("Min speed can not be bigger than max speed");
			}
			this.minSpeed = newMinSpeed;
			this.maxSpeed = newMaxSpeed;
		}

	}

	public static class AttackPointAdapter<EntityT extends EntityLiving> extends ConstantAirTimeAdapter<EntityT> {

		private static class ConstPointResolver<EntityT extends EntityLiving> implements ITargetResolver<EntityT> {

			private Vec3d targetPoint;

			public ConstPointResolver(Vec3d target) {
				this.targetPoint = target;
			}

			@Override
			public Vec3d getJumpTarget(EntityT entity) {
				return this.targetPoint;
			}

		}

		public AttackPointAdapter(float jumpTimeInTicks, Vec3d targetPoint) {
			super(jumpTimeInTicks, new ConstPointResolver<EntityT>(targetPoint));
		}

	}

	/**
	 * A class that implements the jump parameter aiming to provide a constant jump time when jumping towards the enemy
	 * with enough speed to land directly at his position.
	 */
	public static class AttackTargetAdapter<EntityT extends EntityLiving> extends ConstantAirTimeAdapter<EntityT> {

		public AttackTargetAdapter(float jumpTimeInTicks) {
			super(jumpTimeInTicks, entity -> {
				EntityLivingBase attackTarget = entity.getAttackTarget();
				Vec3d target;
				if (attackTarget != null) {
					target = attackTarget.getPositionVector();
				} else {
					target = entity.getPositionVector();
				}
				return target;
			});
		}
	}

}
