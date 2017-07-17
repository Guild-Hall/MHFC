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
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class RamRun extends DamagingAction<EntityBarroth> implements IHasAttackProvider {

	protected float speed, turnrate;

	public RamRun(float speed, float turnrate) {
		this.speed = speed;
		this.turnrate = turnrate;

	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		EntityBarroth entity = this.getEntity();
		entity.getTurnHelper().updateTurnSpeed(14.17f);
	}

	@Override
	protected float computeSelectionWeight() {
		EntityBarroth entity = this.getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		Vec3d toTarget = WorldHelper.getVectorToTarget(entity, target);
		double dist = toTarget.lengthVector();
		if (dist > 30F) {
			return DONT_SELECT;
		}
		return 10;
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return new AttackAdapter(
				new AnimationAdapter(this, "mhfc:models/Barroth/BarrothRamRun.mcanm", 130),
				new DamageAdapter(AIUtils.defaultDamageCalc(115f, 50F, 9999999f)));
	}

	@Override
	public void onUpdate() {
		EntityBarroth entity = getEntity();
		entity.getTurnHelper().updateTargetPoint(target);
		super.onUpdate();
		damageCollidingEntities();
		if (this.getCurrentFrame() == 20) {

			getEntity().playSound(MHFCSoundRegistry.getRegistry().barrothCharge, 3.0F, 1.0F);
			entity.getTurnHelper().updateTurnSpeed(this.turnrate);
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
