package mhfc.net.client.render.block;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import mhfc.net.client.model.block.ModelArmorStandBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderArmorStandBase extends TileEntitySpecialRenderer {
	
	private ModelArmorStandBase model;
	private static final ResourceLocation texture = new ResourceLocation("mhfc:textures/tile/armorstandbase.png");
	
	public RenderArmorStandBase() {
		model = new ModelArmorStandBase();
	}

	public void renderTileEntityAt(TileEntity tileentity, double x, double y,double z, float f) {
		GL11.glPushMatrix();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)x + 0.5F, (float)y - -1.5F, (float)z + 0.5F);
		GL11.glRotatef(180F, 0F, 0F, 1F);
		bindTexture(texture);
		GL11.glPushMatrix();
		model.renderModel(0.0625F);
		GL11.glPopMatrix();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
	

}
