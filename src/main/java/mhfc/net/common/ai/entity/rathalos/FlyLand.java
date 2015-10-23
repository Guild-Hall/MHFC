package mhfc.net.common.ai.entity.rathalos;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.mob.EntityRathalos;
import mhfc.net.common.entity.mob.EntityRathalos.Stances;
import net.minecraft.entity.EntityLiving;

public class FlyLand extends ActionAdapter<EntityRathalos> {

	public static final float WEIGHT = 1.0f;
	private static final int LAND_LAST_FRAME = 10;

	boolean hasTouchedDown;

	public FlyLand() {
		setAnimation("mhfc:models/Rathalos/RathalosIntoFlight.mcanm");
		setLastFrame(LAND_LAST_FRAME);
	}

	@Override
	public float getWeight() {
		EntityRathalos rathalos = this.getEntity();
		if (rathalos.getAttackManager().getCurrentStance() != Stances.FLYING)
			return DONT_SELECT;
		return WEIGHT;
	}

	@Override
	public void beginExecution() {
		EntityRathalos rathalos = this.getEntity();
		rathalos.getAttackManager().setNextStance(Stances.GROUND);
		hasTouchedDown = false;
	}

	@Override
	public void update() {
		if (!hasTouchedDown) {
			EntityLiving entity = getEntity();
			if (!entity.isAirBorne) {
				hasTouchedDown = true;
				setToNextFrame(-1);
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
