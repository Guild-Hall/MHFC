package mhfc.net.client.gui.quests;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;

public class QuestGiverScreen extends GuiScreen {

	private List<String> groupIDsDisplayed;

	public QuestGiverScreen(String[] groupIDs) {
		groupIDsDisplayed = new ArrayList<String>();
		for (int i = 0; i < groupIDs.length; i++)
			groupIDsDisplayed.add(groupIDs[i]);
	}

	@Override
	public void drawScreen(int positionX, int positionY, float partialTick) {
		super.drawScreen(positionX, positionY, partialTick);
	}

	@Override
	public void onGuiClosed() {
		// TODO Auto-generated method stub
		super.onGuiClosed();
	}

}
