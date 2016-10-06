package mhfc.net.common.ai.entity.boss.tigrex;

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
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityTigrex;

public class Jump extends JumpAction<EntityTigrex> implements IHasJumpProvider<EntityTigrex> {

	private static final String ANIMATION = "mhfc:models/Tigrex/jump.mcanm";
	private static final int LAST_FRAME = 50;
	private static final int JUMP_FRAME = 20;
	private static final float TURN_RATE = 14;
	private static final float JUMP_TIME = 16f;

	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(45f, 62f, 999999F);
	private static final double MIN_DIST = 6f;
	private static final double MAX_DIST = 60f;
	private static final float MAX_ANGLE = 140f;
	private static final float SELECTION_WEIGHT = 1f;

	private final IJumpProvider<EntityTigrex> JUMP;
	{
		IJumpParameterProvider<EntityTigrex> params = new AttackTargetAdapter<>(JUMP_TIME);
		IJumpTimingProvider<EntityTigrex> timing = new JumpTimingAdapter<>(JUMP_FRAME, TURN_RATE, 0);
		IAnimationProvider animation = new AnimationAdapter(this, ANIMATION, LAST_FRAME);
		JUMP = new JumpAdapter<>(animation, new DamageAdapter(damageCalc), params, timing);
	}

	public Jump() {}

	private boolean shouldSelect() {
		return SelectionUtils.isInDistance(MIN_DIST, MAX_DIST, getEntity(), target)
				&& SelectionUtils.isInViewAngle(-MAX_ANGLE, MAX_ANGLE, getEntity(), target);
	}

	@Override
	protected float computeSelectionWeight() {
		return shouldSelect() ? SELECTION_WEIGHT : DONT_SELECT;
	}

	@Override
	public void onUpdate() {
		EntityTigrex entity = getEntity();
		if (this.getCurrentFrame() == 10) {
			entity.playSound(MHFCSoundRegistry.getRegistry().tigrexLeap, 2.0F, 1.0F);
		}
	}

	@Override
	public IJumpProvider<EntityTigrex> getJumpProvider() {
		return JUMP;
	}
}
