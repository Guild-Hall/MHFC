package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.type.EntityMHFCBase;

public abstract class AIGeneralDeath<EntityT extends EntityMHFCBase<? super EntityT>> extends ActionAdapter<EntityT> {
	
	public static int deathLingeringTicks;
	protected String deathsoundlocation;
	
	public AIGeneralDeath(String dyingLocation, int deathLinger, String deathsound) {
		setAnimation(dyingLocation);
		deathLingeringTicks = deathLinger; // count how much time its death remains.
		deathsoundlocation = deathsound;
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
	}

	@Override
	protected void update() {
		if (this.getCurrentFrame() == 5){
		getEntity().playSound(deathsoundlocation, 1.0F, 1.0F);
		}
		
	} 

	@Override
	public boolean shouldContinue() {
		return true; // Indefinitely, we are dead
	}

	@Override
	public boolean forceSelection() {
		EntityT entity = getEntity();
		return entity == null ? false : entity.isDead;
	}

	@Override
	public float getWeight() {
		return 0; // but is being forced ya know
	}
}
