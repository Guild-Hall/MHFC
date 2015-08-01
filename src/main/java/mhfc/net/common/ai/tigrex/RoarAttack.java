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
		return rng().nextFloat() * 1;
	}

	@Override
	public void update() {
		EntityTigrex tigrex = this.getEntity();
		
		if(this.getCurrentFrame() == 18)
			tigrex.playSound("mhfc:tigrex-roar", 4.0F, 1.0F);
		// TODO stun all hunters in the near range if possible
	}
}
