package mhfc.net.common.ai.rathalos;

import mhfc.net.MHFCMain;
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
		super.beginExecution();
		EntityRathalos entity = getEntity();
		entity.getAttackManager().setNextStance(EntityRathalos.Stances.FLYING);
	}

	@Override
	public void update() {
		super.update();
		MHFCMain.logger.info("Update of Fly");
		EntityRathalos entity = getEntity();
		entity.moveEntity(0, 0.5f, 0);
	}

	@Override
	public float getWeight() {
		if (getEntity().getAttackManager().getCurrentStance() != EntityRathalos.Stances.GROUND)
			return DONT_SELECT;
		return WEIGHT;
	}

}
