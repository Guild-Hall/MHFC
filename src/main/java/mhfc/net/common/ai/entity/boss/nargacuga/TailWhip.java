package mhfc.net.common.ai.entity.boss.nargacuga;

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
import mhfc.net.common.ai.general.provider.simple.IDamageProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;

public class TailWhip extends DamagingAction<EntityNargacuga> implements IHasAttackProvider {

	private static final int ANIMATION_LENGTH = 0;
	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/TailSwipeRight.mcanm";

	private static final float MIN_ANGLE = 0;
	private static final float MAX_ANGLE = -150;
	private static final float MAX_DISTANCE = 4;
	private static final float WEIGHT = 2;

	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(100, 500, 3333333);

	private final IAttackProvider ATTACK;
	{
		final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, ANIMATION_LENGTH);
		final IDamageProvider DAMAGE = new DamageAdapter(DAMAGE_CALC);
		ATTACK = new AttackAdapter(ANIMATION, DAMAGE);
	}

	public TailWhip() {}

	private boolean shouldSelect() {
		return SelectionUtils.isInDistance(0, MAX_DISTANCE, getEntity(), target)
				&& SelectionUtils.isInViewAngle(MIN_ANGLE, MAX_ANGLE, getEntity(), target);
	}

	@Override
	protected float computeSelectionWeight() {
		return shouldSelect() ? WEIGHT : DONT_SELECT;
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return ATTACK;
	}

}
