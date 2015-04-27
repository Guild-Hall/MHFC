package mhfc.net.common.ai;

import net.minecraft.entity.EntityLivingBase;

/**
 * Offers callback methods e.g. when attacks change etc.
 *
 * @author WorldSEnder
 *
 */
public interface IManagedAttacks<T extends EntityLivingBase & IManagedAttacks<T>> {
	/**
	 * When a new attack is being selected.
	 *
	 * @param newAttack
	 */
	public void onAttackStart(IExecutableAttack<? super T> newAttack);

	public void onAttackEnd(IExecutableAttack<? super T> oldAttack);

	public AIAttackManager<T> getAttackManager();
}
