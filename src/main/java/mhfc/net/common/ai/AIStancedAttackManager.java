package mhfc.net.common.ai;

import net.minecraft.entity.EntityLivingBase;

public class AIStancedAttackManager<EntityT extends EntityLivingBase & IStancedManagedAttacks<EntityT, StanceT>, //
StanceT extends Enum<StanceT> & IStancedAttackManager.Stance<EntityT, StanceT>>
	extends
		AIAttackManager<EntityT>
	implements
		IStancedAttackManager<EntityT, StanceT> {

	StanceT currentMode;

	public AIStancedAttackManager(EntityT entity) {
		super(entity);
	}

	@Override
	public void switchMode(StanceT newMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public StanceT getCurrentMode() {
		return currentMode;
	}

	@Override
	public IExecutableAttack<EntityT> chooseAttack() {
		return currentMode.chooseAttack(this);
	}

}
