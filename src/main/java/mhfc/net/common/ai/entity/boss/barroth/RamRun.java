package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.entity.AIMethods;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.DamagingAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityBarroth;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class RamRun extends DamagingAction<EntityBarroth> implements IHasAttackProvider {
	private static final String ANIMATION_LOCATION = "mhfc:models/Barroth/BarrothRamRun.mcanm";
	private static final int LAST_FRAME = 130;

	private static final double MAX_DIST = 40F;
	private static final float WEIGHT = 2;

	private final IAttackProvider ATTACK;
	{
		IAnimationProvider animationAdapter = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);
		IDamageProvider damageAdapter = new DamageAdapter(AIUtils.defaultDamageCalc(115f, 50F, 9999999f));
		ATTACK = new AttackAdapter(animationAdapter, damageAdapter);
	}

	public RamRun() {}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		EntityBarroth entity = this.getEntity();
		entity.getTurnHelper().updateTurnSpeed(14.17f);
	}

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
		return WEIGHT;
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return ATTACK;
	}

	@Override
	public void onUpdate() {
		EntityBarroth entity = getEntity();
		damageCollidingEntities();
		entity.getTurnHelper().updateTargetPoint(target);

		if (this.getCurrentFrame() == 20) {
			getEntity().playSound(MHFCSoundRegistry.getRegistry().barrothCharge, 3.0F, 1.0F);
			entity.getTurnHelper().updateTurnSpeed(0.37f);
			//ON run

		}
		if (this.getCurrentFrame() > 85) {
			AIMethods.launch(entity, 1.0D, 1.5D, 1.0D);
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			entity.moveForward(0.96, true);
		}
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 80);
	}
}
