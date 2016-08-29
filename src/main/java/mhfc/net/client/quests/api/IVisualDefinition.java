package mhfc.net.client.quests.api;

import net.minecraft.client.gui.FontRenderer;

public interface IVisualDefinition {
	/**
	 * Determines the title to show to the client for this visual.
	 *
	 * @return
	 */
	String getDisplayName();

	/**
	 * Generates a new IMissionInformation. Only called on the client when the player enters a quest.
	 *
	 * @return
	 */
	IMissionInformation build();

	/**
	 * Draws the visual definition when it should be displayed to the client, e.g. on the quest board.
	 *
	 */
	void drawInformation(int positionX, int positionY, int width, int height, int page, FontRenderer fontRenderer);

	int getPageCount();
}
