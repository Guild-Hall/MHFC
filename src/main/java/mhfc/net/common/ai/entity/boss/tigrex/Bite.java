/**
 *
 */
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

/**
 * @author WorldSEnder
 *
 */
public class Bite extends DamagingAction<EntityTigrex> implements IHasAttackProvider {

	private static final int LAST_FRAME = 55;
	private static final String ANIMATION_LOCATION = "mhfc:models/Tigrex/bite.mcanm";

	private static final double MAX_DIST = 5f;
	private static final float MAX_ANGLE = 0.155f; // This is cos(30)

	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(111f, 50F, 9999999f);

	private static final float WEIGHT = 15;

	private final IAttackProvider ATTACK;
	{
		final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);
		ATTACK = new AttackAdapter(ANIMATION, new DamageAdapter(damageCalc));
	}

	public Bite() {}

	private boolean shouldSelect() {
		return SelectionUtils.isInDistance(0, MAX_DIST, getEntity(), target)
				&& SelectionUtils.isInViewAngle(-MAX_ANGLE, MAX_ANGLE, getEntity(), target);
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
		if (this.getCurrentFrame() == 23) {
			getEntity().playSound(MHFCSoundRegistry.getRegistry().tigrexBite, 2.0F, 1.0F);
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			EntityTigrex tig = getEntity();
			tig.moveForward(1, false);
		}
		damageCollidingEntities();
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 30);
	}
}
