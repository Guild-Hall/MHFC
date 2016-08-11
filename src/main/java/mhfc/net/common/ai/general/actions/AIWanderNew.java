package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;

public abstract class AIWanderNew <EntityT extends EntityMHFCBase<? super EntityT>> extends AIAnimatedAction<EntityT> {
	
	private ISelectionPredicate.SelectIdleAdapter<EntityT> selectIdleAdapter;
	
	public AIWanderNew() {
		selectIdleAdapter = new ISelectionPredicate.SelectIdleAdapter<EntityT>();
	}
	
	@Override
	public void beginExecution() {
		super.beginExecution();
	}
	
	@Override
	public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
		return selectIdleAdapter.shouldSelectAttack(attack, actor, target);
	}

	@Override
	protected void update() {
		if (isMoveForwardFrame(getCurrentFrame())) {
			getEntity().moveForward(0.63, true);
		}
	}
	
	public int initState = 20;
	public int finalState = 80;
	
	protected boolean isMoveForwardFrame(int frame) {
		return (frame > initState && frame < finalState);//Should return like this (frame > 20 && frame < 80)
	}
	
	
	
}
