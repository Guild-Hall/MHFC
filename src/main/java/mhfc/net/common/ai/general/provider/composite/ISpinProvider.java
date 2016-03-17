package mhfc.net.common.ai.general.provider.composite;

import mhfc.net.common.ai.general.provider.simple.IAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageProvider;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;

public interface ISpinProvider<EntityT extends EntityMHFCBase<? super EntityT>> extends IAttackProvider<EntityT> {

	class TailWhipAdapter<EntityT extends EntityMHFCBase<? super EntityT>>
			extends
			IAttackProvider.AttackAdapter<EntityT> implements ISpinProvider<EntityT> {

		public TailWhipAdapter(
				IAnimationProvider ANIMPROVIDER,
				IWeightProvider<EntityT> WEIGHTPROVIDER,
				ISelectionPredicate<EntityT> PREDICATE,
				IDamageProvider DAMAGEPROVIDER) {
			super(ANIMPROVIDER, WEIGHTPROVIDER, PREDICATE, DAMAGEPROVIDER);
		}
	}

}
