package mhfc.net.common.ai.general.provider;

import java.security.InvalidParameterException;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

public interface IJumpParamterProvider<EntityT extends EntityLivingBase> {

	/**
	 * Gets the speed upwards which the entity should have when jumping.
	 */
	public float getInitialUpVelocity(EntityT entity);

	/**
	 * Gets the forward speed that the entity should have when jumping.
	 */
	public float getForwardVelocity(EntityT entity);

	/**
	 * A class that implements the jump parameter aiming to provide a constant
	 * jump time when jumping towards the enemy with enough speed to land
	 * directly at his position.
	 * 
	 * @param <EntityT>
	 */
	public static class AttackTargetAdapter<EntityT extends EntityLiving>
		implements
			IJumpParamterProvider<EntityT> {

		public static final float GRAVITATIONAL_C_LIVING = 0.08f; // blocks per
																	// tick^2
		private float jumpTime;

		public AttackTargetAdapter(float jumpTimeInTicks) {
			this.jumpTime = jumpTimeInTicks;
			if (jumpTime <= 0)
				throw new InvalidParameterException(
					"Jump time must be bigger than zero");
		}

		@Override
		public float getInitialUpVelocity(EntityT entity) {
			EntityLivingBase target = entity.getAttackTarget();
			float velocity = (float) (target.posY - entity.posY) / jumpTime
				+ GRAVITATIONAL_C_LIVING * jumpTime / 2;
			return velocity;
		}

		@Override
		public float getForwardVelocity(EntityT entity) {
			float distance = entity.getDistanceToEntity(entity
				.getAttackTarget());
			// CLEANUP why does a multiplication with 3 work so well here??
			// It should be v = s/t just straight up, not v = s/t*3.....
			float velocity = distance / jumpTime * 3;
			return velocity;
		}

	}

}
