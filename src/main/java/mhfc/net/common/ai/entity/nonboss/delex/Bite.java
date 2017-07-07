package mhfc.net.common.ai.entity.nonboss.delex;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.JumpAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackTargetAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpTimingAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IJumpProvider;
import mhfc.net.common.ai.general.provider.impl.IHasJumpProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.entity.monster.EntityDelex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class Bite extends JumpAction<EntityDelex> implements IHasJumpProvider<EntityDelex> {

	private final IJumpProvider<EntityDelex> ATTACK;
	{
		IJumpParameterProvider<EntityDelex> jumpParameter = new AttackTargetAdapter<>(10F);
		IJumpTimingProvider<EntityDelex> jumpTiming = new JumpTimingAdapter<>(1, 10, 14);
		final IAnimationProvider animation = new AnimationAdapter(this, "mhfc:models/delex/delexbite.mcanm", 25);
		ATTACK = new JumpAdapter<>(
				animation,
				new DamageAdapter(AIUtils.defaultDamageCalc(60, 45, 99999)),
				jumpParameter,
				jumpTiming);
	}

	public Bite() {}

	@Override
	protected float computeSelectionWeight() {
		EntityDelex entity = this.getEntity();
		target = entity.getAttackTarget();


		if (this.getCurrentAnimation() != null) {
		if (target == null) {
			return DONT_SELECT;
		}
			Vec3d toTarget = WorldHelper.getVectorToTarget(entity, target);
			double dist = toTarget.lengthVector();
		if (dist > 8F) {
			return DONT_SELECT;
		}
		}
		return 6F;
	}
	
	@Override
	protected void onUpdate() {
		super.onUpdate();
	}


	@Override
	public IJumpProvider<EntityDelex> getJumpProvider() {
		return ATTACK;
	}

}
