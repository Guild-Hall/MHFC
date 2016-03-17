package mhfc.net.common.ai.general.actions;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.provider.composite.IRoarProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public abstract class AIGeneralRoar<EntityT extends EntityMHFCBase<? super EntityT>> extends AIAnimatedAction<EntityT>
		implements
		IRoarProvider<EntityT> {

	private Collection<EntityLivingBase> affectedEntities;

	public AIGeneralRoar() {
		affectedEntities = new HashSet<EntityLivingBase>();
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		affectedEntities.clear();
		getEntity().playSound(getRoarSoundLocation(), 1.0F, 1.0F);
	}

	@Override
	protected void update() {
		EntityT roaringEntity = getEntity();
		@SuppressWarnings("unchecked")
		List<Entity> list = roaringEntity.worldObj.getEntitiesWithinAABBExcludingEntity(
				roaringEntity,
				roaringEntity.boundingBox.expand(4.0D, 3.0D, 4.0D));

		for (Entity affectedEntity : list) {
			if (!affectedEntities.contains(affectedEntity) && affectedEntity instanceof EntityLivingBase) {
				EntityLivingBase entityLiving = (EntityLivingBase) affectedEntity;
				if (shouldStun(entityLiving)) {
					AIUtils.stun(entityLiving);
				}
				affectedEntities.add(entityLiving);
			}
		}
	}
}
