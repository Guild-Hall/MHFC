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
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.entity.monster.EntityTigrex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class TailWhip extends DamagingAction<EntityNargacuga> implements IHasAttackProvider {

	private static final int ANIMATION_LENGTH = 0;
	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/TailSwipeRight.mcanm";

	private static final float MIN_ANGLE = 0;
	private static final float MAX_ANGLE = -150;
	private static final float MAX_DISTANCE = 15;
	private static final float WEIGHT = 6F;

	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(100, 500, 3333333);

	private final IAttackProvider ATTACK;
	{
		final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, ANIMATION_LENGTH);
		final IDamageProvider DAMAGE = new DamageAdapter(DAMAGE_CALC);
		ATTACK = new AttackAdapter(ANIMATION, new DamageAdapter(DAMAGE_CALC));
	}

	public TailWhip() {}

	@Override
	protected float computeSelectionWeight() {
		EntityNargacuga e = this.getEntity();
		target = e.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3d toTarget = WorldHelper.getVectorToTarget(e, target);
		double dist = toTarget.lengthVector();
		if (dist > MAX_DISTANCE) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return ATTACK;
	}
	
	@Override
	public void onUpdate() {
		EntityNargacuga entity = getEntity();
		damageCollidingEntities();
		if (this.getCurrentFrame() <= 10) {
			damageCollidingEntities();
			getEntity().getTurnHelper().updateTargetPoint(targetPoint);
			getEntity().getTurnHelper().updateTurnSpeed(6.0f);
		}
		
		if (this.getCurrentFrame() == 12) {
			entity.playSound(MHFCSoundRegistry.getRegistry().nargacugaTailWhip, 2.0F, 1.0F);
		}
		
		if (this.getCurrentFrame() == 25) {
			damageCollidingEntities();
		}
	}

}
