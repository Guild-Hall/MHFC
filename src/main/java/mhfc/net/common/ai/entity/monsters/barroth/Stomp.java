package mhfc.net.common.ai.entity.monsters.barroth;

import mhfc.net.common.ai.entity.EntityAIMethods;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.DamagingAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityBarroth;

public class Stomp extends DamagingAction<EntityBarroth> implements IHasAttackProvider {
	private boolean thrown = false;
	public float damage;

	public Stomp(float damage) {
		this.damage = damage;
	}

	private void updateStomp() {
		EntityBarroth entity = this.getEntity();
		entity.getTurnHelper().updateTargetPoint(target);
		entity.getTurnHelper().updateTurnSpeed(30.0f);
		if (!entity.onGround || thrown || this.getCurrentFrame() < 19) {
			return;
		}
		EntityAIMethods.stompCracks(entity, 150);
		entity.playSound(MHFCSoundRegistry.getRegistry().barrothRam, 1.0F, 1.0F);
		thrown = true;
	}

	@Override
	protected void onUpdate() {
		EntityBarroth entity = this.getEntity();
		target = entity.getAttackTarget();
		damageCollidingEntities();
		updateStomp();
	}

	@Override
	protected float computeSelectionWeight() {
		EntityBarroth entity = this.getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		if (!SelectionUtils.isInDistance(0, 15, entity, target)) {
			return DONT_SELECT;
		}
		return 10F;
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		EntityBarroth entity = getEntity();
		entity.playSound(MHFCSoundRegistry.getRegistry().barrothHeadsmash, 2.0F, 1.0F);
		thrown = false;
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return new AttackAdapter(
				new AnimationAdapter(this, "mhfc:models/Barroth/BarrothStomp.mcanm", 85),
				new DamageAdapter(AIUtils.defaultDamageCalc(this.damage, this.damage * 5F, 9999999f)));
	}

}
