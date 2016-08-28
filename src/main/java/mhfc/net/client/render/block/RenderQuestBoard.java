package mhfc.net.client.render.block;

import mhfc.net.client.model.block.ModelQuestBoard;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.block.BlockQuestBoard;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderQuestBoard extends TileEntitySpecialRenderer {
	private ModelQuestBoard model;

	public RenderQuestBoard() {
		model = new ModelQuestBoard();
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y,
			double z, float f) {

		GL11.glPushMatrix();
		float f1 = 0.6666667F;
		int meta = tile.getBlockMetadata();
		if ((meta & BlockQuestBoard.offsetMask) == 0x0) {
			GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F * f1,
					(float) z + 0.5F);
			if ((meta & BlockQuestBoard.upMask) == 0x0)
				GL11.glTranslatef(0, -1.06f, 0);
			else
				GL11.glTranslatef(0, -.75f, 0);
			float f2 = meta * 90.0F;
			GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
		} else {
			float rotation;
			meta = (meta & BlockQuestBoard.rotationMask);
			rotation = 90.0F * meta;

			GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F * f1,
					(float) z + 0.5F);
			GL11.glRotatef(-rotation, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, -.75F, -0.3875F);
			GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
		}
		GL11.glScalef(1.75f, 1.75f, 1.75f);
		// Tessellator tessellator = Tessellator.instance;
		bindTexture(new ResourceLocation(MHFCReference.tile_questboard_tex));
		GL11.glPushMatrix();

		int runnings = MHFCRegQuestVisual.getRunningMissionIDs().size();
		model.renderModel((int) (Math.log1p(runnings) / Math.log(1.5)));
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
