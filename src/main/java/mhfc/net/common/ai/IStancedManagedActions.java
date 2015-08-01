package mhfc.net.common.ai;

import net.minecraft.entity.EntityLivingBase;

/**
 * Offers an interface to entities that have multiple stances with different
 * attack managers for each one.
 */

public interface IStancedManagedActions<EntityT extends EntityLivingBase & IStancedManagedActions<EntityT, StanceT> & IManagedActions<EntityT>, //
StanceT extends Enum<StanceT> & IStancedActionManager.Stance<EntityT, StanceT>>
	extends
		IManagedActions<EntityT> {

	@Override
	public AIStancedActionManager<EntityT, StanceT> getAttackManager();
}
