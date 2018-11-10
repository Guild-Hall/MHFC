package mhfc.net.common.ai.general.provider.simple;

import mhfc.net.common.entity.CreatureAttributes;

public interface IJumpTimingProvider<T extends CreatureAttributes<? super T>> {
	/**
	 * Returns the frame at which the monster should perform the jump
	 */
	public boolean isJumpFrame(T entity, int frame);

	public boolean isDamageFrame(T entity, int frame);

	public float getTurnRate(T entity, int frame);

}
