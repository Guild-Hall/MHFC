package mhfc.net.common.ai;

import net.minecraft.entity.EntityLivingBase;

/**
 * Offers an interface to entities that have multiple stances with different
 * attack managers for each one.
 */

public interface IStancedManagedAttacks<EntityT extends EntityLivingBase & IStancedManagedAttacks<EntityT, StanceT> & IManagedAttacks<EntityT>, //
StanceT extends Enum<StanceT> & IStancedAttackManager.Stance<EntityT, StanceT>>
	extends
		IManagedAttacks<EntityT> {

	@Override
	public AIStancedAttackManager<EntityT, StanceT> getAttackManager();
}
