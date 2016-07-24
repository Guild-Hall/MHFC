package mhfc.net.common.ai.entity.boss.rathalos;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityRathalos;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.Vec3;

public class BiteRight extends ActionAdapter<EntityRathalos> {

	private static int ANIM_FRAME = 50;

	private static IDamageCalculator DAMAGE = AIUtils.defaultDamageCalc(95F, 125F, 99999999F);

	private static double TARGET_DISTANCE = 5F;

	private static double AIM_ANGLE = 0.155;

	private static float WEIGHT = 2F;

	public BiteRight() {
		setAnimation("mhfc:models/Rathalos/RathalosBiteRight.mcanm");
		setLastFrame(ANIM_FRAME);
	}

	@Override
	public float getWeight() {
		EntityRathalos entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3 LOOK_TARGET = WorldHelper.getVectorToTarget(entity, target);
		double distance = LOOK_TARGET.lengthVector();
		if (distance > TARGET_DISTANCE) {
			return DONT_SELECT;
		}
		if (LOOK_TARGET.normalize().dotProduct(entity.getLookVec()) < -AIM_ANGLE) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}

	@Override
	public void update() {
		if (this.getCurrentFrame() == 38) {
			getEntity().playSound("mhfc:rathalos.bite", 3.0F, 1.0F);
		}
		AIUtils.damageCollidingEntities(getEntity(), DAMAGE);
	}

}
