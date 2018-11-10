package mhfc.net.client.render.projectile;

import mhfc.net.common.entity.projectile.ProjectileArrow;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWyverniaArrow extends RenderArrow<ProjectileArrow> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(ResourceInterface.projectile_wyverniaarrow_tex);

	public RenderWyverniaArrow(RenderManager manager) {
		super(manager);
	}

	@Override
	protected ResourceLocation getEntityTexture(ProjectileArrow entity) {
		return TEXTURE;
	}

}
