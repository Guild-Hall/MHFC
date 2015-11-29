/**
 *
 */
package mhfc.net.common.ai.entity.deviljho;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.mob.EntityDeviljho;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.Vec3;

/**
 * @author WorldSEnder
 *
 */
public class DeviljhoBiteA extends ActionAdapter<EntityDeviljho> {
	private static final int LAST_FRAME = 35;
	private static final IDamageCalculator damageCalc = AIUtils
		.defaultDamageCalc(72f, 62f, 8888f);

	private static final double MAX_DIST = 5f;
	private static final double MAX_ANGLE = 0.155; // This is cos(30)
	private static final float WEIGHT = 3;

	public DeviljhoBiteA() {
		setAnimation("mhfc:models/Deviljho/bite2.mcanm");
		setLastFrame(LAST_FRAME);
	}

	@Override
	public float getWeight() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null)
			return DONT_SELECT;
		Vec3 toTarget = WorldHelper.getVectorToTarget(entity, target);
		double dist = toTarget.lengthVector();
		if (dist > MAX_DIST)
			return DONT_SELECT;
//		if (toTarget.normalize().dotProduct(entity.getLookVec()) < MAX_ANGLE)return DONT_SELECT;
		return WEIGHT;
	}

	@Override
	public void update() {
		if (this.getCurrentFrame() == 25) {
			getEntity().playSound("mhfc:deviljho-bite", 1.0F, 1.0F);
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			EntityDeviljho e = getEntity();
			e.moveForward(1, false);
		}
		AIUtils.damageCollidingEntities(getEntity(), damageCalc);
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 30);
	}
}
