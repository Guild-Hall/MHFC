package mhfc.net.client.render.projectile;

import mhfc.net.common.entity.projectile.ProjectileFlamethrower;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFlameThrowerProjectile extends RenderTexProjectile<ProjectileFlamethrower>{

	public RenderFlameThrowerProjectile(RenderManager renderManager) {
		super(renderManager);
		textureLoc = new ResourceLocation(ResourceInterface.main_modid,"textures/fx/fireball.png");
    	scale=1.0f;
    	baseSize=0.15f;
	}
	
	@Override
	public void doRender(ProjectileFlamethrower entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
	}
}
