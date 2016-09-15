package mhfc.net.client.render.projectile;

import static org.lwjgl.opengl.GL11.GL_QUADS;

import mhfc.net.common.entity.projectile.EntityBreathe;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBreathe extends Render<EntityBreathe> {
	private static final ResourceLocation TEXTURES = new ResourceLocation(MHFCReference.projectile_breathe_tex);

	public RenderBreathe(RenderManager manager) {
		super(manager);
	}

	@Override
	public void doRender(EntityBreathe projectile, double x, double y, double z, float par8, float par9) {
		GlStateManager.enableRescaleNormal();

		float minU = 0;
		float maxU = 1;
		float minV = 0;
		float maxV = 1;
		float f7 = 1.0F;
		float f8 = 0.5F;
		float f9 = 0.25F;

		Tessellator tessellator = Tessellator.getInstance();
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.scale(0.5F, 0.5F, 0.5F);
		GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

		this.bindEntityTexture(projectile);
		VertexBuffer buffer = tessellator.getBuffer();
		buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		buffer.pos(0.0F - f8, 0.0F - f9, 0.0D).tex(minU, maxV).endVertex();
		buffer.pos(f7 - f8, 0.0F - f9, 0.0D).tex(maxU, maxV).endVertex();
		buffer.pos(f7 - f8, 1.0F - f9, 0.0D).tex(maxU, minV).endVertex();
		buffer.pos(0.0F - f8, 1.0F - f9, 0.0D).tex(minU, minV).endVertex();
		buffer.putNormal(0.0F, 1.0F, 0.0F);
		tessellator.draw();

		GlStateManager.popMatrix();

		GlStateManager.disableRescaleNormal();
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityBreathe p_110775_1_) {
		return TEXTURES;
	}
}
