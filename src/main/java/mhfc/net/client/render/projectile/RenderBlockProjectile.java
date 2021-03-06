package mhfc.net.client.render.projectile;

import mhfc.net.common.entity.projectile.ProjectileBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBlockProjectile extends Render<ProjectileBlock> {

	public RenderBlockProjectile(RenderManager manager) {
		super(manager);
		shadowSize = 0.5f; // <-- set the size of its shadow.
	}

	@Override
	public void doRender(ProjectileBlock entity, double x, double y, double z, float f, float partialTicks) {
		EntityFallingBlock fallingBlock = entity.getProxy();
		BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
		GlStateManager.pushMatrix();

		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);

		// GlStateManager.rotate((entity.ticksExisted + f1) * 20.0F, 1.0F, 0.0F, 0.0F);
		// GlStateManager.rotate((entity.ticksExisted + f1) * 12.0F, 0.0F, 0.0F, -1.0F);

		blockrendererdispatcher.renderBlockBrightness(fallingBlock.getBlock(), 1F);
		renderManager.getEntityRenderObject(fallingBlock).doRender(fallingBlock, 0, 0, 0, f, partialTicks);

		GlStateManager.popMatrix();

	}

	@Override
	protected ResourceLocation getEntityTexture(ProjectileBlock entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}
