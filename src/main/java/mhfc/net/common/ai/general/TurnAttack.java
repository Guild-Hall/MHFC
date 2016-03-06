package mhfc.net.common.ai.general;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class TurnAttack extends ActionAdapter<EntityMHFCBase<?>> {

	private double minAngleCos, maxAngleCos;
	private float weight;
	private float turnRate;

	public TurnAttack(float minAngleDeg, float maxAngleDeg, float weight, float turnRate, int turnTime) {
		this.minAngleCos = Math.cos(Math.toRadians(minAngleDeg));
		this.maxAngleCos = Math.cos(Math.toRadians(maxAngleDeg));
		this.weight = weight;
		this.turnRate = turnRate;
		this.setLastFrame(turnTime);
	}

	protected boolean isValidTarget(Vec3 toTarget, Vec3 look) {
		toTarget = toTarget.normalize();
		double dot = toTarget.dotProduct(look);
		return dot < minAngleCos && dot > maxAngleCos;
	}

	@Override
	public float getWeight() {
		EntityMHFCBase<?> entity = getEntity();
		EntityLivingBase target = entity.getAttackTarget();
		if (target == null)
			return DONT_SELECT;
		Vec3 vec = WorldHelper.getVectorToTarget(entity, target);
		Vec3 look = entity.getLookVec();
		return isValidTarget(vec, look) ? weight : DONT_SELECT;
	}

	@Override
	public void update() {
		EntityMHFCBase<?> entity = getEntity();
		entity.getTurnHelper().updateTargetPoint(entity.getAttackTarget());
		entity.getTurnHelper().updateTurnSpeed(turnRate);
	}
}
