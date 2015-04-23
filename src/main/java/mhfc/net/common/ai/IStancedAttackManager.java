package mhfc.net.common.ai;

import net.minecraft.entity.EntityLivingBase;

/*
 * What is this wtf-ery with types? Basically it makes sure the entity really is
 * of a type that has a attack manager with multiple stances, and the stance
 * type is an enum that also conforms to an interface requiring additional
 * methods (see nested interface Stance).
 * 
 * The nested interface requires the same of the entity type and also takes the
 * type of the enum again, just to make sure it can be used as the type inside
 * the methods.
 * 
 * @param <EntityT>
 */

public interface IStancedAttackManager<EntityT extends EntityLivingBase & IStancedManagedAttacks<EntityT, StanceT> & IManagedAttacks<EntityT>, //
StanceT extends Enum<StanceT> & IStancedAttackManager.Stance<EntityT, StanceT>>
	extends
		IAttackManager<EntityT> {

	public static interface Stance<EntityT extends EntityLivingBase & IStancedManagedAttacks<EntityT, StanceT> & IManagedAttacks<EntityT>, //
	StanceT extends Enum<StanceT> & IStancedAttackManager.Stance<EntityT, StanceT>> {
		public IExecutableAttack<EntityT> chooseAttack(
			IStancedAttackManager<EntityT, StanceT> manager);

		public void registerAttack(IExecutableAttack<EntityT> attack);
	}

	public void switchMode(StanceT newMode);

	public StanceT getCurrentMode();

}
