package mhfc.net.client.render.projectile;

import mhfc.net.common.entity.projectile.NargacugaSpike;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderNargacugaSpike extends Render {

	private static final ResourceLocation TEXTURE = new ResourceLocation(MHFCReference.projectile_nargacugaspike_tex);

	@Override
	public void doRender(Entity entity, double positionX, double positionY, double positionZ, float yaw, float pitch) {
		@SuppressWarnings("unused")
		NargacugaSpike nargaSpike = (NargacugaSpike) entity;
		// FIXME implement rendering of Nargacuga spike
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return TEXTURE;
	}

}
