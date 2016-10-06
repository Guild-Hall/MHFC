package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.DamageCalculatorHelper;
import mhfc.net.common.ai.general.provider.requirements.INeedsDamageCalculator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;

public abstract class DamagingAction<T extends EntityCreature> extends AnimatedAction<T>
		implements
		INeedsDamageCalculator {
	private DamageCalculatorHelper dmgHelper;

	public DamagingAction() {
		dmgHelper = new DamageCalculatorHelper();
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		dmgHelper.reset();
		dmgHelper.setDamageCalculator(provideDamageCalculator());
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
