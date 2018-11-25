package mhfc.net.common.ai.entity.monsters.rathalos;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.JumpAction;
import mhfc.net.common.ai.general.provider.adapters.*;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IJumpProvider;
import mhfc.net.common.ai.general.provider.impl.IHasJumpProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.entity.creature.Rathalos;

public class FlyBack extends JumpAction<Rathalos> implements IHasJumpProvider<Rathalos> {

	public FlyBack() {}

	private final IJumpProvider<Rathalos> ATTACK;
	{
		IJumpParameterProvider<Rathalos> jumpParameter = new AttackTargetAdapter<Rathalos>(60F) {
			@Override
			public float getForwardVelocity(Rathalos entity) {
				return -1.8F;
			}
		};
		IJumpTimingProvider<Rathalos> jumpTiming = new JumpTimingAdapter<>(3, 15F, 15F);
		final IAnimationProvider animation = new AnimationAdapter(this, "mhfc:models/Rathalos/rathalosflighthover.mcanm", 50);
		ATTACK = new JumpAdapter<>(
				animation,
				new DamageAdapter(AIUtils.defaultDamageCalc(80, 45, 99999)),
				jumpParameter,
				jumpTiming);
	}


	@Override
	public IJumpProvider<Rathalos> getJumpProvider() {
		return ATTACK;
	}

	@Override
	protected float computeSelectionWeight() {
		Rathalos entity = getEntity();
		target = entity.getAttackTarget();

		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		float targetPoint = entity.getDistance(target);
		if (targetPoint > 11F) {
			return DONT_SELECT;
		}

		return 12F;
	}

}
