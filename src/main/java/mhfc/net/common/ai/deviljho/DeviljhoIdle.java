package mhfc.net.common.ai.deviljho;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.mob.EntityDeviljho;
import mhfc.net.common.entity.mob.EntityTigrex;

public class DeviljhoIdle extends ActionAdapter<EntityDeviljho> {

	private static final int LAST_FRAME = 160;

	public DeviljhoIdle() {
		setLastFrame(LAST_FRAME);
		setAnimation("mhfc:models/Deviljho/DeviljhoIdle.mhanm");
	}

	@Override
	public float getWeight() {
		EntityDeviljho deviljho = this.getEntity();
		target = deviljho.getAttackTarget();
		if (target != null)
			return DONT_SELECT;
		return rng().nextFloat() * 3;
	}

	@Override
	public void update() {
		EntityDeviljho deviljho = this.getEntity();
		deviljho.playLivingSound();
		// just a copy from roar the update method. nothing else
	}
}
