package mhfc.net.client.gui;

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
		FontRenderer fRend = m.fontRenderer;
		String representation = getRepresentationString();
		@SuppressWarnings("unchecked")
		List<String> lineWrapped = fRend.listFormattedStringToWidth(
				representation, width);
		int lines = lineWrapped.size();
		int stringLength = 0;
		for (String s : lineWrapped) {
			stringLength = Math.max(stringLength, fRend.getStringWidth(s));
		}
		int color = (selected) ? 0x404040 : 0x808080;
		int positionX = x;
		switch (alignment) {
			case LEFT :
				positionX += 3;
				break;
			case MIDDLE :
				positionX += (width - stringLength) / 2;
				break;
			case RIGHT :
				positionX += stringLength - 3;
		}
		fRend.drawSplitString(representation, positionX, y
				+ (height - (fRend.FONT_HEIGHT) * lines) / 2 + 1, width - 5,
				color);
	}
}
