package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.DamageAreaAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.entity.monster.EntityTigrex;

public class ClawSwipe extends DamageAreaAction<EntityTigrex> implements IHasAttackProvider {

	protected float range, height, knockback, arc;

	public ClawSwipe(float range, float height, float knockback, float arc) {
		this.range = range;
		this.height = height;
		this.knockback = knockback;
		this.arc = arc;

	}



	@Override
	protected float getRange() {
		return this.range;
	}

	@Override
	protected float getKnockBack() {
		return this.knockback;
	}

	@Override
	protected float getArc() {
		return this.arc;
	}

	@Override
	protected float getHeight() {
		return this.height;
	}

	@Override
	protected float computeSelectionWeight() {
		EntityTigrex entity = this.getEntity();
		target = entity.getAttackTarget();
		float targetPoint = entity.getDistanceToEntity(target);
		if (target == null) {
			return DONT_SELECT;
		}
		if (targetPoint > 10F) {
			return DONT_SELECT;
		}
		return 10F;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/Tigrex/clawswipe.mcanm", 41);
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return new AttackAdapter(
				getAnimProvider(),
				new DamageAdapter(AIUtils.defaultDamageCalc(10f, 5F, 9999999f)));
	}



}
