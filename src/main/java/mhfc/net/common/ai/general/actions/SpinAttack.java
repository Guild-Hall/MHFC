package mhfc.net.common.ai.general.actions;

import net.minecraft.entity.Entity;
import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.JumpAttack.IJumpProvider;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;

public class SpinAttack <EntityT extends EntityMHFCBase<? super EntityT>>
extends
ActionAdapter<EntityT> {

public static interface IJumpProvider<EntityT extends EntityMHFCBase<? super EntityT>>
extends
	IAnimationProvider,
	ISelectionPredicate<EntityT>,
	IWeightProvider<EntityT>,
	IDamageProvider {
	public static class JumpAdapter<EntityT extends EntityMHFCBase<? super EntityT>>
	implements
		IJumpProvider<EntityT> {

		@Override
		public String getAnimationLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getAnimationLength() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean shouldSelectAttack(IExecutableAction<EntityT> attack,
				EntityT actor, Entity target) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public float getWeight(EntityT entity, Entity target) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public IDamageCalculator getDamageCalculator() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}

@Override
protected void update() {
	// TODO Auto-generated method stub
	
}

@Override
public float getWeight() {
	// TODO Auto-generated method stub
	return 0;
}

}
