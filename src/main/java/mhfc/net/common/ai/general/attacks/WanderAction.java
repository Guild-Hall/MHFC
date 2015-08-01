package mhfc.net.common.ai.general.attacks;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.provider.*;
import mhfc.net.common.entity.type.EntityMHFCBase;

public class WanderAction<EntityT extends EntityMHFCBase<? super EntityT>>
	extends
		ActionAdapter<EntityT> {

	public static interface IWanderProvider<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
			IAnimationProvider,
			ISelectionPredicate<EntityT>,
			IContinuationPredicate<EntityT>,
			IWeightProvider<EntityT>,
			IPathProvider<EntityT> {

	}

	@Override
	public float getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub

	}

}
