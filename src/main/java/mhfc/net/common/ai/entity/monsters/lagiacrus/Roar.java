package mhfc.net.common.ai.entity.monsters.lagiacrus;

import mhfc.net.common.ai.general.actions.RoarAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.RoarAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IRoarProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.wip.EntityLagiacrus;

public class Roar extends RoarAction<EntityLagiacrus> implements IHasAnimationProvider {

	private static final int LAST_FRAME = 95;
	private static final String ANIMATION_LOCATION = "mhfc:models/Lagiacrus/LagiacrusRoar.mcanm";

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);

	public Roar() {}


	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	public IRoarProvider provideRoarBehaviour() {
		return new RoarAdapter(MHFCSoundRegistry.getRegistry().lagiacrusRoar, true);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		EntityLagiacrus entity = this.getEntity();
		target = entity.getAttackTarget();
		if (this.getCurrentFrame() >= 18 && this.getCurrentFrame() <= 22) {
			entity.getTurnHelper().updateTargetPoint(target);
			entity.getTurnHelper().updateTurnSpeed(30.0f);
		}
	}

}
