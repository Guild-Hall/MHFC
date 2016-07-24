package mhfc.net.common.ai.entity.boss.lagiacrus;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityLagiacrus;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.Vec3;

public class Sweep extends ActionAdapter<EntityLagiacrus> {
	
	private static final String set_Animation = "mhfc:models/Lagiacrus/LagiacrusSweep.mcanm";
	private static final int set_Frame = 90;
	private static final double set_MaxDistance = 40f;
	private static final float set_Weight = 8F;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(115f, 50F, 9999999f);
	
	public Sweep()   {
		this.setAnimation(set_Animation);
		this.setLastFrame(set_Frame);
	}
	
	@Override
	public float getWeight() {
		EntityLagiacrus entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3 toTarget = WorldHelper.getVectorToTarget(entity, target);
		double dist = toTarget.lengthVector();
		if (dist > set_MaxDistance) {
			return DONT_SELECT;
		}
		return set_Weight;
	}

	@Override
	protected void beginExecution() {
		EntityLagiacrus entity = this.getEntity();
		entity.getTurnHelper().updateTurnSpeed(14.17f);
		getEntity().playSound("mhfc:lagiacrus.sweep", 3.0F, 1.0F);
	}

	@Override
	protected void update() {
		EntityLagiacrus entity = getEntity();
		AIUtils.damageCollidingEntities(getEntity(), damageCalc);
		entity.getTurnHelper().updateTargetPoint(target);
		if (this.getCurrentFrame() == 20) {
			entity.getTurnHelper().updateTurnSpeed(0.37f);
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			entity.moveForward(0.96, true);
		}
	}
	
	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 80);
	}



}
