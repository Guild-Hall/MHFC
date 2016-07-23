package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;

public abstract class AIGeneralPanic <EntityT extends EntityMHFCBase<? super EntityT>>
extends
AIAnimatedAction<EntityT> {
	
	  private double speed;
	    private double randPosX;
	    private double randPosY;
	    private double randPosZ;
	
	/**
	 * 
	 * This AI suits monsters that when get attack they try to find random areas to run...
	 * 
	 * 
	 * **/


	@Override
	public float getWeight(EntityT entity, Entity target) {
		return 0;
	}

	@Override
	public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
		return false;
	}
	
	@Override
	public void beginExecution(){
		this.getEntity().getNavigator().tryMoveToXYZ(randPosX, randPosY, randPosZ, speed);
	}

	@Override
	protected void update() {
		
		
	}

	@Override
	public String getAnimationLocation() {
		return null;
	}

	@Override
	public int getAnimationLength() {
		return 0;
	}
	
	

}
