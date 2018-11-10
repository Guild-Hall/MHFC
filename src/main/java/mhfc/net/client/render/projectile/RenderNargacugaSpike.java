package mhfc.net.client.render.projectile;

import mhfc.net.common.entity.projectile.ProjectileNargaSpike;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderNargacugaSpike extends Render<ProjectileNargaSpike> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(ResourceInterface.projectile_nargacugaspike_tex);

	public RenderNargacugaSpike(RenderManager manager) {
		super(manager);
	}

	@Override
	public void doRender(ProjectileNargaSpike entity, double posX, double posY, double posZ, float yaw, float pitch) {
		@SuppressWarnings("unused")
		ProjectileNargaSpike nargaSpike = entity;
		// FIXME implement rendering of Nargacuga spike
	}

	@Override
	protected ResourceLocation getEntityTexture(ProjectileNargaSpike entity) {
		return TEXTURE;
	}

}
