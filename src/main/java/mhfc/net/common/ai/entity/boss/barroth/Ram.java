package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.DamagingAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageProvider;
import mhfc.net.common.entity.monster.EntityBarroth;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class Ram extends DamagingAction<EntityBarroth> implements IHasAttackProvider {
	private static final int LAST_FRAME = 75;
	private static final String ANIMATION_LOCATION = "mhfc:models/Barroth/BarrothRam.mcanm";
	private final IAttackProvider ATTACK;
	{
		IAnimationProvider animation = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);
		IDamageProvider DAMAGE_CALC = new DamageAdapter(AIUtils.defaultDamageCalc(95f, 50F, 9999999f));
		ATTACK = new AttackAdapter(animation, DAMAGE_CALC);
	}

	private static final double MAX_DIST = 5.5f;
	private static final double MAX_ANGLE = 0.155; // This is cos(30)
	private static final float WEIGHT = 15;

	public Ram() {}

	@Override
	protected float computeSelectionWeight() {
		EntityBarroth entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3d toTarget = WorldHelper.getVectorToTarget(entity, target);
		double dist = toTarget.lengthVector();
		if (dist > MAX_DIST) {
			return DONT_SELECT;
		}
		if (toTarget.normalize().dotProduct(entity.getLookVec()) < MAX_ANGLE) {
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

		if (entity.getAttackTarget() != null && this.getCurrentFrame() == 20) {
			entity.getAttackTarget().addVelocity(-0.8, 1.8, 0);
			//	getEntity().playSound("mhfc:entity.bite", 2.0F, 1.0F);
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			entity.moveForward(1, false);
		}
		damageCollidingEntities();
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 30);
	}
}
