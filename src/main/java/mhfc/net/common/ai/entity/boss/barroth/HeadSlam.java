package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.entity.EntityAIMethods;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.DamageAreaAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityBarroth;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class HeadSlam extends DamageAreaAction<EntityBarroth> implements IHasAttackProvider {

	protected float range, height, knockback, arc;

	public HeadSlam(float range, float height, float knockback, float arc) {
		this.range = range;
		this.height = height;
		this.knockback = knockback;
		this.arc = arc;

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
		if (dist > 8F) {
			return DONT_SELECT;
		}
		return 5F;
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return new AttackAdapter(
				new AnimationAdapter(this, "mhfc:models/barroth/headslam.mcanm", 60),
				new DamageAdapter(AIUtils.defaultDamageCalc(150F, 125F, 99999999F)));
	}

	@Override
	public void onUpdate() {
		EntityBarroth entity = getEntity();
		if (getEntity().getAttackTarget() != null && this.getCurrentFrame() == 25) {
			super.onUpdate();
			getEntity().playSound(MHFCSoundRegistry.getRegistry().barrothHeadsmash, 2.0F, 1.0F);
			EntityAIMethods.launch(entity, 1.0D, 2.5D, 1.0D);
		}

	}


	@Override
	protected float getRange() {
		return this.range;
	}

	@Override
	protected float getKnockBack() {
		return this.knockback;
	}

	@Override
	protected float getArc() {
		return this.arc;
	}

	@Override
	protected float getHeight() {
		return this.height;
	}

}
