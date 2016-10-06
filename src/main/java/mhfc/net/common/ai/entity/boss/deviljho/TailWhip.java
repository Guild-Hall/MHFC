package mhfc.net.common.ai.entity.boss.deviljho;

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
import mhfc.net.common.entity.monster.EntityDeviljho;

public class TailWhip extends DamagingAction<EntityDeviljho> implements IHasAttackProvider {

	private static final int LAST_FRAME = 55; // FIXME exact value here please
	private static final String ANIMATION_LOCATION = "mhfc:models/Deviljho/tailswipe.mcanm";

	private static final double MIN_DIST = 2f;
	private static final double MAX_DISTANCE = 18F;
	private static final float MIN_RIGHT_ANGLE = 10f;
	private static final float WEIGHT = 5F;

	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(145, 50, 9999999f);
	private final IAttackProvider ATTACK;
	{
		IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);
		IDamageProvider DAMAGE = new DamageAdapter(DAMAGE_CALC);
		ATTACK = new AttackAdapter(ANIMATION, DAMAGE);
	}

	public TailWhip() {}

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
		EntityDeviljho entity = getEntity();
		if (this.getCurrentFrame() == 5) {
			entity.playSound(MHFCSoundRegistry.getRegistry().deviljhoTailWhip, 2.0F, 1.0F);
		}
	}

}
