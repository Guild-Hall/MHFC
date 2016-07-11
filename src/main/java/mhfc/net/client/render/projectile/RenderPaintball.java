package mhfc.net.client.render.projectile;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.Color;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.entity.projectile.EntityPaintball;
import mhfc.net.common.item.ItemColor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class RenderPaintball extends RenderSnowball {

	private Item item;

	public RenderPaintball() {
		super(MHFCItemRegistry.getRegistry().paintball);
		this.item = MHFCItemRegistry.getRegistry().paintball;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick) {
		IIcon iicon = this.item.getIconFromDamage(0);

		if (entity instanceof EntityPaintball && iicon != null) {
			EntityPaintball entityPaintball = (EntityPaintball) entity;
			GL11.glPushMatrix();
			GL11.glTranslatef((float) x, (float) y, (float) z);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			this.bindEntityTexture(entity);
			Tessellator tessellator = Tessellator.instance;

			ItemColor itemColor = entityPaintball.getColor();
			Color color = new Color(itemColor.getColor());
			GL11.glColor3f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);

			this.renderToTesselator(tessellator, iicon);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		} else {
			MHFCMain.logger().log(Level.INFO, "Unable to render Paintball.");
		}
	}

	private void renderToTesselator(Tessellator tess, IIcon iicon) {
		float f = iicon.getMinU();
		float f1 = iicon.getMaxU();
		float f2 = iicon.getMinV();
		float f3 = iicon.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.addVertexWithUV(0.0F - f5, 0.0F - f6, 0.0D, f, f3);
		tess.addVertexWithUV(f4 - f5, 0.0F - f6, 0.0D, f1, f3);
		tess.addVertexWithUV(f4 - f5, f4 - f6, 0.0D, f1, f2);
		tess.addVertexWithUV(0.0F - f5, f4 - f6, 0.0D, f, f2);
		tess.draw();
	}
}
