package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.AIUtils;
import mhfc.net.common.ai.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.Vec3;

public class JumpAttack extends AttackAdapter<EntityTigrex> {
	private static final int LAST_FRAME = 80;
	private static final IDamageCalculator damageCalc = AIUtils
			.defaultDamageCalc(2.2f, 62f, 400f);
	private static final double MIN_DIST = 10f;

	public JumpAttack() {
		setAnimation("mhfc:models/Tigrex/jump.mcanm");
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
		return MIN_DIST < dist ? DONT_SELECT : 5f;
	}

	@Override
	public void beginExecution() {
		Vec3 look = getEntity().getLookVec();
		float scale = 1.25f;
		getEntity().addVelocity(look.xCoord * scale, 0.3f * scale,
				look.zCoord * scale);
	}

	@Override
	public void update() {
		AIUtils.damageCollidingEntities(getEntity(), damageCalc);
	}

}
