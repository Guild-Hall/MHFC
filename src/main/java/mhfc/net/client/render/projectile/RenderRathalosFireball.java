package mhfc.net.client.render.projectile;

import mhfc.net.client.model.projectile.ModelRathalosFireball;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRathalosFireball extends Render {
	private ModelRathalosFireball model;
	public RenderRathalosFireball() {
		model = new ModelRathalosFireball();
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z,
			float yaw, float partialTick) {
		renderEntityModel(entity, x, y, z, yaw, partialTick);
	}

	public void renderEntityModel(Entity entity, double x, double y, double z,
			float yaw, float partialTick) {
		GL11.glPushMatrix();
		float scale = entity.ticksExisted % 13 > 10 ? 1.65F : 1.25F;
		bindTexture(getEntityTexture(entity));
		GL11.glTranslated(x, y, z);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glRotatef(150, 1F, 1F, 300F);
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(0.0F, -1.0F, 0.0F);
		model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0875F);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation(MHFCReference.entity_rathalosfireball_tex);
	}

}
