package mhfc.net.common.ai.entity.monsters.lagiacrus;

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
import mhfc.net.common.entity.creature.Lagiacrus;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class Sweep extends DamagingAction<Lagiacrus> implements IHasAttackProvider {

	private static final int FRAMES = 90;
	private static final String ANIMATION_LOCATION = "mhfc:models/Lagiacrus/LagiacrusSweep.mcanm";

	private static final double MAXDISTANCE = 40f;
	private static final float WEIGHT = 8F;

	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(115f, 50F, 9999999f);

	private final IAttackProvider ATTACK;
	{
		final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, FRAMES);
		final IDamageProvider DAMAGE = new DamageAdapter(DAMAGE_CALC);
		ATTACK = new AttackAdapter(ANIMATION, DAMAGE);
	}

	public Sweep() {}

	@Override
	protected float computeSelectionWeight() {
		Lagiacrus entity = this.getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		Vec3d toTarget = WorldHelper.getVectorToTarget(entity, target);
		double dist = toTarget.length();
		if (dist > MAXDISTANCE) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return ATTACK;
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		Lagiacrus entity = this.getEntity();
		entity.getTurnHelper().updateTurnSpeed(14.17f);
		getEntity().playSound(MHFCSoundRegistry.getRegistry().lagiacrusSweep, 3.0F, 1.0F);
	}

	@Override
	protected void onUpdate() {
		this.damageCollidingEntities();
		Lagiacrus entity = getEntity();
		entity.getTurnHelper().updateTargetPoint(target);
		if (this.getCurrentFrame() == 20) {
			entity.getTurnHelper().updateTurnSpeed(0.37f);
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			entity.moveForward(0.96, true);
		}
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 80);
	}

}
