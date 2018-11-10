package mhfc.net.common.ai.entity.monsters.barroth;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.DamagingAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.entity.CreatureAttributes;
import mhfc.net.common.entity.creature.Barroth;

public class Ram extends DamagingAction<Barroth> implements IHasAttackProvider {

	public float damage;

	public Ram(float damage) {
		this.damage = damage;
	}

	@Override
	protected float computeSelectionWeight() {
		Barroth entity = this.getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		if (!SelectionUtils.isInDistance(0, 15, entity, target)) {
			return DONT_SELECT;
		}
		return 5F;
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return new AttackAdapter(
				new AnimationAdapter(this, "mhfc:models/Barroth/BarrothRam.mcanm", 75),
				new DamageAdapter(AIUtils.defaultDamageCalc(this.damage, this.damage * 5F, 9999999f)));
	}

	@Override
	public void onUpdate() {
		Barroth entity = getEntity();
		CreatureAttributes.mountVelocity(entity, 1.0D, 5.5D, 1.0D);
		damageCollidingEntities();
		super.onUpdate();
	}
}
