package mhfc.net.common.ai.entity.boss.lagiacrus;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityLagiacrus;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class Sweep extends ActionAdapter<EntityLagiacrus> {

	private static final String ANIMATION = "mhfc:models/Lagiacrus/LagiacrusSweep.mcanm";
	private static final int FRAMES = 90;
	private static final double MAXDISTANCE = 40f;
	private static final float WEIGHT = 8F;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(115f, 50F, 9999999f);

	public Sweep() {
		this.setAnimation(ANIMATION);
		this.setLastFrame(FRAMES);
	}

	@Override
	public float getWeight() {
		EntityLagiacrus entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3d toTarget = WorldHelper.getVectorToTarget(entity, target);
		double dist = toTarget.lengthVector();
		if (dist > MAXDISTANCE) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}

	@Override
	protected void beginExecution() {
		EntityLagiacrus entity = this.getEntity();
		entity.getTurnHelper().updateTurnSpeed(14.17f);
		getEntity().playSound(MHFCSoundRegistry.getRegistry().lagiacrusSweep, 3.0F, 1.0F);
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
