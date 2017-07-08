package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.general.AIUtils;
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
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class TailWhip extends DamagingAction<EntityTigrex> implements IHasAttackProvider {

	private static final int LAST_FRAME = 60;
	private static final String ANIMATION_LOCATION = "mhfc:models/Tigrex/tailswipe.mcanm";
	private static final double MAX_DISTANCE = 10F;
	private static final float WEIGHT = 5.5F;

	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(5, 156, 9999999f);

	private final IAttackProvider ATTACK;
	{
		final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);
		ATTACK = new AttackAdapter(ANIMATION, new DamageAdapter(DAMAGE_CALC));
	}

	public TailWhip() {}

	@Override
	protected float computeSelectionWeight() {
		EntityTigrex tigrex = this.getEntity();
		target = tigrex.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3d toTarget = WorldHelper.getVectorToTarget(tigrex, target);
		double dist = toTarget.lengthVector();
		if (dist > MAX_DISTANCE) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}
	
	@Override
	protected void beginExecution() {
		super.beginExecution();
		this.damageCollidingEntities();
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return ATTACK;
	}

	@Override
	public void onUpdate() {
		if (this.getCurrentFrame() <= 10) {
			getEntity().getTurnHelper().updateTargetPoint(targetPoint);
			getEntity().getTurnHelper().updateTurnSpeed(30.0f);
		}
		
		if (this.getCurrentFrame() == 12) {
			getEntity().playSound(MHFCSoundRegistry.getRegistry().tigrexTailWhip, 2.0F, 1.0F);
		}
		
		if (this.getCurrentFrame() == 25) {

		}

		super.onUpdate();
	}

}
