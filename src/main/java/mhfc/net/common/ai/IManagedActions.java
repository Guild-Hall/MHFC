package mhfc.net.common.ai;

import net.minecraft.entity.EntityLivingBase;

/**
 * Offers callback methods e.g. when attacks change etc.
 *
 * @author WorldSEnder
 *
 */
public interface IManagedActions<T extends EntityLivingBase & IManagedActions<T>> {
	/**
	 * When a new attack is being selected.
	 *
	 * @param newAttack
	 */
	public void onAttackStart(IExecutableAction<? super T> newAttack);

	public void onAttackEnd(IExecutableAction<? super T> oldAttack);

	public AIActionManager<T> getAttackManager();
}
