package mhfc.net.client.gui;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public abstract class GuiListItem {

	public static enum Alignment {
		LEFT,
		MIDDLE,
		RIGHT;
	}

	public abstract String getRepresentationString();

	public void draw(int x, int y, int width, int height, Minecraft m,
			boolean selected, Alignment alignment) {
		int innerStringWidth = width - 5;
		FontRenderer fRend = m.fontRenderer;
		String representation = getRepresentationString().trim();
		@SuppressWarnings("unchecked")
		List<String> lineWrapped = fRend.listFormattedStringToWidth(
				representation, innerStringWidth);
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
			case LEFT :
				positionX += 3;
				break;
			case MIDDLE :
				positionX += (width - stringLength) / 2;
				break;
			case RIGHT :
				positionX += width - stringLength - 3;
		}

		int color = (selected) ? 0x404040 : 0x808080;
		fRend.drawSplitString(representation, positionX, positionY,
				innerStringWidth, color);
	}
}
