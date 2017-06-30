package mhfc.net.client;

import mhfc.net.ProxyBase;
import mhfc.net.client.core.MHFCClientRegistry;
import mhfc.net.common.core.MHFCCommonRegistry;
import mhfc.net.common.entity.particle.EntityPaintParticleEmitter;
import mhfc.net.common.entity.particle.EnumParticleType;
import mhfc.net.common.entity.particle.ParticlePaint;
import mhfc.net.common.entity.type.EntityParticleEmitter;
import net.minecraft.client.Minecraft;

public class MHFCClient extends ProxyBase {
	@Override
	public void staticInit() {
		MHFCCommonRegistry.staticInit();
		MHFCClientRegistry.staticInit();
	}

	/**
	 * Spawns a particle from its respective emitter.
	 */
	@Override
	public void spawnParticle(EnumParticleType type, EntityParticleEmitter emitter) {
		switch (type) {
		case PAINT:
			spawnPaintParticle(emitter);
			break;
		default:
			return;
		}
	}

	private static void spawnPaintParticle(EntityParticleEmitter emitter) {
		if (emitter == null || !(emitter instanceof EntityPaintParticleEmitter)) {
			return;
		}
		EntityPaintParticleEmitter paintEmitter = (EntityPaintParticleEmitter) emitter;
		ParticlePaint particle = new ParticlePaint(
				paintEmitter.world,
				paintEmitter.color,
				paintEmitter.posX,
				paintEmitter.posY,
				paintEmitter.posZ);

		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}
}
