/**
 *
 */
package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.AIUtils;
import mhfc.net.common.ai.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.Vec3;

/**
 * @author WorldSEnder
 *
 */
public class BiteAttack extends AttackAdapter<EntityTigrex> {
	private static final int LAST_FRAME = 55;
	private static final IDamageCalculator damageCalc = AIUtils
		.defaultDamageCalc(34f, 62f, 700f);

	private static final double MAX_DIST = 3f;
	private static final double MAX_ANGLE = 0.155; // This is cos(30)
	private static final float WEIGHT = 15;

	public BiteAttack() {
		setAnimation("mhfc:models/Tigrex/rawr.mcanm");
		setLastFrame(LAST_FRAME);
	}

	@Override
	public float getWeight() {
		EntityTigrex tigrex = this.getEntity();
		target = tigrex.getAttackTarget();
		if (target == null)
			return DONT_SELECT;
		Vec3 toTarget = WorldHelper.getVectorToTarget(tigrex, target);
		double dist = toTarget.lengthVector();
		if (dist > MAX_DIST)
			return DONT_SELECT;
		if (toTarget.normalize().dotProduct(tigrex.getLookVec()) < MAX_ANGLE)
			return DONT_SELECT;
		return WEIGHT;
	}

	@Override
	public void update() {
		AIUtils.damageCollidingEntities(getEntity(), damageCalc);
	}
}
