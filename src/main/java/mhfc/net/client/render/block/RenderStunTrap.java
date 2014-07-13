package mhfc.net.client.render.block;

import mhfc.net.client.model.block.ModelStunTrap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderStunTrap extends TileEntitySpecialRenderer {

	private ModelStunTrap model;
	private static final ResourceLocation texture = new ResourceLocation(
			"mhfc:textures/tile/stuntrap.png");

	public RenderStunTrap() {
		model = new ModelStunTrap();

	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y,
			double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y - -2F, (float) z + 0.5F);
		GL11.glRotatef(180F, 0F, 0F, 1F);
		bindTexture(texture);
		GL11.glPushMatrix();
		model.renderModel(0.0825F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();

	}

}
