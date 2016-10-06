package mhfc.net.common.ai.entity.boss.lagiacrus;

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
import mhfc.net.common.entity.monster.EntityLagiacrus;

public class Bite extends DamagingAction<EntityLagiacrus> implements IHasAttackProvider {

	private static final int ANIM_FRAME = 60;
	private static final String ANIM_LOCATION = "mhfc:models/Lagiacrus/LagiacrusBite.mcanm";

	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(105F, 125F, 99999999F);

	private static double TARGET_DISTANCE = 8.5F;
	private static float AIM_ANGLE = 0.155f;

	private static float WEIGHT = 15;

	private final IAttackProvider ATTACK;
	{
		final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIM_LOCATION, ANIM_FRAME);
		final IDamageProvider DAMAGE = new DamageAdapter(DAMAGE_CALC);
		ATTACK = new AttackAdapter(ANIMATION, DAMAGE);
	}

	public Bite() {}

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
		if (getEntity().getAttackTarget() != null && this.getCurrentFrame() == 38) {
			getEntity().playSound(MHFCSoundRegistry.getRegistry().lagiacrusBite, 2.0F, 1.0F);
			getEntity().getAttackTarget().addVelocity(0.9D, 0.3D, 0.9D);
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			EntityLagiacrus entity = getEntity();
			entity.moveForward(0.2, false);
		}
		damageCollidingEntities();

	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 10 && frame < 25);
	}

}
