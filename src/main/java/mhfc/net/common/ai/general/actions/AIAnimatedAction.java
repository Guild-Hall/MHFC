package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimatedActionProvider;
import net.minecraft.entity.EntityCreature;

public abstract class AIAnimatedAction<EntityT extends EntityCreature> extends ActionAdapter<EntityT>
		implements
		IAnimatedActionProvider<EntityT> {

	@Override
	protected void beginExecution() {
		super.beginExecution();
		setAnimation(getAnimationLocation());
		setLastFrame(getAnimationLength());
	}

	@Override
	public float getWeight() {
		EntityT entity = this.getEntity();
		target = entity.getAttackTarget();
		if (shouldSelectAttack(this, entity, target)) {
			return getWeight(entity, target);
		} else {
			return DONT_SELECT;
		}
	}

}
