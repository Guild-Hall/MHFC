package mhfc.net.client;

import mhfc.net.ProxyBase;
import mhfc.net.client.core.MHFCClientRegistry;
import mhfc.net.common.core.MHFCCommonRegistry;
import mhfc.net.common.entity.particle.EntityPaintFX;
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

	protected void spawnPaintParticle(EntityParticleEmitter emitter) {
		if (!(emitter instanceof EntityPaintParticleEmitter) || emitter == null) {
			return;
		}
		EntityPaintParticleEmitter paintEmitter = (EntityPaintParticleEmitter) emitter;
		EntityPaintFX particle = new EntityPaintFX(
				paintEmitter.worldObj,
				paintEmitter.color,
				paintEmitter.posX,
				paintEmitter.posY,
				paintEmitter.posZ);

		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}
}
