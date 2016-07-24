package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityBarroth;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.Vec3;


public class Ram extends ActionAdapter<EntityBarroth> {
	private static final int LAST_FRAME = 75;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(95f, 50F, 9999999f);

	private static final double MAX_DIST = 5.5f;
	private static final double MAX_ANGLE = 0.155; // This is cos(30)
	private static final float WEIGHT = 15;

	public Ram() {
		setAnimation("mhfc:models/Barroth/BarrothRam.mcanm");
		setLastFrame(LAST_FRAME);
	}

	@Override
	protected void beginExecution() {}

	@Override
	public float getWeight() {
		EntityBarroth entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3 toTarget = WorldHelper.getVectorToTarget(entity, target);
		double dist = toTarget.lengthVector();
		if (dist > MAX_DIST) {
			return DONT_SELECT;
		}
		if (toTarget.normalize().dotProduct(entity.getLookVec()) < MAX_ANGLE) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}

	@Override
	public void update() {
		EntityBarroth entity = getEntity();
		
		if (entity.getAttackTarget() != null && this.getCurrentFrame() == 20) {
			entity.getAttackTarget().setVelocity(-0.8D, 1.8D, 0d);
		//	getEntity().playSound("mhfc:entity.bite", 2.0F, 1.0F);
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			entity.moveForward(1, false);
		}
		AIUtils.damageCollidingEntities(getEntity(), damageCalc);
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 30);
	}
}
