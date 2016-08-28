package mhfc.net.client.quests.api;

import net.minecraft.client.gui.FontRenderer;

public interface IPagedView {
	void drawInformation(int positionX, int positionY, int width, int height, int page, FontRenderer fontRenderer);

	int getPageCount();
}
