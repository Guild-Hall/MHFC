package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;

public class AIGeneralTailWhip<EntityT extends EntityMHFCBase<? super EntityT>> extends AIGeneralAttack<EntityT> {

	public static interface ISpinProvider<EntityT extends EntityMHFCBase<? super EntityT>>
			extends
			IAttackProvider<EntityT> {

	}

	public static class TailWhipAdapter<EntityT extends EntityMHFCBase<? super EntityT>>
			extends
			AIGeneralAttack.AttackAdapter<EntityT> implements ISpinProvider<EntityT> {

		public TailWhipAdapter(
				IAnimationProvider ANIMPROVIDER,
				IWeightProvider<EntityT> WEIGHTPROVIDER,
				ISelectionPredicate<EntityT> PREDICATE,
				IDamageProvider DAMAGEPROVIDER) {
			super(ANIMPROVIDER, WEIGHTPROVIDER, PREDICATE, DAMAGEPROVIDER);
		}
	}

	protected ISpinProvider<EntityT> provider;

	public AIGeneralTailWhip(ISpinProvider<EntityT> PROVIDER) {
		super(PROVIDER);
		this.provider = PROVIDER;
		dmgHelper.setDamageCalculator(provider.getDamageCalculator());
	}

}
