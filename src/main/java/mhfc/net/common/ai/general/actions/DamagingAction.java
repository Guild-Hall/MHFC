package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.DamageCalculatorHelper;
import mhfc.net.common.ai.general.provider.requirements.INeedsDamageCalculator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.Vec3d;

public abstract class DamagingAction<T extends EntityCreature> extends AnimatedAction<T>
		implements
		INeedsDamageCalculator {
	private DamageCalculatorHelper dmgHelper;
	protected static Vec3d targetPoint;

	public DamagingAction() {
		dmgHelper = new DamageCalculatorHelper();
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		dmgHelper.reset();
		dmgHelper.setDamageCalculator(provideDamageCalculator());
		Entity target = getEntity().getAttackTarget();
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
		damageCollidingEntities();
	}

}
