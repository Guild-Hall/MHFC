package mhfc.net.common.ai.general.provider;

import java.security.InvalidParameterException;
import java.util.Objects;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public interface IJumpParamterProvider<EntityT extends EntityLivingBase> {

	/**
	 * Gets the speed upwards which the entity should have when jumping.
	 */
	public float getInitialUpVelocity(EntityT entity);

	/**
	 * Gets the forward speed that the entity should have when jumping.
	 */
	public float getForwardVelocity(EntityT entity);

	public static class ConstantAirTimeAdapter<EntityT extends EntityLiving>
		implements
			IJumpParamterProvider<EntityT> {

		public static interface ITargetResolver<EntityT extends EntityLiving> {
			Vec3 getJumpTarget(EntityT entity);
		}

		public static final float GRAVITATIONAL_C_LIVING = 0.08f; // blocks per
																	// tick^2
		protected float airTime;
		private ITargetResolver<EntityT> targetResolver;

		public ConstantAirTimeAdapter(float jumpAirTimeInTicks,
			ITargetResolver<EntityT> targetResolver) {
			this.airTime = jumpAirTimeInTicks;
			this.targetResolver = Objects.requireNonNull(targetResolver);
			if (airTime <= 0)
				throw new InvalidParameterException(
					"Jump time must be bigger than zero");
		}

		@Override
		public float getInitialUpVelocity(EntityT entity) {
			Vec3 target = Objects.requireNonNull(targetResolver.getJumpTarget(
				entity));
			float velocity = (float) (target.yCoord - entity.posY) / airTime
				+ GRAVITATIONAL_C_LIVING * airTime / 2;
			return velocity;
		}

		@Override
		public float getForwardVelocity(EntityT entity) {
			Vec3 target = Objects.requireNonNull(targetResolver.getJumpTarget(
				entity));
			float distance = (float) entity.getPosition(1.0f).distanceTo(
				target);
			// CLEANUP why does a multiplication with 3 work so well here??
			// It should be v = s/t just straight up, not v = s/t*3.....
			float velocity = distance / airTime * 3 *
				// Correct minecraft slowdown
				(airTime * 0.02f) / (1 - (float) Math.pow(0.98, airTime));
			return Math.max(velocity, 0);
		}

	}

	public static class AttackPointAdapter<EntityT extends EntityLiving>
		extends
			ConstantAirTimeAdapter<EntityT> {

		private static class ConstPointResolver<EntityT extends EntityLiving>
			implements
				ITargetResolver<EntityT> {

			private Vec3 targetPoint;

			public ConstPointResolver(Vec3 target) {
				this.targetPoint = target;
			}

			@Override
			public Vec3 getJumpTarget(EntityT entity) {
				return this.targetPoint;
			}

		}

		public AttackPointAdapter(float jumpTimeInTicks, Vec3 targetPoint) {
			super(jumpTimeInTicks, new ConstPointResolver<EntityT>(
				targetPoint));
		}

	}

	/**
	 * A class that implements the jump parameter aiming to provide a constant
	 * jump time when jumping towards the enemy with enough speed to land
	 * directly at his position.
	 */
	public static class AttackTargetAdapter<EntityT extends EntityLiving>
		extends
			ConstantAirTimeAdapter<EntityT> {

		public AttackTargetAdapter(float jumpTimeInTicks) {
			super(jumpTimeInTicks, new ITargetResolver<EntityT>() {
				@Override
				public Vec3 getJumpTarget(EntityLiving entity) {
					EntityLivingBase attackTarget = entity.getAttackTarget();
					Vec3 target;
					if (attackTarget != null) {
						target = attackTarget.getPosition(1.0f);
					} else {
						target = entity.getPosition(1.0f);
					}
					return target;
				}
			});
		}
	}

}
