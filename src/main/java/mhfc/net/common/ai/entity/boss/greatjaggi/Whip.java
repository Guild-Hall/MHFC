package mhfc.net.common.ai.entity.boss.greatjaggi;

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
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityGreatJaggi;

public class Whip extends DamagingAction<EntityGreatJaggi> implements IHasAttackProvider {

	private static final int LAST_FRAME = 35;
	private static final String ANIMATION_LOCATION = "mhfc:models/GreatJaggi/GreatJaggiTailWhip.mcanm";
	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(85, 50, 9999999f);

	private static final double MIN_DISTANCE = 0f;
	private static final double MAX_DISTANCE = 16F;
	private static final float MIN_RIGHT_ANGLE = 5f;

	private static final float WEIGHT = 5F;

	private final IAttackProvider ATTACK;
	{
		final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);
		final IDamageProvider DAMAGE = new DamageAdapter(DAMAGE_CALC);
		ATTACK = new AttackAdapter(ANIMATION, DAMAGE);
	}

	public Whip() {}

	private boolean shouldSelect() {
		return SelectionUtils.isInDistance(MIN_DISTANCE, MAX_DISTANCE, getEntity(), target)
				&& SelectionUtils.isInViewAngle(MIN_RIGHT_ANGLE, 180f, getEntity(), target);
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
	protected void onUpdate() {
		damageCollidingEntities();

		EntityGreatJaggi entity = getEntity();
		if (this.getCurrentFrame() == 10) {
			entity.playSound(MHFCSoundRegistry.getRegistry().greatJaggiTailWhip, 2.0f, 1.0f);
		}
	}

}
