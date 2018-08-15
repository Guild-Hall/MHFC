package mhfc.net.common.ai.general.provider.adapters;

import java.security.InvalidParameterException;
import java.util.Objects;

import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.Vec3d;

/**
 * Tries to jump as exactly as possible onto the target coordinate
 *
 * @author Katora
 *
 * @param <T>
 */
public class ConstantAirTimeAdapter<T extends EntityLiving> implements IJumpParameterProvider<T> {

	public static interface ITargetResolver<T extends EntityLiving> {
		Vec3d getJumpTarget(T entity);
	}

	public static final float GRAVITATIONAL_C_LIVING = 0.06f; // blocks per
																// tick^2
	protected float airTime;
	private float maxSpeed, minSpeed;
	private ITargetResolver<T> targetResolver;

	public ConstantAirTimeAdapter(float jumpAirTimeInTicks, ITargetResolver<T> targetResolver) {
		if (jumpAirTimeInTicks <= 0) {
			throw new InvalidParameterException("Jump time must be bigger than zero");
		}
		this.airTime = jumpAirTimeInTicks;
		this.targetResolver = Objects.requireNonNull(targetResolver);
		this.maxSpeed = 100;
		this.minSpeed = 0;
	}

	@Override
	public float getInitialUpVelocity(T entity) {
		Vec3d target = Objects.requireNonNull(targetResolver.getJumpTarget(entity));
		float velocity = (float) (target.yCoord - entity.posY) / airTime + GRAVITATIONAL_C_LIVING * airTime / 2;
		return velocity;
	}

	@Override
	public float getForwardVelocity(T entity) {
		Vec3d target = Objects.requireNonNull(targetResolver.getJumpTarget(entity));
		Vec3d position = entity.getPositionVector();
		float distance = (float) position.distanceTo(target);
		// TODO why does a multiplication with 3 work so well here??
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
