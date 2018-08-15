package mhfc.net.client.render.projectile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import mhfc.net.client.model.projectile.ModelRathalosFireball;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRathalosFireball extends Render<EntityRathalosFireball> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(ResourceInterface.entity_rathalosfireball_tex);

	private ModelRathalosFireball model;

	public RenderRathalosFireball(RenderManager manager) {
		super(manager);
		model = new ModelRathalosFireball();
	}

	@Override
	public void doRender(EntityRathalosFireball entity, double x, double y, double z, float yaw, float partialTick) {
		// FIXME: use GLStateManager
		GL11.glPushMatrix();
		float scale = entity.ticksExisted % 13 > 10 ? 1.65F : 1.25F;
		this.bindEntityTexture(entity);
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
	protected ResourceLocation getEntityTexture(EntityRathalosFireball entity) {
		return TEXTURE;
	}

}
