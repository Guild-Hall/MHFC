package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.DamageAreaAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class TailWhip extends DamageAreaAction<EntityNargacuga> implements IHasAttackProvider {

	protected float range, height, knockback, arc;

	public TailWhip(float range, float height, float knockback, float arc) {
		this.range = range;
		this.height = height;
		this.knockback = knockback;
		this.arc = arc;
	}

	@Override
	protected float computeSelectionWeight() {
		EntityNargacuga e = this.getEntity();
		target = e.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3d toTarget = WorldHelper.getVectorToTarget(e, target);
		double dist = toTarget.lengthVector();
		if (dist > 15) {
			return DONT_SELECT;
		}
		return 6F;
	}



	@Override
	public void onUpdate() {
		EntityNargacuga entity = getEntity();

		if (this.getCurrentFrame() == 12) {
			entity.playSound(MHFCSoundRegistry.getRegistry().nargacugaTailWhip, 2.0F, 1.0F);
		}
		if (this.getCurrentFrame() == 23) {
			super.onUpdate();
		}
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/Nargacuga/TailSwipeRight.mcanm", 56);
	}

	@Override
	public IDamageCalculator provideDamageCalculator() {
		return AIUtils.defaultDamageCalc(100, 500, 3333333);
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return new AttackAdapter(getAnimProvider(), new DamageAdapter(provideDamageCalculator()));
	}

	@Override
	protected float getRange() {
		return this.range;
	}

	@Override
	protected float getKnockBack() {
		// TODO Auto-generated method stub
		return this.knockback;
	}

	@Override
	protected float getArc() {
		// TODO Auto-generated method stub
		return this.arc;
	}

	@Override
	protected float getHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}

}
