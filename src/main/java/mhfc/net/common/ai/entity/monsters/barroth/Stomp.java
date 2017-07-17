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

public class Stomp extends DamagingAction<EntityBarroth> implements IHasAttackProvider {
	private boolean thrown = false;

	public Stomp() {}

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
		updateStomp();
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
		if (dist > 15F) {
			return DONT_SELECT;
		}
		return 3.5F;
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
				new DamageAdapter(AIUtils.defaultDamageCalc(60f, 50F, 9999999f)));
	}

}
