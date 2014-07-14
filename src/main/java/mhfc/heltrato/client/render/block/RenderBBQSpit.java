package mhfc.heltrato.client.render.block;

import mhfc.heltrato.client.model.block.ModelBBQSpit;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderBBQSpit extends TileEntitySpecialRenderer{
	private ModelBBQSpit model;
	private static final ResourceLocation texture = new ResourceLocation("mhfc:textures/tile/bbqspit.png");
	
	public RenderBBQSpit(){
		model = new ModelBBQSpit();
	}
	
	public void renderTileEntityAt(TileEntity tileentity, double x, double y,double z, float f) {
		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef((float)x + 0.5F, (float)y - -1.5F, (float)z + 0.5F);
		GL11.glRotatef(180F, 0F, 0F, 1F);
		bindTexture(texture);
		GL11.glPushMatrix();
		model.renderModel(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		
		
	}

}
