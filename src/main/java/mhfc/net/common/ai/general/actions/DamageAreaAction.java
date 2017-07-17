package mhfc.net.common.ai.general.actions;

import java.util.List;

import mhfc.net.common.ai.entity.EntityAIMethods;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.DamageCalculatorHelper;
import mhfc.net.common.ai.general.provider.requirements.INeedsDamageCalculator;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public abstract class DamageAreaAction<T extends EntityMHFCBase<?>> extends AnimatedAction<T>
		implements
		INeedsDamageCalculator {

	private DamageCalculatorHelper dmgHelper;
	protected Vec3d targetPoint;

	protected abstract float getRange();

	protected abstract float getKnockBack();

	protected abstract float getArc();

	protected abstract float getHeight();

	public DamageAreaAction() {
		dmgHelper = new DamageCalculatorHelper();
	}

	@Override
	protected void beginExecution() {
		dmgHelper.reset();
		dmgHelper.setDamageCalculator(provideDamageCalculator());
		targetPoint = target != null ? target.getPositionVector() : null;
		super.beginExecution();
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();

		if (target != null) {
			getEntity().getTurnHelper().updateTurnSpeed(30F);
			getEntity().getTurnHelper().updateTargetPoint(targetPoint);
		}

	}

	public void hitAreaEntities() {
		List<EntityLivingBase> affectedEntity = EntityAIMethods
				.getEntityLivingBaseNearby(getEntity(), getRange(), getRange(), getHeight(), getRange());
		@SuppressWarnings("unused")
		boolean hit = false;
		for (EntityLivingBase affectedentities : affectedEntity) {
			float angleofHit = (float) ((Math
					.atan2(affectedentities.posZ - getEntity().posZ, affectedentities.posX - getEntity().posX)
					* (180 / Math.PI) - 90) % 360);
			float attackerAngle = getEntity().renderYawOffset % 360;

			// Angle adjustment or relocating exact angle for area to get hit.
			if (angleofHit < 0) {
				angleofHit += 360;
			}
			if (attackerAngle < 0) {
				attackerAngle += 360;
			}

			float relativityAngle = angleofHit - attackerAngle;
			float distanceOfHit = (float) Math.sqrt(
					(affectedentities.posZ - getEntity().posZ) * (affectedentities.posZ - getEntity().posZ)
							+ (affectedentities.posX - getEntity().posX) * (affectedentities.posX - getEntity().posX));
			if (distanceOfHit <= getRange() && (relativityAngle <= getArc() / 2 && relativityAngle >= -getArc() / 2)
					|| (relativityAngle >= 360 - getArc() / 2 || relativityAngle <= -360 + getArc() / 2)) {
				AIUtils.damageEntitiesFromAI(getEntity(), affectedentities, dmgHelper.getCalculator());
				affectedentities.motionX *= getKnockBack();
				affectedentities.motionZ *= getKnockBack();
				hit = true;
			}
		}
	}

}
