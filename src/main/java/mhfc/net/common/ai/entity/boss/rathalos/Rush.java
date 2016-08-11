package mhfc.net.common.ai.entity.boss.rathalos;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityRathalos;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.Vec3;


public class Rush extends ActionAdapter<EntityRathalos> {
	private static final int LAST_FRAME = 60;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(100f, 50F, 9999999f);
	private static final double MAX_DIST = 20F;
	private static final float WEIGHT = 4;
	

	public Rush() {
		setAnimation("mhfc:models/Rathalos/RathalosRush.mcanm");
		setLastFrame(LAST_FRAME);
	}

	@Override
	protected void beginExecution() {
		EntityRathalos entity = getEntity();
		entity.getTurnHelper().updateTurnSpeed(14.17f);
	}

	@Override
	public float getWeight() {
		EntityRathalos entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3 toTarget = WorldHelper.getVectorToTarget(entity, target);
		double dist = toTarget.lengthVector();
		if (dist < MAX_DIST) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}

	@Override
	public void update() {
		EntityRathalos entity = getEntity();
		AIUtils.damageCollidingEntities(getEntity(), damageCalc);
		entity.getTurnHelper().updateTargetPoint(target);
		
		if (this.getCurrentFrame() == 20) {
			getEntity().playSound("mhfc:rathalos.charge", 3.0F, 1.0F);
			entity.getTurnHelper().updateTurnSpeed(0.24f);
			//ON run
			
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			entity.moveForward(0.73, true);
		}
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 60);
	}
}
