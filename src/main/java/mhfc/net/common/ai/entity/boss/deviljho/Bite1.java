/**
 *
 */
package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.Vec3;

/**
 * @author WorldSEnder
 *
 */
public class Bite1 extends ActionAdapter<EntityDeviljho> {
	@SuppressWarnings("unused")
	private static final String ANIMATION = "mhfc:models/Deviljho/bite2.mcanm";
	private static final int LAST_FRAME = 35;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(120f, 50F,9999999f);

	private static final double MAX_DIST = 5f;
	private static final float WEIGHT = 5;

	public Bite1() {
		setAnimation("mhfc:models/Deviljho/bite2.mcanm");
		setLastFrame(LAST_FRAME);
	}

	@Override
	public float getWeight() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3 toTarget = WorldHelper.getVectorToTarget(entity, target);
		double dist = toTarget.lengthVector();
		if (dist > MAX_DIST) {
			return DONT_SELECT;
		}
		// if (toTarget.normalize().dotProduct(entity.getLookVec()) <
		// MAX_ANGLE)return DONT_SELECT;
		return WEIGHT;
	}

	@Override
	public void update() {
		
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		
		if (this.getCurrentFrame() == 25) {
			getEntity().playSound("mhfc:deviljho.bitea", 2.0F, 1.0F);
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
