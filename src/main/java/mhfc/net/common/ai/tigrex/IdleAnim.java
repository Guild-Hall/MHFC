package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;

public class IdleAnim extends AttackAdapter<EntityTigrex> {
	
	private static final int LAST_FRAME = 70;
	public IdleAnim() {
		setLastFrame(LAST_FRAME);
		setAnimation("mhfc:models/Tigrex/idle.mcanm");
	}

@Override
public float getWeight() {
	EntityTigrex tigrex = this.getEntity();
	if (target != null )
		return DONT_SELECT;
	return rng().nextFloat() *3;
}
@Override
public void update() {
	super.update();
	EntityTigrex tigrex = this.getEntity();
	// just a copy from roar the update method. nothing else
	}
}



