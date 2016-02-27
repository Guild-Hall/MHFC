package mhfc.net.common.ai.entity.rathalos;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.monster.EntityRathalos;

public class BiteAttack extends ActionAdapter<EntityRathalos> {

	public static final String ANIMATION = "mhfc:models/Rathalos/RathalosBiteLeft.mcanm";
	public static final int LAST_FRAME = 40;
	private static float WEIGHT = 3.0f;

	public BiteAttack() {
		setAnimation(ANIMATION);
		setLastFrame(LAST_FRAME);
	}

	@Override
	public void update() {}

	@Override
	public float getWeight() {
		if (getEntity().getStance() != EntityRathalos.Stances.GROUND)
			return DONT_SELECT;
		return WEIGHT;
	}

}
