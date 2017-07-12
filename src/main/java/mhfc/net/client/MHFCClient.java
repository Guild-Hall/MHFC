package mhfc.net.client;

import mhfc.net.ProxyBase;
import mhfc.net.client.core.MHFCClientRegistry;
import mhfc.net.common.core.MHFCCommonRegistry;
import mhfc.net.common.entity.particle.EntityPaintParticleEmitter;
import mhfc.net.common.entity.particle.EnumParticleType;
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
		// TODO 1.12: see if there's a forge way to register particles with the effect renderer
		// Minecraft.getMinecraft().effectRenderer.registerParticle(id, particleFactory);
		EntityPaintParticleEmitter paintEmitter = (EntityPaintParticleEmitter) emitter;

		Minecraft.getMinecraft().effectRenderer.addEffect(paintEmitter.createPaintParticle());
	}
}
