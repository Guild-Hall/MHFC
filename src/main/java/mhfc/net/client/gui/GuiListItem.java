package mhfc.net.client.gui;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.client.util.gui.MHFCGuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public abstract class GuiListItem {

	public static enum Alignment {
		LEFT,
		MIDDLE,
		RIGHT;
	}

	public abstract String getRepresentationString();

	public void draw(int x, int y, int width, int height, Minecraft m, boolean selected, Alignment alignment) {

		GL11.glPushMatrix();
		GL11.glColor4f(1, 1, 1, 1);

		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5f, selected ? 0.25f : 0, 0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		Minecraft.getMinecraft().getTextureManager().bindTexture(MHFCRegQuestVisual.CLICKABLE_LIST);
		MHFCGuiUtil.drawTexturedBoxFromBorder(x, y, 0, width, height, 5, 7 / 128f, 0.5f, 0.25f);

		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

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
			positionX += (width - stringLength) / 2;
			break;
		case RIGHT:
			positionX += width - stringLength - 3;
		}

		int color = (selected) ? MHFCGuiUtil.COLOUR_FOREGROUND : MHFCGuiUtil.COLOUR_TEXT;
		fRend.drawSplitString(representation, positionX, positionY, innerStringWidth, color);
		GL11.glPopMatrix();
	}
}
