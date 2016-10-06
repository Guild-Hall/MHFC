package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.general.WeightUtils;
import mhfc.net.common.ai.general.actions.RoarAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.RoarAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IRoarProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityBarroth;

public class Roar extends RoarAction<EntityBarroth> implements IHasAnimationProvider {

	private static final String ANIMATION_LOCATION = "mhfc:models/Barroth/BarrothRoar.mcanm";
	private static final int LAST_FRAME = 105;

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);

	public Roar() {}

	@Override
	public void onUpdate() {
		super.onUpdate();
		EntityBarroth entity = this.getEntity();
		target = entity.getAttackTarget();
		if (this.getCurrentFrame() >= 18 && this.getCurrentFrame() <= 22) {
			entity.getTurnHelper().updateTargetPoint(target);
			entity.getTurnHelper().updateTurnSpeed(30.0f);
		}
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	public IRoarProvider provideRoarBehaviour() {
		return new RoarAdapter(MHFCSoundRegistry.getRegistry().barrothRoar, true);
	}

	@Override
	protected float computeSelectionWeight() {
		return WeightUtils.random(rng(), 1F);
	}
}
