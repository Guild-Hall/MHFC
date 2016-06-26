package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;

public abstract class AIGeneralAttack<EntityT extends EntityMHFCBase<? super EntityT>> extends AIAnimatedAction<EntityT>
		implements
		IAttackProvider<EntityT> {

	public AIGeneralAttack() {}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		dmgHelper.setDamageCalculator(getDamageCalculator());
		dmgHelper.reset();
	}

	@Override
	protected void update() {
		AIUtils.damageCollidingEntities(this.getEntity(), dmgHelper.getCalculator());
	}

}
