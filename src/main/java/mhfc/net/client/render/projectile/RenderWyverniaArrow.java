package mhfc.net.client.render.projectile;

import static org.lwjgl.opengl.GL11.GL_QUADS;

import org.lwjgl.opengl.GL11;

import mhfc.net.common.entity.projectile.EntityWyverniaArrow;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWyverniaArrow extends Render<EntityWyverniaArrow> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(ResourceInterface.projectile_wyverniaarrow_tex);

	public RenderWyverniaArrow(RenderManager manager) {
		super(manager);
	}

	@Override
	public void doRender(EntityWyverniaArrow entity, double posX, double posY, double posZ, float yaw, float subTick) {
		this.bindEntityTexture(entity);
		float yawAngle = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * subTick - 90.0F;
		float pitchAngle = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * subTick;
		float scale = 0.05625F;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer buffer = tessellator.getBuffer();

		GlStateManager.pushMatrix();
		GlStateManager.translate(posX, posY, posZ);
		GlStateManager.rotate(yawAngle, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(pitchAngle, 0.0F, 0.0F, 1.0F);
		float shakeIntensity = entity.arrowShake - subTick;
		if (shakeIntensity > 0.0F) {
			float f12 = -MathHelper.sin(shakeIntensity * 3.0F) * shakeIntensity;
			GlStateManager.rotate(f12, 0.0F, 0.0F, 1.0F);
		}

		float f6 = 0 / 32.0F;
		float f7 = 5 / 32.0F;
		float f8 = 5 / 32.0F;
		float f9 = 10 / 32.0F;

		GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.translate(-4.0F, 0.0F, 0.0F);
		buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		buffer.pos(-7.0D, -2.0D, -2.0D).tex(f6, f8).endVertex();
		buffer.pos(-7.0D, -2.0D, 2.0D).tex(f7, f8).endVertex();
		buffer.pos(-7.0D, 2.0D, 2.0D).tex(f7, f9).endVertex();
		buffer.pos(-7.0D, 2.0D, -2.0D).tex(f6, f9).endVertex();
		buffer.putNormal(1f, 0, 0);
		tessellator.draw();

		buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		buffer.pos(-7.0D, 2.0D, -2.0D).tex(f6, f8).endVertex();
		buffer.pos(-7.0D, 2.0D, 2.0D).tex(f7, f8).endVertex();
		buffer.pos(-7.0D, -2.0D, 2.0D).tex(f7, f9).endVertex();
		buffer.pos(-7.0D, -2.0D, -2.0D).tex(f6, f9).endVertex();
		buffer.putNormal(-1f, 0, 0);
		tessellator.draw();

		float f2 = 0 / 32.0F;
		float f3 = 16 / 32.0F;
		float f4 = 0 / 32.0F;
		float f5 = 5 / 32.0F;
		for (int i = 0; i < 4; ++i) {
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
			buffer.pos(-8.0D, -2.0D, 0.0D).tex(f2, f4).endVertex();
			buffer.pos(8.0D, -2.0D, 0.0D).tex(f3, f4).endVertex();
			buffer.pos(8.0D, 2.0D, 0.0D).tex(f3, f5).endVertex();
			buffer.pos(-8.0D, 2.0D, 0.0D).tex(f2, f5).endVertex();
			buffer.putNormal(0, 0, 1f);
			tessellator.draw();
		}

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityWyverniaArrow entity) {
		return TEXTURE;
	}

}
