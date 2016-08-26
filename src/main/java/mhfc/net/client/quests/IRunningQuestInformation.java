package mhfc.net.client.quests;

import mhfc.net.common.quests.api.IVisualDefinition;
import mhfc.net.common.util.MHFCStringDecode.CompositeString;
import net.minecraft.client.gui.FontRenderer;

public interface IRunningQuestInformation extends IVisualDefinition {

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

	CompositeString getMemberInformation();

	CompositeString getShortStatus();

	CompositeString getLongStatus();

	@Override
	void drawInformation(int positionX, int positionY, int width, int height, int page, FontRenderer fontRenderer);

	default void cleanUp() {
		// Called before the client disposes this information
	}
}
