/**
 *
 */
package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityTigrex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.Vec3;

/**
 * @author WorldSEnder
 *
 */
public class Bite extends ActionAdapter<EntityTigrex> {
	private static final int LAST_FRAME = 55;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(105f, 50F, 9999999f);

	private static final double MAX_DIST = 5f;
	private static final double MAX_ANGLE = 0.155; // This is cos(30)
	private static final float WEIGHT = 15;

	public Bite() {
		setAnimation("mhfc:models/Tigrex/bite.mcanm");
		setLastFrame(LAST_FRAME);
	}

	@Override
	protected void beginExecution() {}

	@Override
	public float getWeight() {
		EntityTigrex tigrex = this.getEntity();
		target = tigrex.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3 toTarget = WorldHelper.getVectorToTarget(tigrex, target);
		double dist = toTarget.lengthVector();
		if (dist > MAX_DIST) {
			return DONT_SELECT;
		}
		if (toTarget.normalize().dotProduct(tigrex.getLookVec()) < MAX_ANGLE) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}

	@Override
	public void update() {
		if (this.getCurrentFrame() == 23) {
			getEntity().playSound("mhfc:tigrex.bite", 2.0F, 1.0F);
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			EntityTigrex tig = getEntity();
			tig.moveForward(1, false);
		}
		AIUtils.damageCollidingEntities(getEntity(), damageCalc);
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 30);
	}
}
