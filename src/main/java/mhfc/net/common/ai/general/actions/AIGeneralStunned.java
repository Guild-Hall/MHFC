package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;

public abstract class AIGeneralStunned<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
		AIAnimatedAction<EntityT> {
	
	
	@Override
	public void beginExecution(){
		getEntity().playStunnedSound();
	}
	
	@Override
	protected void update() {

	}

	@Override
	public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
		return actor.isPotionActive(MHFCPotionRegistry.getRegistry().stun);
	}

}
