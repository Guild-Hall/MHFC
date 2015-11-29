package mhfc.net.common.ai.entity.deviljho;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.mob.EntityDeviljho;

public class DeviljhoIdle extends ActionAdapter<EntityDeviljho> {

	private static final int LAST_FRAME = 100;

	public DeviljhoIdle() {
		setLastFrame(LAST_FRAME);
		setAnimation("mhfc:models/Deviljho/idle.mcanm");
	}

	@Override
	public float getWeight() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		 if (target != null)
		 return DONT_SELECT;
		return rng().nextFloat() * 3;
	}

	@Override
	public void update() {
		EntityDeviljho entity = this.getEntity();
		if (this.getCurrentFrame() == 50) {
		entity.playLivingSound();
		// just a copy from roar the update method. nothing else
	}
}
}