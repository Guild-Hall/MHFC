package mhfc.net.common.ai.kirin;

import mhfc.net.common.entity.mob.EntityKirin;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class AIKirinAttack extends EntityAIBase {

	private float dist;
	private float actualDistSq;
	private EntityKirin entity;
	private EntityLivingBase attackTarget;

	public AIKirinAttack(EntityKirin par1, float f) {
		dist = f;
		actualDistSq = 0.0f;
		entity = par1;
		attackTarget = null;
	}

	public boolean isContinuous() {
		return false;
	}

	@Override
	public void resetTask() {
		super.resetTask();
	}

	@Override
	public boolean shouldExecute() {
		attackTarget = entity.getAttackTarget();
		if (attackTarget == null) {
			return false;
		}
		return actualDistSq <= dist * dist;
	}

	@Override
	public void startExecuting() {
		entity.attackEntityAtDistSq(attackTarget, actualDistSq);
	}

}
