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

public class RamRun extends DamagingAction<EntityBarroth> implements IHasAttackProvider {

	protected float speed, turnrate, damage;

	public RamRun(float speed, float turnrate, float damage) {
		this.speed = speed;
		this.turnrate = turnrate;
		this.damage = damage;

	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		EntityBarroth entity = this.getEntity();
		entity.getTurnHelper().updateTurnSpeed(this.turnrate);
		entity.getTurnHelper().updateTargetPoint(targetPoint);
	}

	@Override
	protected float computeSelectionWeight() {
		EntityBarroth entity = this.getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		return 15;
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return new AttackAdapter(
				new AnimationAdapter(this, "mhfc:models/Barroth/BarrothRamRun.mcanm", 130),
				new DamageAdapter(AIUtils.defaultDamageCalc(this.damage, this.damage * 5F, 9999999f)));
	}

	@Override
	public void onUpdate() {
		EntityBarroth entity = getEntity();
		damageCollidingEntities();
		entity.getTurnHelper().updateTurnSpeed(0.1F);
		entity.getTurnHelper().updateTargetPoint(target);
		if (this.getCurrentFrame() == 20) {

			getEntity().playSound(MHFCSoundRegistry.getRegistry().barrothCharge, 3.0F, 1.0F);
			//ON run

		}
		if (this.getCurrentFrame() > 85) {
			EntityAIMethods.launch(entity, 1.0D, 1.5D, 1.0D);
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			entity.moveForward(this.speed, true);
		}
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 80);
	}
}
