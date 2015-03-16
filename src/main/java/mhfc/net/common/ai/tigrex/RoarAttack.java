package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;

public class RoarAttack extends AttackAdapter<EntityTigrex> {
	private static final int LAST_FRAME = 70;

	public RoarAttack() {
		setAnimation("mhfc:models/Tigrex/rawr.mcanm");
		setLastFrame(LAST_FRAME);
	}

	@Override
	public float getWeight() {
		return rng().nextFloat() * 10;
	}

	@Override
	public void update() {
		// TODO stun all hunters in the near range if possible
	}
}
