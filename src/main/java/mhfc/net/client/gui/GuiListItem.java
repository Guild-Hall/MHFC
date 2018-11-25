package mhfc.net.client.gui;

import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.client.util.gui.MHFCGuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Iterator;
import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class GuiListItem {

	public static enum Alignment {
		LEFT,
		MIDDLE,
		RIGHT;
	}

	public abstract String getRepresentationString();

	public void draw(int x, int y, int width, int height, Minecraft m, boolean selected, Alignment alignment) {

		GlStateManager.pushMatrix();
		GlStateManager.color(1, 1, 1, 1);

		GlStateManager.matrixMode(GL11.GL_TEXTURE);
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.5f, selected ? 0.25f : 0, 0);
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);

		Minecraft.getMinecraft().getTextureManager().bindTexture(MHFCRegQuestVisual.CLICKABLE_LIST);
		MHFCGuiUtil.drawTexturedBoxFromBorder(x, y, 0, width, height, 5, 7 / 128f, 0.5f, 0.25f);

		GlStateManager.matrixMode(GL11.GL_TEXTURE);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);

		int innerStringWidth = width - 5;
		FontRenderer fRend = m.fontRenderer;
		String representation = getRepresentationString().trim();
		List<String> lineWrapped = fRend.listFormattedStringToWidth(representation, innerStringWidth);
		int stringLength = 0;
		Iterator<String> it = lineWrapped.iterator();
		while (it.hasNext()) {
			String s = it.next();
			int thisStringLength = fRend.getStringWidth(s);
			stringLength = Math.max(stringLength, thisStringLength);
		}
		int lines = lineWrapped.size();

		int positionY = y + (height - (fRend.FONT_HEIGHT) * lines) / 2 + 1;
		int positionX = x;
		switch (alignment) {
		case LEFT:
			positionX += 3;
			break;
		case MIDDLE:
		default:
			positionX += (width - stringLength) / 2;
			break;
		case RIGHT:
			positionX += width - stringLength - 3;
		}

		int color = (selected) ? MHFCGuiUtil.COLOUR_FOREGROUND : MHFCGuiUtil.COLOUR_TEXT;
		fRend.drawSplitString(representation, positionX, positionY, innerStringWidth, color);
		GlStateManager.popMatrix();
	}
}
