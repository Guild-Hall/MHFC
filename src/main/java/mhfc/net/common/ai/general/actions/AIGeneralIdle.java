package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;

public class AIGeneralIdle<EntityT extends EntityMHFCBase<? super EntityT>> extends AIAnimatedAction<EntityT> {

	public AIGeneralIdle(IAnimationProvider animation, IWeightProvider<EntityT> weight) {
		super(new AnimatedActionAdapter<>(animation, new ISelectionPredicate.SelectIdleAdapter<EntityT>(), weight));
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		getEntity().playLivingSound();
	}

	@Override
	protected void update() {} // do nothing, we idle, remember?

}
