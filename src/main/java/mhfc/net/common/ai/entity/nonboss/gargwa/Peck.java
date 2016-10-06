package mhfc.net.common.ai.entity.nonboss.gargwa;

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
import mhfc.net.common.entity.monster.EntityGargwa;

public class Peck extends DamagingAction<EntityGargwa> implements IHasAttackProvider {

	private static int ANIM_FRAME = 30;
	private static final String ANIMATION_LOCATION = "mhfc:models/Gagua/GaguaPeck.mcanm";

	private static IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(15F, 125F, 99999999F);

	private static float AIM_ANGLE = 30f;
	private static double TARGET_DISTANCE = 5.2F;

	private static float WEIGHT = 4;

	private final IAttackProvider ATTACK;
	{
		IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, ANIM_FRAME);
		ATTACK = new AttackAdapter(ANIMATION, new DamageAdapter(DAMAGE_CALC));
	}

	public Peck() {}

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
			getEntity().playSound(MHFCSoundRegistry.getRegistry().gargwaBite, 2.0F, 1.0F);
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			EntityGargwa entity = getEntity();
			entity.moveForward(0.4, false);
		}
		damageCollidingEntities();
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 10 && frame < 25);
	}

}
