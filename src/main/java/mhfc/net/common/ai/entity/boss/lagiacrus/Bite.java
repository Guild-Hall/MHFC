package mhfc.net.common.ai.entity.boss.lagiacrus;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityLagiacrus;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.Vec3;

public class Bite extends ActionAdapter <EntityLagiacrus> {
	
	private static int ANIM_FRAME = 60;
	
	private static IDamageCalculator DAMAGE = AIUtils.defaultDamageCalc(105F, 125F, 99999999F);
	
	private static double TARGET_DISTANCE = 8.5F;
	
	private static double AIM_ANGLE = 0.155;
	
	private static float WEIGHT = 15;
	
	public Bite()	 {
		setAnimation("mhfc:models/Lagiacrus/LagiacrusBite.mcanm");
		setLastFrame(ANIM_FRAME);
	}

	@Override
	public float getWeight() {
		EntityLagiacrus entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3 LOOK_TARGET = WorldHelper.getVectorToTarget(entity, target);
		double distance = LOOK_TARGET.lengthVector();
		if (distance > TARGET_DISTANCE) {
			return DONT_SELECT;
		}
		if (LOOK_TARGET.normalize().dotProduct(entity.getLookVec()) < AIM_ANGLE) {
				return DONT_SELECT;
		}
			return WEIGHT;
	}

	@Override
	public void update() {
		if (getEntity().getAttackTarget() != null && this.getCurrentFrame() == 38) {
			getEntity().playSound("mhfc:lagiacrus.bite", 2.0F, 1.0F);
			getEntity().getAttackTarget().addVelocity(0.9D, 0.3D, 0.9D);
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			EntityLagiacrus entity = getEntity();
			entity.moveForward(0.2, false);
			
		}
		AIUtils.damageCollidingEntities(getEntity(), DAMAGE);
		
	}
	
	private boolean isMoveForwardFrame(int frame) {
		return (frame > 10 && frame < 25);
	}

}
