package mhfc.net.common.ai.entity.boss.greatjaggi;

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
import mhfc.net.common.entity.monster.EntityGreatJaggi;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class Bite extends DamagingAction<EntityGreatJaggi> implements IHasAttackProvider {

	private static final String ANIMATION_LOCATION = "mhfc:models/GreatJaggi/GreatJaggiBite.mcanm";
	private static final int ANIM_FRAME = 33;

	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(95F, 125F, 99999999F);

	private static final double TARGET_DISTANCE = 4.2F;
	private static final double AIM_ANGLE = 0.155;

	private static final float WEIGHT = 15;

	private final IAttackProvider ATTACK;
	{
		IDamageProvider DAMAGE = new DamageAdapter(DAMAGE_CALC);
		IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, ANIM_FRAME);
		ATTACK = new AttackAdapter(ANIMATION, DAMAGE);
	}

	public Bite() {}

	@Override
	protected float computeSelectionWeight() {
		EntityGreatJaggi entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3d LOOK_TARGET = WorldHelper.getVectorToTarget(entity, target);
		double distance = LOOK_TARGET.lengthVector();
		if (distance > TARGET_DISTANCE) {
			return DONT_SELECT;
		}
		if (LOOK_TARGET.normalize().dotProduct(entity.getLookVec()) < AIM_ANGLE) {
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
		if (this.getCurrentFrame() == 23) {
			getEntity().playSound(MHFCSoundRegistry.getRegistry().greatJaggiBite, 1.0F, 1.0F);
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			EntityGreatJaggi entity = getEntity();
			entity.moveForward(0.2, false);
		}
		damageCollidingEntities();
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 10 && frame < 25);
	}

}
