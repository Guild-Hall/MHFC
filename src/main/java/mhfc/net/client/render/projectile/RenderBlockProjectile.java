package mhfc.net.client.render.projectile;

import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBlockProjectile extends Render {

	public RenderBlockProjectile() {
		shadowSize = 0.5f; // <-- set the size of its shadow.
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2,
			float f, float f1) {

		// EntityTigrexBlock block = (EntityTigrexBlock)entity;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glRotatef(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef((entity.ticksExisted + f1) * 20.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef((entity.ticksExisted + f1) * 12.0F, 0.0F, 0.0F, -1.0F);
		bindEntityTexture(entity);
		GL11.glDisable(GL_LIGHTING);
		field_147909_c.setRenderBoundsFromBlock(Blocks.dirt);
		int x = MathHelper.floor_double(entity.posX);
		int y = MathHelper.floor_double(entity.posY);
		int z = MathHelper.floor_double(entity.posZ);
		field_147909_c.renderBlockSandFalling(Blocks.dirt, entity.worldObj, x,
				y, z, 0);
		GL11.glEnable(GL_LIGHTING);
		GL11.glPopMatrix();

	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationBlocksTexture;
	}

}
