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
import mhfc.net.common.entity.creature.Delex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class Bite extends JumpAction<Delex> implements IHasJumpProvider<Delex> {

	public Bite() {
	}

	@Override
	protected float computeSelectionWeight() {
		Delex entity = this.getEntity();
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
	public IJumpProvider<Delex> getJumpProvider() {
		return new JumpAdapter<Delex>(new AnimationAdapter(this, "mhfc:models/delex/delexbite.mcanm", 25),
				new DamageAdapter(AIUtils.defaultDamageCalc(25f, 45, 99999)), new AttackTargetAdapter<Delex>(10F),
				new JumpTimingAdapter<Delex>(1, 10, 14));
	}

}
