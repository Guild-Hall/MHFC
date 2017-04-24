package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.general.provider.requirements.INeedsJumpParameters;
import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.util.math.Vec3d;

public abstract class JumpAction<T extends EntityMHFCBase<? super T>> extends DamagingAction<T>
		implements
		INeedsJumpParameters<T> {

	protected static final float FORWARD_VEL_CAP = 20f;
	protected static final float UPWARD_VEL_CAP = 20f;

	private IJumpParameterProvider<T> jumpParameterProvider;
	private IJumpTimingProvider<T> jumpTimingProvider;

	@Override
	public void beginExecution() {
		jumpParameterProvider = provideJumpParameters();
		jumpTimingProvider = provideTimingParameters();
		System.out.println("AI Executed.");
		super.beginExecution();
		getEntity().getTurnHelper().updateTurnSpeed(jumpTimingProvider.getTurnRate(getEntity(), 0));
	}

	protected Vec3d getJumpVector(Vec3d lookVector) {
		return new Vec3d(lookVector.xCoord, 0, lookVector.zCoord);
	}

	private float capVelocity(float velocity, float cap) {
		assert cap >= 0;
		return Math.max(Math.min(velocity, cap), -cap);
	}

	@Override
	protected void onUpdate() {
		T entity = getEntity();
		Vec3d direction = jumpParameterProvider.getJumpVector(entity).normalize();
		int frame = getCurrentFrame();
		float turnRate = jumpTimingProvider.getTurnRate(entity, frame);
		if (turnRate > 0) {
			entity.getTurnHelper().updateTurnSpeed(turnRate);
			entity.getTurnHelper().updateTargetPoint(getEntity().getAttackTarget());
		}
		if (jumpTimingProvider.isJumpFrame(entity, frame)) {
			float upVelocity = capVelocity(jumpParameterProvider.getInitialUpVelocity(entity), UPWARD_VEL_CAP);
			float forwardVelocity = capVelocity(jumpParameterProvider.getForwardVelocity(entity), FORWARD_VEL_CAP);
			entity.motionX += direction.xCoord * forwardVelocity;
			entity.motionY += upVelocity;
			entity.motionZ += direction.zCoord * forwardVelocity;
			entity.isAirBorne = true;
		}
		if (jumpTimingProvider.isDamageFrame(entity, frame)) {
			super.onUpdate();
		}
	}

}
