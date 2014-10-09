package mhfc.net.common.ai;

import net.minecraft.entity.EntityLivingBase;

/**
 * Offers callback methods e.g. when attacks change etc.
 *
 * @author WorldSEnder
 *
 */
public interface IMangedAttacks<T extends EntityLivingBase & IMangedAttacks<T>> {
	/**
	 * When a new attack is being selected.
	 *
	 * @param newAttack
	 */
	public void onAttackStart(IExecutableAttack<T> newAttack);

	public void onAttackEnd(IExecutableAttack<T> oldAttack);

	public AIAttackManager<T> getAttackManager();
}
