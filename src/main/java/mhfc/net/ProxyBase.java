package mhfc.net;

import mhfc.net.common.entity.particle.EnumParticleType;
import mhfc.net.common.entity.type.EntityParticleEmitter;

public abstract class ProxyBase {
	public abstract void staticInit();

	/**
	 * Dummy method to ensure that particle spawn calls are not passed to the server.
	 *
	 * @param type
	 * @param emitter
	 */
	public void spawnParticle(EnumParticleType type, EntityParticleEmitter emitter) {}
}
