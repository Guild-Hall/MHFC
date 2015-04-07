package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.AIUtils;
import mhfc.net.common.ai.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.Vec3;

public class JumpAttack extends AttackAdapter<EntityTigrex> {
	private static final int LAST_FRAME = 80;
	private static final int JUMP_FRAME = 20;
	private static final float TURN_RATE = 3;
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
		if (toTarget.dotProduct(tigrex.getLookVec()) < 0)
			return DONT_SELECT;
		double dist = toTarget.lengthVector();
		return MIN_DIST < dist ? DONT_SELECT : 5f;
	}

	@Override
	public void beginExecution() {
		getEntity().getTurnHelper().updateTurnSpeed(TURN_RATE);
	}

	@Override
	public void update() {
		Vec3 look = getEntity().getLookVec();
		int frame = getRecentFrame();
		if (frame < JUMP_FRAME) {
			getEntity().getTurnHelper().updateTargetPoint(
				getEntity().getAttackTarget());
		} else if (frame == JUMP_FRAME) {
			AIUtils.damageCollidingEntities(getEntity(), damageCalc);
			float scale = 1.4f;
			getEntity().addVelocity(look.xCoord * scale, 0.35f * scale,
				look.zCoord * scale);
		}
	}

}
