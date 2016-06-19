package mhfc.net.common.potion;

import mhfc.net.common.entity.particle.EntityPaintParticleEmitter;
import mhfc.net.common.entity.type.EntityParticleEmitter.DurationType;
import mhfc.net.common.item.ItemColor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionPainted extends Potion {

	private boolean isClearedInWater;

	/**
	 * Paintball effect. Amplifier encodes paint color, rather
	 * than the strength of the effect.
	 * @param potionID
	 * @param isBadEffect
	 * @param liquidColor
	 */
	public PotionPainted(int potionID, boolean isBadEffect, int liquidColor, boolean isClearedInWater) {
		super(potionID, isBadEffect, liquidColor);
		this.isClearedInWater = isClearedInWater;
	}

	/**
	 * Effect spawns a particle emitter 4 times a second.
	 */
	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 5 == 0;
	}

	/**
	 * Spawns a random paint emitter.
	 * Also clears the effect if the entity is in water.
	 */
	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if(isClearedInWater && entity.isInWater()) {
			entity.addPotionEffect(new PotionEffect(this.id, 1, 127, false));
		}

		if(amplifier >= 0 && amplifier < 16) {
			double rand = entity.worldObj.rand.nextDouble();
			DurationType duration;
			if(rand < 0.01) 	 duration = DurationType.VERY_LONG;
			else if (rand < 0.1) duration = DurationType.LONG;
			else if (rand < 0.4) duration = DurationType.MEDIUM;
			else 				 duration = DurationType.SHORT;

			EntityPaintParticleEmitter emitter =
					new EntityPaintParticleEmitter(
							entity.worldObj,
							duration,
							ItemColor.byMetadata(amplifier),
							entity);

			entity.worldObj.spawnEntityInWorld(emitter);
		}
	}
}
