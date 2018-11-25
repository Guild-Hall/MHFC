package mhfc.net.client.render.block;

import mhfc.net.client.model.block.ModelStunTrap;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.tile.TileStunTrap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderStunTrap extends TileEntitySpecialRenderer<TileStunTrap> {

	private ModelStunTrap model;

	public RenderStunTrap() {
		model = new ModelStunTrap();

	}

	@Override
	public void render(TileStunTrap tileentity, double x, double y, double z, float f, int destruction,float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y - -2F, (float) z + 0.5F);
		GL11.glRotatef(180F, 0F, 0F, 1F);
		bindTexture(new ResourceLocation(ResourceInterface.tile_stuntrap_tex));
		GL11.glPushMatrix();
		model.renderModel(0.0825F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();

	}

}
