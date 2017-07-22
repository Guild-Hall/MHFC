package mhfc.net.common.ai.entity.monsters.greatjaggi;

import mhfc.net.common.ai.general.AIUtils;
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
import mhfc.net.common.entity.monster.wip.EntityGreatJaggi;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class Whip extends DamagingAction<EntityGreatJaggi> implements IHasAttackProvider {

	private static final int LAST_FRAME = 35;
	private static final String ANIMATION_LOCATION = "mhfc:models/GreatJaggi/GreatJaggiTailWhip.mcanm";
	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(85, 50, 9999999f);

	private static final double MAX_DISTANCE = 16F;

	private static final float WEIGHT = 5F;

	private final IAttackProvider ATTACK;
	{
		final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);
		final IDamageProvider DAMAGE = new DamageAdapter(DAMAGE_CALC);
		ATTACK = new AttackAdapter(ANIMATION, DAMAGE);
	}

	public Whip() {}

	@Override
	protected float computeSelectionWeight() {
		EntityGreatJaggi entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3d LOOK_TARGET = WorldHelper.getVectorToTarget(entity, target);
		double distance = LOOK_TARGET.lengthVector();
		if (distance > MAX_DISTANCE) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return ATTACK;
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();

		EntityGreatJaggi entity = getEntity();
		if (this.getCurrentFrame() == 10) {
			entity.playSound(MHFCSoundRegistry.getRegistry().greatJaggiTailWhip, 2.0f, 1.0f);
		}
	}

}
