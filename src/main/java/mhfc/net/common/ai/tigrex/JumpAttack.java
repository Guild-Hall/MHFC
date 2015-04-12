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
	private static final float TURN_RATE = 4;
	private static final float JUMP_SCALE = 0.95f;
	private static final float MAX_SCALE = 3;
	private static final float MIN_SCALE = 0.8f;
	private static final float JUMP_HEIGHT = 0.5f;

	private static final IDamageCalculator damageCalc = AIUtils
		.defaultDamageCalc(32f, 62f, 500f);
	private static final double MIN_DIST = 10f;
	private static final float MAX_ANGLE = -0.1f;
	private static final float SELECTION_WEIGHT = 5f;

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
		if (toTarget.normalize().dotProduct(tigrex.getLookVec()) < MAX_ANGLE)
			return DONT_SELECT;
		double dist = toTarget.lengthVector();
		return MIN_DIST > dist ? DONT_SELECT : SELECTION_WEIGHT;
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
			float distanceMult = JUMP_SCALE;
			distanceMult *= Math.sqrt(getEntity().getDistanceToEntity(
				getEntity().getAttackTarget()));
			distanceMult = Math.min(MAX_SCALE, Math
				.max(distanceMult, MIN_SCALE));
			getEntity().addVelocity(look.xCoord * JUMP_SCALE * distanceMult,
				JUMP_HEIGHT, look.zCoord * JUMP_SCALE * distanceMult);
		} else {
			AIUtils.damageCollidingEntities(getEntity(), damageCalc);
		}
	}

}
