package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.DamageCalculatorHelper;
import mhfc.net.common.ai.general.provider.requirements.INeedsDamageCalculator;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.Vec3d;

public abstract class DamagingAction<T extends EntityCreature> extends AnimatedAction<T>
		implements
		INeedsDamageCalculator {
	private DamageCalculatorHelper dmgHelper;
	protected Vec3d targetPoint;

	public DamagingAction() {
		dmgHelper = new DamageCalculatorHelper();
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		dmgHelper.reset();
		dmgHelper.setDamageCalculator(provideDamageCalculator());
		targetPoint = target != null ? target.getPositionVector() : null;


	}

	protected void damage(Entity entity) {
		AIUtils.damageEntitiesFromAI(getEntity(), entity, dmgHelper.getCalculator());
	}

	protected void damageCollidingEntities() {
		AIUtils.damageCollidingEntities(this.getEntity(), dmgHelper.getCalculator());
	}

	@Override
	protected void onUpdate() {
		if (target != null) {
			((EntityMHFCBase<?>) getEntity()).getTurnHelper().updateTurnSpeed(50F);
			((EntityMHFCBase<?>) getEntity()).getTurnHelper().updateTargetPoint(targetPoint);
			((EntityMHFCBase<?>) getEntity()).getLookHelper().setLookPositionWithEntity(target, 15, 15);
			;
		}

	}

	protected void updateTurnHelper(Entity entity, float turnRate) {
		if (target != null) {
			((EntityMHFCBase<?>) getEntity()).getTurnHelper().updateTurnSpeed(turnRate);
			((EntityMHFCBase<?>) getEntity()).getTurnHelper().updateTargetPoint(targetPoint);
			((EntityMHFCBase<?>) getEntity()).getLookHelper().setLookPositionWithEntity(target, 15, 15);
		}

	}

}
