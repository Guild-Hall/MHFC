package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.type.EntityMHFCBase;

public abstract class AIGeneralDeath<EntityT extends EntityMHFCBase<? super EntityT>> extends ActionAdapter<EntityT> {

	public static final int deathLingeringTicks = 30 * 15;
	protected String deathsoundlocation;

	public AIGeneralDeath(String dyingLocation, String sound) {
		setAnimation(dyingLocation);
		deathsoundlocation = sound;
		
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		getEntity().playSound(deathsoundlocation, 4.0F, 1.0F);
	}

	@Override
	protected void update() {
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
