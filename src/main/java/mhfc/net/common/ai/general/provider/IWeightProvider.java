package mhfc.net.common.ai.general.provider;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public interface IWeightProvider<EntityT extends EntityLiving> {

	public float getWeight(EntityT entity, Entity target);

	public void onSelected();

	public static class SimpleWeightAdapter<EntityT extends EntityLiving>
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

		@Override
		public void onSelected() {
		}
	}

	public static class RandomWeightAdapter<EntityT extends EntityLiving>
		implements
			IWeightProvider<EntityT> {

		private static final Random rng = new Random(System.nanoTime());
		private float max;

		public RandomWeightAdapter(float maximum) {
			this.max = maximum;
		}

		@Override
		public float getWeight(EntityT entity, Entity target) {
			return rng.nextFloat() * max;
		}

		@Override
		public void onSelected() {
		}
	}

	public static class CooldownAdapter<EntityT extends EntityLiving>
		implements
			IWeightProvider<EntityT> {

		private int cooldown;
		private int cooldownRemaining;
		IWeightProvider<EntityT> originalWeight;

		public CooldownAdapter(int cooldown,
			IWeightProvider<EntityT> originalWeight) {
			this.cooldown = cooldown;
			this.cooldownRemaining = 0;
			this.originalWeight = originalWeight;
		}

		@Override
		public float getWeight(EntityT entity, Entity target) {
			if (cooldownRemaining > 0) {
				cooldownRemaining--;
				return 0;
			} else {
				return originalWeight.getWeight(entity, target);
			}
		}

		@Override
		public void onSelected() {
			cooldownRemaining = cooldown;
		}
	}

}
