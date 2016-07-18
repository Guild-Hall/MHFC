package mhfc.net.client.quests;

import mhfc.net.common.quests.api.IVisualInformation;
import mhfc.net.common.util.MHFCStringDecode.CompositeString;
import net.minecraft.client.gui.FontRenderer;

public interface IRunningInformation extends IVisualInformation {
	CompositeString getMemberInformation();

	CompositeString getShortStatus();

	CompositeString getLongStatus();

	void drawInformation(
			int positionX,
			int positionY,
			int width,
			int height,
			FontRenderer fontRenderer,
			int renderFrame);

	void drawInformation(int positionX, int positionY, int width, int height, int page, FontRenderer fontRenderer);

	default void cleanUp() {
		// Called before the client disposes this information
	}
}
