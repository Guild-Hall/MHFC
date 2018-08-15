package mhfc.net.common.ai.general.provider.simple;

import mhfc.net.common.entity.type.EntityMHFCBase;

public interface IJumpTimingProvider<T extends EntityMHFCBase<? super T>> {
	/**
	 * Returns the frame at which the monster should perform the jump
	 */
	public boolean isJumpFrame(T entity, int frame);

	public boolean isDamageFrame(T entity, int frame);

	public float getTurnRate(T entity, int frame);

}
