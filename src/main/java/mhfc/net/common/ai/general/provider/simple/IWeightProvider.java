package mhfc.net.common.ai.general.provider.simple;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public interface IWeightProvider<EntityT extends EntityLiving> {

	public float getWeight(EntityT entity, Entity target);

	public static class SimpleWeightAdapter<EntityT extends EntityLiving> implements IWeightProvider<EntityT> {
		private float weight;

		public SimpleWeightAdapter(float weight) {
			this.weight = weight;
		}

		@Override
		public float getWeight(EntityT entity, Entity target) {
			return weight;
		}

	}

	public static class RandomWeightAdapter<EntityT extends EntityLiving> implements IWeightProvider<EntityT> {

		private static final Random rng = new Random(System.nanoTime());
		private float max;

		public RandomWeightAdapter(float maximum) {
			this.max = maximum;
		}

		@Override
		public float getWeight(EntityT entity, Entity target) {
			return rng.nextFloat() * max;
		}
	}
}
