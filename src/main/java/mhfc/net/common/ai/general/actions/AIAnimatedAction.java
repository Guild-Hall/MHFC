package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.provider.composite.IAnimatedActionProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;

public abstract class AIAnimatedAction<EntityT extends EntityCreature> extends ActionAdapter<EntityT>
		implements
		IAnimatedActionProvider<EntityT> {

	public AIAnimatedAction() {
		setAnimation(getAnimationLocation());
		setLastFrame(getAnimationLength());
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
	}

	@Override // Redeclared to make it clear
	public abstract float getWeight(EntityT entity, Entity target);

	@Override // Redeclared to make it clear
	public abstract boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target);

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
