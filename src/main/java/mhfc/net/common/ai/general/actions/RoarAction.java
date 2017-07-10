package mhfc.net.common.ai.general.actions;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import mhfc.net.common.ai.entity.EntityAIMethods;
import mhfc.net.common.ai.general.WeightUtils;
import mhfc.net.common.ai.general.provider.requirements.INeedsRoarBehaviour;
import mhfc.net.common.ai.general.provider.simple.IRoarProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public abstract class RoarAction<T extends EntityMHFCBase<? super T>> extends AnimatedAction<T>
		implements
		INeedsRoarBehaviour {

	private Collection<EntityLivingBase> affectedEntities;
	private IRoarProvider roarProvider;
	
	//TODO add damage soon as a parameter. @Heltrato
	//protected abstract boolean doesDealDamage();

	public RoarAction() {
		affectedEntities = new HashSet<>();
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		affectedEntities.clear();
		roarProvider = provideRoarBehaviour();
		getEntity().playSound(roarProvider.getRoarSoundLocation(), 3.0F, 1.0F);
	}
	
	@Override
	public float computeSelectionWeight(){
		if(getEntity().getAttackTarget() == null){
			return DONT_SELECT;
		}
		return WeightUtils.random(rng(), 5f);
		
	}
	

	@Override
	protected void onUpdate() {
		T roaringEntity = getEntity();
		List<Entity> list = roaringEntity.world.getEntitiesWithinAABBExcludingEntity(
				roaringEntity,
				roaringEntity.getEntityBoundingBox().expand(4.0D, 3.0D, 4.0D));

		for (Entity affectedEntity : list) {
			if (affectedEntities.contains(affectedEntity) || !(affectedEntity instanceof EntityLivingBase)) {
				continue;
			}
			EntityLivingBase entityLiving = (EntityLivingBase) affectedEntity;
			affectedEntities.add(entityLiving);
			if (!roarProvider.shouldAffect(entityLiving)) {
				continue;
			}
			EntityAIMethods.roarEffect(entityLiving);
			//if (doesDealDamage());
		}
	}
}
