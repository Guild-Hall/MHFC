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
import mhfc.net.common.entity.monster.EntityRathalos;

public class Rush extends DamagingAction<EntityRathalos> implements IHasAttackProvider {
	private static final int LAST_FRAME = 60;
	private static final String ANIMATION_LOCATION = "mhfc:models/Rathalos/RathalosRush.mcanm";

	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(100f, 50F, 9999999f);
	private static final double MAX_DIST = 20F;
	private static final float WEIGHT = 4;

	private final IAttackProvider ATTACK;
	{
		final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);
		ATTACK = new AttackAdapter(ANIMATION, new DamageAdapter(DAMAGE_CALC));
	}

	public Rush() {}

	private boolean shouldSelect() {
		return SelectionUtils.isInDistance(0, MAX_DIST, getEntity(), target);
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
	protected void beginExecution() {
		EntityRathalos entity = getEntity();
		entity.getTurnHelper().updateTurnSpeed(14.17f);
	}

	@Override
	public void onUpdate() {
		EntityRathalos entity = getEntity();
		damageCollidingEntities();
		entity.getTurnHelper().updateTargetPoint(target);

		if (this.getCurrentFrame() == 20) {
			getEntity().playSound(MHFCSoundRegistry.getRegistry().rathalosCharge, 3.0F, 1.0F);
			entity.getTurnHelper().updateTurnSpeed(0.24f);
			//ON run

		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			entity.moveForward(0.73, true);
		}
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 60);
	}
}
