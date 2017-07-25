package mhfc.net.client;

import mhfc.net.ProxyBase;
import mhfc.net.client.core.MHFCClientRegistry;
import mhfc.net.client.particle.EnumParticleType;
import mhfc.net.client.particle.paint.ParticleEmitter;
import mhfc.net.client.particle.paint.ParticlePaintEmitter;
import mhfc.net.client.particle.particles.ParticleAnimated;
import mhfc.net.client.render.RenderHelper.RenderType;
import mhfc.net.common.core.MHFCCommonRegistry;
import net.minecraft.client.Minecraft;

public class MHFCClient extends ProxyBase {
	@Override
	public void staticInit() {
		MHFCCommonRegistry.staticInit();
		MHFCClientRegistry.staticInit();
	}

	@Override
	public void spawnParticle(EnumParticleType types, double x, double y, double z, Object... info) {
		Minecraft mc = Minecraft.getMinecraft();
		ParticleAnimated fx = null;
		switch (types) {
		case LASERBEAM:
			fx = new ParticleAnimated(
					mc.world,
					x,
					y,
					z,
					0.25F,
					"deviljho_laserbeam2.png",
					4,
					4,
					16,
					RenderType.ALPHA);
			fx.positionX = x;
			fx.positiony = y;
			fx.positionz = z;
			fx.setMaxAge(20);
			fx.setRandomRotation(true);
			fx.setRandomness(0.75F);
			fx.setMotionDamping(0.98F);
			break;
		default:
			return;
		}
		if (fx != null) {
			mc.effectRenderer.addEffect(fx);
		}
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
