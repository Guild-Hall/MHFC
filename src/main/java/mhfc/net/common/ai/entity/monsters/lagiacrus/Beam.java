package mhfc.net.common.ai.entity.monsters.lagiacrus;

import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityLagiacrus;

public class Beam extends AnimatedAction<EntityLagiacrus> implements IHasAnimationProvider {

	private static final int FRAMES = 100;
	private static final String ANIMATION_LOCATION = "mhfc:models/Lagiacrus/LagiacrusBeam.mcanm";

	private static final double MAXDISTANCE = 10f;
	private static final float WEIGHT = 6F;

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, FRAMES);

	public Beam() {}

	private boolean shouldSelect() {
		return SelectionUtils.isInDistance(0, MAXDISTANCE, getEntity(), target);
	}

	@Override
	protected float computeSelectionWeight() {
		return shouldSelect() ? WEIGHT : DONT_SELECT;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	protected void beginExecution() {
		EntityLagiacrus actor = this.getEntity();
		actor.playSound(MHFCSoundRegistry.getRegistry().lagiacrusBeam, 2.0F, 1.0F);
	}

	@Override
	protected void onUpdate() {
		// TODO: spawn beam?
	}

}
