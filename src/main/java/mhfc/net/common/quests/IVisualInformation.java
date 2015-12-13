package mhfc.net.common.quests;

import net.minecraft.client.gui.FontRenderer;

public interface IVisualInformation {

	String getSerializerType();

	String getName();

	String getDescription();

	String getClient();

	String getAims();

	String getFails();

	String getTimeLimitAsString();

	String getQuestType();

	String getRewardString();

	String getFeeString();

	String getAreaName();

	String getMaxPartySize();

	void drawInformation(int positionX, int positionY, int width, int height,
		FontRenderer fontRenderer, int renderFrame);

	void drawInformation(int positionX, int positionY, int width, int height,
		int page, FontRenderer fontRenderer);

}
