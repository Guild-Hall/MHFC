package mhfc.net.client.render.projectile;

import mhfc.net.common.entity.projectile.NargacugaSpike;
import mhfc.net.common.util.Libraries;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderNargacugaSpike extends Render<NargacugaSpike> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Libraries.projectile_nargacugaspike_tex);

	public RenderNargacugaSpike(RenderManager manager) {
		super(manager);
	}

	@Override
	public void doRender(NargacugaSpike entity, double posX, double posY, double posZ, float yaw, float pitch) {
		@SuppressWarnings("unused")
		NargacugaSpike nargaSpike = entity;
		// FIXME implement rendering of Nargacuga spike
	}

	@Override
	protected ResourceLocation getEntityTexture(NargacugaSpike entity) {
		return TEXTURE;
	}

}
