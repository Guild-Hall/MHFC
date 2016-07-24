package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.monster.EntityBarroth;

public class Idle extends ActionAdapter<EntityBarroth> {

	private static final int LAST_FRAME = 60;

	public Idle() {
		setLastFrame(LAST_FRAME);
		setAnimation("mhfc:models/Barroth/BarrothIdle.mcanm");
	}

	@Override
	public float getWeight() {
		EntityBarroth entity = this.getEntity();
		if (entity.isDead) {
			return DONT_SELECT;
		}
		target = entity.getAttackTarget();
		// if (target == null)
		// return DONT_SELECT;
		return rng().nextFloat() * 6;
	}

	@Override
	public void update() {
		EntityBarroth entity = this.getEntity();
		if (this.getCurrentFrame() == 50) {
			entity.playLivingSound();
		}
		// just a copy from roar the update method. nothing else
	}
}
