package mhfc.net.client.render.projectile;

import mhfc.net.common.entity.projectile.EntityProjectileBlock;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBlockProjectile extends Render<EntityProjectileBlock> {

	public RenderBlockProjectile(RenderManager manager) {
		super(manager);
		shadowSize = 0.5f; // <-- set the size of its shadow.
	}

	@Override
	public void doRender(EntityProjectileBlock entity, double d, double d1, double d2, float f, float f1) {
		EntityFallingBlock fallingBlock = entity.getProxy();

		GlStateManager.pushMatrix();

		GlStateManager.translate((float) d, (float) d1, (float) d2);
		GlStateManager.rotate(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate((entity.ticksExisted + f1) * 20.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate((entity.ticksExisted + f1) * 12.0F, 0.0F, 0.0F, -1.0F);

		renderManager.getEntityRenderObject(fallingBlock).doRender(fallingBlock, d, d1, d2, f, f1);

		GlStateManager.popMatrix();

	}

	@Override
	protected ResourceLocation getEntityTexture(EntityProjectileBlock entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}
