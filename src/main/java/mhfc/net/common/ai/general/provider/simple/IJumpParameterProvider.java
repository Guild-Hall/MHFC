package mhfc.net.common.ai.general.provider.simple;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public interface IJumpParameterProvider<T extends EntityLivingBase> {

	public default Vec3d getJumpVector(T entity) {
		Vec3d lookVector = entity.getLookVec();
		return new Vec3d(lookVector.xCoord, 0, lookVector.zCoord);
	}

	/**
	 * Gets the speed upwards which the entity should have when jumping.
	 */
	public float getInitialUpVelocity(T entity);

	/**
	 * Gets the forward speed that the entity should have when jumping.
	 */
	public float getForwardVelocity(T entity);

}
