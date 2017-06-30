package mhfc.net.common.ai.general.provider.simple;

import mhfc.net.common.entity.type.EntityMHFCBase;

public interface ICooldownAnimation<T extends EntityMHFCBase<? super T>> {
	
	/**
	 * Still getting my code hanging
	 * Sets the cooldown for animations?
	 * */
	
	public boolean hasActioned(T entity, int frame);
	public int cooldownProcs(int cooldown);

}
