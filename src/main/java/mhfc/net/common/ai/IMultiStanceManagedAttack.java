package mhfc.net.common.ai;

import net.minecraft.entity.EntityLivingBase;

/**
 * Offers an interface to entities that have multiple stances with different
 * attack managers for each one.
 * 
 * @param <T>
 */
public interface IMultiStanceManagedAttack<T extends EntityLivingBase & IMultiStanceManagedAttack<T, E> & IManagedAttacks<T>, E extends Enum<E>>
	extends
		IManagedAttacks<T> {

	@Override
	public AIMultiAttackManager<T, E> getAttackManager();
}
