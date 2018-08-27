package mhfc.net.common.ai.entity.monsters.rathalos;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
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
import mhfc.net.common.entity.monster.wip.EntityRathalos;

public class FlyBack extends JumpAction<EntityRathalos> implements IHasJumpProvider<EntityRathalos> {

	public FlyBack() {}

	private final IJumpProvider<EntityRathalos> ATTACK;
	{
		IJumpParameterProvider<EntityRathalos> jumpParameter = new AttackTargetAdapter<EntityRathalos>(60F) {
			@Override
			public float getForwardVelocity(EntityRathalos entity) {
				return -1.8F;
			}
		};
		IJumpTimingProvider<EntityRathalos> jumpTiming = new JumpTimingAdapter<>(3, 15F, 15F);
		final IAnimationProvider animation = new AnimationAdapter(this, "mhfc:models/Rathalos/rathalosflighthover.mcanm", 50);
		ATTACK = new JumpAdapter<>(
				animation,
				new DamageAdapter(AIUtils.defaultDamageCalc(80, 45, 99999)),
				jumpParameter,
				jumpTiming);
	}


	@Override
	public IJumpProvider<EntityRathalos> getJumpProvider() {
		return ATTACK;
	}

	@Override
	protected float computeSelectionWeight() {
		EntityRathalos entity = getEntity();
		target = entity.getAttackTarget();

		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		float targetPoint = entity.getDistanceToEntity(target);
		if (targetPoint > 11F) {
			return DONT_SELECT;
		}

		return 12F;
	}

}
