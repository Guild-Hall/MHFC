package mhfc.net.common.ai.entity.monsters.rathalos;

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
import mhfc.net.common.entity.creature.Rathalos;

public class BiteLeft extends DamagingAction<Rathalos> implements IHasAttackProvider {

	private static final int ANIM_FRAME = 50;
	private static final String ANIMATION_LOCATION = "mhfc:models/Rathalos/RathalosBiteLeft.mcanm";

	private static IDamageCalculator DAMAGE = AIUtils.defaultDamageCalc(95F, 125F, 99999999F);

	private static double TARGET_DISTANCE = 5F;
	private static float AIM_ANGLE = 0.155f;

	private static float WEIGHT = 2F;

	private final IAttackProvider ATTACK;
	{
		IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, ANIM_FRAME);
		ATTACK = new AttackAdapter(ANIMATION, new DamageAdapter(DAMAGE));
	}

	public BiteLeft() {}

	private boolean shouldSelect() {
		return SelectionUtils.isInDistance(0, TARGET_DISTANCE, getEntity(), target)
				&& SelectionUtils.isInViewAngle(-AIM_ANGLE, AIM_ANGLE, getEntity(), target);
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
		if (this.getCurrentFrame() == 38) {
			getEntity().playSound(MHFCSoundRegistry.getRegistry().rathalosBite, 3.0F, 1.0F);
		}
		damageCollidingEntities();
	}

}
