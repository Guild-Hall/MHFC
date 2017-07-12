package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.entity.EntityAIMethods;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.DamagingAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.entity.monster.EntityBarroth;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class Ram extends DamagingAction<EntityBarroth> implements IHasAttackProvider {


	public Ram() {}

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
		return 3.5F;
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return new AttackAdapter(
				new AnimationAdapter(this, "mhfc:models/Barroth/BarrothRam.mcanm", 75),
				new DamageAdapter(AIUtils.defaultDamageCalc(95f, 50F, 9999999f)));
	}

	@Override
	public void onUpdate() {
		EntityBarroth entity = getEntity();
		EntityAIMethods.launch(entity, 1.0D, 5.5D, 1.0D);
		if (isMoveForwardFrame(getCurrentFrame())) {
			entity.moveForward(1, false);
		}
		super.onUpdate();
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 30);
	}
}
