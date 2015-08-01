package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;

public class RoarAttack extends ActionAdapter<EntityTigrex> {
	private static final int LAST_FRAME = 70;

	public RoarAttack() {
		setAnimation("mhfc:models/Tigrex/rawr.mcanm");
		setLastFrame(LAST_FRAME);
	}

	@Override
	public float getWeight() {
		return rng().nextFloat() * 3;
	}

	@Override
	public void update() {
		// TODO stun all hunters in the near range if possible
	}
}
