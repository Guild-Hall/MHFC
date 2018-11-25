package mhfc.net.common.ai.entity.monsters.akuravashimu;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.creature.incomplete.AkuraVashimu;

public class Move1 extends AnimatedAction<AkuraVashimu> implements IHasAnimationProvider{

	public Move1() {};
	
	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/akuravashimu/move1.mcanm", 160);
	}

	@Override
	protected float computeSelectionWeight() {
		if(SelectionUtils.isIdle(getEntity())) {
			return DONT_SELECT;
		}
		return 5;
	}
	
	@Override
	public final boolean shouldContinue() {
		return this.getEntity().getDistance(getEntity().getAttackTarget()) > 5;
	}
	
	@Override
	protected void onUpdate() {
		AkuraVashimu entity = getEntity();
		if(this.getCurrentFrame() % 30 == 0) {
			AIUtils.damageCollidingEntities(getEntity().getAttackTarget(), 50);
		}
		if(entity.getAttackTarget() != null) {
			entity.getNavigator().tryMoveToEntityLiving(entity.getAttackTarget(), 0.4D);
			if(entity.getDistance(target) == 0) {
				super.finishExecution();
			}
		}
	}
	
	
	

}
