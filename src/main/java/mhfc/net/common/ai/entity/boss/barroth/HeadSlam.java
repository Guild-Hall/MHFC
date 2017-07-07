package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.entity.EntityAIMethods;
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
import mhfc.net.common.entity.monster.EntityBarroth;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class HeadSlam extends DamagingAction<EntityBarroth> implements IHasAttackProvider {

	private static final int ANIM_FRAME = 60;
	private static final String ANIM_LOCATION = "mhfc:models/barroth/headslam.mcanm";

	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(150F, 125F, 99999999F);

	private static double TARGET_DISTANCE = 40F;

	private static float WEIGHT = 5;

	private final IAttackProvider ATTACK;
	{
		final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIM_LOCATION, ANIM_FRAME);
		final IDamageProvider DAMAGE = new DamageAdapter(DAMAGE_CALC);
		ATTACK = new AttackAdapter(ANIMATION, DAMAGE);
	}

	public HeadSlam() {}

	@Override
	protected float computeSelectionWeight() {
		EntityBarroth entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3d toTarget = WorldHelper.getVectorToTarget(entity, target);
		double dist = toTarget.lengthVector();
		if (dist > TARGET_DISTANCE) {
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
		EntityBarroth entity = getEntity();
		if (getEntity().getAttackTarget() != null && this.getCurrentFrame() == 25) {
			
			getEntity().playSound(MHFCSoundRegistry.getRegistry().barrothHeadsmash, 2.0F, 1.0F);
			EntityAIMethods.launch(entity, 1.0D, 2.5D, 1.0D);
		}
		damageCollidingEntities();

	}


}
