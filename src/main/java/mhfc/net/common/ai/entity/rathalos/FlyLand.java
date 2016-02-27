package mhfc.net.common.ai.entity.rathalos;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.monster.EntityRathalos;
import mhfc.net.common.entity.monster.EntityRathalos.Stances;

public class FlyLand extends ActionAdapter<EntityRathalos> {

	public static final float WEIGHT = 1.0f;
	private static final int LAND_LAST_FRAME = 10;

	boolean hasTouchedDown;

	public FlyLand() {}

	@Override
	public float getWeight() {
		EntityRathalos rathalos = this.getEntity();
		if (rathalos.getStance() != Stances.FLYING)
			return DONT_SELECT;
		return WEIGHT;
	}

	@Override
	public void beginExecution() {
		setAnimation("mhfc:models/Rathalos/RathalosIntoFlight.mcanm");
		setLastFrame(LAND_LAST_FRAME);
		hasTouchedDown = false;
	}

	@Override
	public void update() {
		if (!hasTouchedDown) {
			EntityRathalos entity = getEntity();
			if (!entity.isAirBorne) {
				hasTouchedDown = true;
				entity.setStance(Stances.GROUND);
			}
			entity.fallDistance = 0;
		} else {

		}
	}

	@Override
	public boolean shouldContinue() {
		return hasTouchedDown && super.shouldContinue();
	}

}
