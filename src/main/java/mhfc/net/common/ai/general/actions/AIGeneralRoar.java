package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;

public class AIGeneralRoar <EntityT extends EntityMHFCBase<? super EntityT>>
extends
ActionAdapter<EntityT> {
	
	public static interface IRoarProvider<EntityT extends EntityMHFCBase<? super EntityT>>
	extends
		IAnimationProvider,
		IDamageProvider{
	}
	
	public static class RoarAdapter<EntityT extends EntityMHFCBase<? super EntityT>> 
	implements 
	IRoarProvider<EntityT>{
		private IAnimationProvider animationProvider;
		private IDamageProvider damageProvider;
		
		public RoarAdapter(IAnimationProvider ANIMATION, IDamageProvider DAMAGE){
			animationProvider = ANIMATION;
			damageProvider = DAMAGE;
		}

		@Override
		public String getAnimationLocation() {
			return animationProvider.getAnimationLocation();
		}

		@Override
		public int getAnimationLength() {
			return animationProvider.getAnimationLength();
		}

		@Override
		public IDamageCalculator getDamageCalculator() {
			return damageProvider.getDamageCalculator();
		}
	}
	{
}
	@Override
	protected void update() {
		
	}
	@Override
	public float getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
