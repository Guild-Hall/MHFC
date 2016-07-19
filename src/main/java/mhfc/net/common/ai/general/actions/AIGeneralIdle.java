package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;

public abstract class AIGeneralIdle<EntityT extends EntityMHFCBase<? super EntityT>> extends AIAnimatedAction<EntityT> {

	private ISelectionPredicate.SelectIdleAdapter<EntityT> selectIdleAdapter;

	public AIGeneralIdle() {
		selectIdleAdapter = new ISelectionPredicate.SelectIdleAdapter<EntityT>();
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		if(target != null){
			getEntity().getTurnHelper().updateTargetPoint(target);
		}
		getEntity().getTurnHelper().updateTurnSpeed(2.0F);
		getEntity().playLivingSound();
	}

	@Override
	public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
		return selectIdleAdapter.shouldSelectAttack(attack, actor, target);
	}

	@Override
	protected void update() {} // do nothing, we idle, remember?

}
