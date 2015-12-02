package mhfc.net.common.ai.entity.rathalos;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.monster.EntityRathalos;

public class FlyStart extends ActionAdapter<EntityRathalos> {

	public static final int LAST_FRAME = 55;
	public static final float WEIGHT = 3.0f;

	public FlyStart() {
		setAnimation("mhfc:models/Rathalos/RathalosFlightHover.mcanm");
		setLastFrame(LAST_FRAME);
	}

	@Override
	public void beginExecution() {
		EntityRathalos entity = getEntity();
		entity.getAttackManager().setNextStance(EntityRathalos.Stances.FLYING);
	}

	@Override
	public void update() {
		EntityRathalos entity = getEntity();
		if (getCurrentFrame() < 20)
			entity.moveEntity(0, 1f, 0);
		entity.motionY = 0;
	}

	@Override
	public float getWeight() {
		if (getEntity().getAttackManager().getCurrentStance() != EntityRathalos.Stances.GROUND)
			return DONT_SELECT;
		return WEIGHT;
	}

}
