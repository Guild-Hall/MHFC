package mhfc.net.common.quests.api;

import mhfc.net.client.quests.IRunningQuestInformation;
import net.minecraft.client.gui.FontRenderer;

public interface IVisualDefinition {
	/**
	 * Determines the title to show to the client for this visual.
	 *
	 * @return
	 */
	String getDisplayName();

	String getSerializerType();

	/**
	 * Generates a new IRunningQuestInformation. Only called on the client when the player enters a quest.
	 *
	 * @return
	 */
	IRunningQuestInformation newInstance();

	/**
	 * Draws the visual definition when it should be displayed to the client, e.g. on the quest board.
	 * 
	 * @param positionX
	 * @param positionY
	 * @param width
	 * @param height
	 * @param page
	 * @param fontRendererObj
	 */
	void drawInformation(int positionX, int positionY, int width, int height, int page, FontRenderer fontRendererObj);
}
