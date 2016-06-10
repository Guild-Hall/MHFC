package mhfc.net.common.ai.entity.deviljho;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.monster.EntityDeviljho;

public class DeviljhoDeath extends ActionAdapter<EntityDeviljho> {

	private static final int LAST_FRAME = 50;

	public DeviljhoDeath() {
		setLastFrame(LAST_FRAME);
		setAnimation("mhfc:models/Deviljho/DeviljhoDeath.mcanm");
	}

	@Override
	public float getWeight() {
		EntityDeviljho entity = this.getEntity();
		if (entity.getMaxHealth() > 0) {
			return DONT_SELECT;
		}
		return 5;
	}

	@Override
	protected void beginExecution() {
		EntityDeviljho entity = this.getEntity();
		entity.deathTime++;
		if (entity.deathTime > 50) {
			entity.setDead();
		}
	}

	@Override
	protected void update() {

	}

}
