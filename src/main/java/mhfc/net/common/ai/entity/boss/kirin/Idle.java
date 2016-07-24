package mhfc.net.common.ai.entity.boss.kirin;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.monster.EntityKirin;

public class Idle extends ActionAdapter<EntityKirin> {

	private static final int LAST_FRAME = 160;

	public Idle() {
		setLastFrame(LAST_FRAME);
		setAnimation("mhfc:models/Kirin/KirinIdle.mcanm");
	}

	@Override
	public float getWeight() {
		EntityKirin entity = this.getEntity();
		if (entity.isDead) {
			return DONT_SELECT;
		}
		target = entity.getAttackTarget();
		// if (target == null)
		// return DONT_SELECT;
		return rng().nextFloat() * 3;
	}

	@Override
	public void update() {
		EntityKirin entity = this.getEntity();
		if (this.getCurrentFrame() == 10) {
			entity.playLivingSound();
		}
		// just a copy from roar the update method. nothing else
	}
}
