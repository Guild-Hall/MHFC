package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.util.Vec3;

public abstract class AIGeneralJumpAttack<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
		AIAnimatedAction<EntityT> implements IJumpProvider<EntityT> {

	protected float forwardVelocityCap = 20f;
	protected float upwardVelocityCap = 20f;

	@Override
	public void beginExecution() {
		super.beginExecution();
		dmgHelper.setDamageCalculator(getDamageCalculator());
		getEntity().getTurnHelper().updateTurnSpeed(getTurnRate(getEntity(), 0));
	}

	protected Vec3 getJumpVector(Vec3 lookVector) {
		return Vec3.createVectorHelper(lookVector.xCoord, 0, lookVector.zCoord);
	}

	@Override
	public void update() {

		EntityT entity = getEntity();
		Vec3 forward = getJumpVector(entity).normalize();
		int frame = getCurrentFrame();
		float turnRate = getTurnRate(entity, frame);
		if (turnRate > 0) {
			entity.getTurnHelper().updateTurnSpeed(turnRate);
			entity.getTurnHelper().updateTargetPoint(getEntity().getAttackTarget());
		}
		if (isJumpFrame(entity, frame)) {
			float upVelocity = getInitialUpVelocity(entity);
			float forwardVelocity = getForwardVelocity(entity);
			upVelocity = Math.min(upVelocity, upwardVelocityCap);
			forwardVelocity = Math.max(Math.min(forwardVelocity, forwardVelocityCap), -forwardVelocityCap);
			entity.motionX = forward.xCoord * forwardVelocity;
			entity.motionY = upVelocity;
			entity.motionZ = forward.zCoord * forwardVelocity;
			entity.isAirBorne = true;
		}
		if (isDamageFrame(entity, frame)) {
			AIUtils.damageCollidingEntities(getEntity(), dmgHelper.getCalculator());
		}
	}

}
