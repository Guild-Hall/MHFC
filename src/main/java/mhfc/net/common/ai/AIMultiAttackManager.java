package mhfc.net.common.ai;

import net.minecraft.entity.EntityLivingBase;

public class AIMultiAttackManager<E extends EntityLivingBase & IManagedAttacks<E>, Mode extends Enum<Mode>>
	extends
		AIAttackManager<E>
	implements
		IMultiAttackManager<E, AIAttackManager<E>, AIMultiAttackManager<E, Mode>, Mode> {

	public AIMultiAttackManager(E entity) {
		super(entity);
	}

	@Override
	public AIMultiAttackManager<E, Mode> getEntityAI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void switchMode(Mode newMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public Mode getCurrentMode() {
		// TODO Auto-generated method stub
		return null;
	}

}
