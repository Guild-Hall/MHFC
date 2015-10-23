package mhfc.net.common.ai.entity.barroth;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.mob.EntityBarroth;

public class BarrothIdle extends ActionAdapter<EntityBarroth> {

	private static final int LAST_FRAME = 100;

	public BarrothIdle() {
		setLastFrame(LAST_FRAME);
		setAnimation("mhfc:models/Barroth/BarrothIdle.mcanm");
	}

	@Override
	public float getWeight() {
		EntityBarroth entity = this.getEntity();
		target = entity.getAttackTarget();
		// if (target == null)
		// return DONT_SELECT;
		return rng().nextFloat() * 3;
	}

	@Override
	public void update() {
		EntityBarroth entity = this.getEntity();
		entity.playLivingSound();
		// just a copy from roar the update method. nothing else
	}
}
