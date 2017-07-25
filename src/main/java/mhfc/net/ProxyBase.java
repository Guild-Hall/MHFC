package mhfc.net;

import mhfc.net.client.particle.EnumParticleType;
import mhfc.net.client.particle.paint.ParticleEmitter;

public abstract class ProxyBase {
	public abstract void staticInit();

	/**
	 * Dummy method to ensure that particle spawn calls are not passed to the server.
	 *
	 * @param type
	 * @param emitter
	 */
	public void spawnPaintBallParticle(EnumParticleType type, ParticleEmitter emitter) {}

	public void spawnParticle(EnumParticleType types, double x, double y, double z, Object... info) {}
}
