package mhfc.net.common.ai.rathalos;

import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityRathalos;

public class BiteAttack extends AttackAdapter<EntityRathalos> {

	public static final int LAST_FRAME = 40;
	private static float WEIGHT = 3.0f;

	public BiteAttack() {
		setAnimation("mhfc:models/Rathalos/"); // TODO path to real animation
		setLastFrame(LAST_FRAME);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public float getWeight() {
		if (getEntity().getAttackManager().getCurrentMode() != EntityRathalos.Stances.GROUND)
			return DONT_SELECT;
		return WEIGHT;
	}

}
