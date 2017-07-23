package mhfc.net.common.ai.entity.monsters.delex;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.JumpAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackTargetAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpTimingAdapter;
import mhfc.net.common.ai.general.provider.composite.IJumpProvider;
import mhfc.net.common.ai.general.provider.impl.IHasJumpProvider;
import mhfc.net.common.entity.monster.EntityDelex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class Bite extends JumpAction<EntityDelex> implements IHasJumpProvider<EntityDelex> {

	public Bite() {}

	@Override
	protected float computeSelectionWeight() {
		EntityDelex entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
			Vec3d toTarget = WorldHelper.getVectorToTarget(entity, target);
			double dist = toTarget.lengthVector();
		if (dist > 8F) {
			return DONT_SELECT;
		}

		return 6F;
	}
	
	@Override
	protected void onUpdate() {
		super.onUpdate();
	}


	@Override
	public IJumpProvider<EntityDelex> getJumpProvider() {
		return new JumpAdapter<EntityDelex>(
				new AnimationAdapter(this, "mhfc:models/delex/delexbite.mcanm", 25),
				new DamageAdapter(AIUtils.defaultDamageCalc(16, 45, 99999)),
				new AttackTargetAdapter<EntityDelex>(10F),
				new JumpTimingAdapter<EntityDelex>(1, 10, 14));
	}

}
