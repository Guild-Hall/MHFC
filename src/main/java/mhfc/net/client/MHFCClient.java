package mhfc.net.client;

import mhfc.net.ProxyBase;
import mhfc.net.client.core.MHFCClientRegistry;
import mhfc.net.client.particle.EnumParticleType;
import mhfc.net.client.particle.paint.ParticleEmitter;
import mhfc.net.client.particle.paint.ParticlePaintEmitter;
import mhfc.net.common.core.MHFCCommonRegistry;
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
	public void spawnPaintBallParticle(EnumParticleType type, ParticleEmitter emitter) {
		switch (type) {
		case PAINT:
			spawnPaintParticle(emitter);
			break;
		default:
			return;
		}
	}

	private static void spawnPaintParticle(ParticleEmitter emitter) {
		if (emitter == null || !(emitter instanceof ParticlePaintEmitter)) {
			return;
		}
		// TODO 1.12: see if there's a forge way to register particles with the effect renderer
		// Minecraft.getMinecraft().effectRenderer.registerParticle(id, particleFactory);
		ParticlePaintEmitter paintEmitter = (ParticlePaintEmitter) emitter;

		Minecraft.getMinecraft().effectRenderer.addEffect(paintEmitter.createPaintParticle());
	}
}
