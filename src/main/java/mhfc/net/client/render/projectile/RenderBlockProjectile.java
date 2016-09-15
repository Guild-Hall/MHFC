package mhfc.net.client.render.projectile;

import org.lwjgl.opengl.GL11;

import mhfc.net.common.entity.projectile.EntityProjectileBlock;
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

		GL11.glPushMatrix();

		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glRotatef(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef((entity.ticksExisted + f1) * 20.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef((entity.ticksExisted + f1) * 12.0F, 0.0F, 0.0F, -1.0F);

		renderManager.getEntityRenderObject(fallingBlock).doRender(fallingBlock, d, d1, d2, f, f1);

		GL11.glPopMatrix();

	}

	@Override
	protected ResourceLocation getEntityTexture(EntityProjectileBlock entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}
