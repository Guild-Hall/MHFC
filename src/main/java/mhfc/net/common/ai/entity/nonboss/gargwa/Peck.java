package mhfc.net.common.ai.entity.nonboss.gargwa;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityGargwa;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.Vec3;

public class Peck extends ActionAdapter<EntityGargwa>  {

	private static int ANIM_FRAME = 30;
	
	private static IDamageCalculator DAMAGE = AIUtils.defaultDamageCalc(15F, 125F, 99999999F);
	
	private static double TARGET_DISTANCE = 5.2F;
	
	private static double AIM_ANGLE = 0.155;
	
	private static float WEIGHT = 4;
	
	public Peck()	 {
		setAnimation("mhfc:models/Gagua/GaguaPeck.mcanm");
		setLastFrame(ANIM_FRAME);
	}

	@Override
	public float getWeight() {
		EntityGargwa entity = this.getEntity();
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
		if (this.getCurrentFrame() == 38) {
		//	getEntity().playSound("mhfc:lagacirus.bite", 2.0F, 1.0F);
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			EntityGargwa entity = getEntity();
			entity.moveForward(0.4, false);
		}
		AIUtils.damageCollidingEntities(getEntity(), DAMAGE);
	}
	
	private boolean isMoveForwardFrame(int frame) {
		return (frame > 10 && frame < 25);
	}

}
