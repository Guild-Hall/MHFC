package mhfc.net.common.ai.entity.monsters.tigrex;

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
import mhfc.net.common.entity.creature.Tigrex;

public class BackOff extends JumpAction<Tigrex> implements IHasJumpProvider<Tigrex> {

	public BackOff() {}

	private final IJumpProvider<Tigrex> ATTACK;
	{
		IJumpParameterProvider<Tigrex> jumpParameter = new AttackTargetAdapter<Tigrex>(40F) {
			@Override
			public float getForwardVelocity(Tigrex entity) {
				return -3.5F;
			}
		};
		IJumpTimingProvider<Tigrex> jumpTiming = new JumpTimingAdapter<>(5, 2F, 2F);
		final IAnimationProvider animation = new AnimationAdapter(this, "mhfc:models/Tigrex/jump_away.mcanm", 25);
		ATTACK = new JumpAdapter<>(
				animation,
				new DamageAdapter(AIUtils.defaultDamageCalc(80, 45, 99999)),
				jumpParameter,
				jumpTiming);
	}


	@Override
	public IJumpProvider<Tigrex> getJumpProvider() {
		return ATTACK;
	}

	@Override
	protected float computeSelectionWeight() {
		Tigrex entity = getEntity();
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
