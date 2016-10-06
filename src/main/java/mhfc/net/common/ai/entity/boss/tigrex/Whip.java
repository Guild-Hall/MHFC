package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.DamagingAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityTigrex;

public class Whip extends DamagingAction<EntityTigrex> implements IHasAttackProvider {

	private static final int LAST_FRAME = 60;
	private static final String ANIMATION_LOCATION = "mhfc:models/Tigrex/tailswipe.mcanm";

	private static final double MIN_DIST = 0f;
	private static final double MAX_DISTANCE = 12F;
	private static final float MIN_RIGHT_ANGLE = 10f;

	private static final float WEIGHT = 5;

	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(102, 156, 9999999f);

	private final IAttackProvider ATTACK;
	{
		final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);
		ATTACK = new AttackAdapter(ANIMATION, new DamageAdapter(DAMAGE_CALC));
	}

	public Whip() {}

	private boolean shouldSelect() {
		return SelectionUtils.isInDistance(MIN_DIST, MAX_DISTANCE, getEntity(), target)
				&& SelectionUtils.isInViewAngle(MIN_RIGHT_ANGLE, 180, getEntity(), target);
	}

	@Override
	protected float computeSelectionWeight() {
		return shouldSelect() ? WEIGHT : DONT_SELECT;
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return ATTACK;
	}

	@Override
	public void onUpdate() {
		EntityTigrex entity = getEntity();
		if (this.getCurrentFrame() == 12) {
			entity.playSound(MHFCSoundRegistry.getRegistry().tigrexTailWhip, 2.0F, 1.0F);
		}
	}

}
