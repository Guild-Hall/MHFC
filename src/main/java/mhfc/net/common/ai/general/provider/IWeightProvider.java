package mhfc.net.common.ai.general.provider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public interface IWeightProvider<EntityT extends EntityLiving> {

	public float getWeight(EntityT entity, Entity target);

	static class SimpleWeightAdapter<EntityT extends EntityLiving>
		implements
			IWeightProvider<EntityT> {
		private float weight;

		public SimpleWeightAdapter(float weight) {
			this.weight = weight;
		}

		@Override
		public float getWeight(EntityT entity, Entity target) {
			return weight;
		}
	}

}
