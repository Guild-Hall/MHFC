package mhfc.net.common.ai.rathalos;

import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityRathalos;

public class FlyStart extends AttackAdapter<EntityRathalos> {

	public static final int LAST_FRAME = 55;
	public static final float WEIGHT = 3.0f;

	public FlyStart() {
		setLastFrame(LAST_FRAME);
	}

	@Override
	public void beginExecution() {
		EntityRathalos entity = getEntity();
		entity.getAttackManager().setNextMode(EntityRathalos.Stances.FLYING);
	}

	@Override
	public void update() {
		EntityRathalos entity = getEntity();
		entity.moveFlying(0, 0.2f, 0);
	}

	@Override
	public float getWeight() {
		if (getEntity().getAttackManager().getCurrentMode() != EntityRathalos.Stances.GROUND)
			return DONT_SELECT;
		return WEIGHT;
	}

}
