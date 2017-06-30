package mhfc.net.client.render.block;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.block.ModelHunterBench;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.tile.TileHunterBench;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderHunterBench extends TileEntitySpecialRenderer<TileHunterBench> {
	private ModelHunterBench model;

	public RenderHunterBench() {
		model = new ModelHunterBench();
	}

	@Override
	public void renderTileEntityAt(TileHunterBench tileentity, double x, double y, double z, float f, int destruction) {
		GL11.glPushMatrix();
		// Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef((float) x + 0.5F, (float) y - -0.7F, (float) z + 0.5F);
		GL11.glRotatef(180F, 0F, 0F, 1F);
		bindTexture(new ResourceLocation(ResourceInterface.tile_huntertable_tex));
		GL11.glPushMatrix();
		model.renderModel(0.0325F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
