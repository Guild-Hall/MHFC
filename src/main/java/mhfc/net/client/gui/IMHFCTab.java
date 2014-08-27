package mhfc.net.client.gui;

import net.minecraft.inventory.Slot;

public interface IMHFCTab {
	public void drawTab(int posX, int posY, int mousePosX, int mousePosY,
			float partialTick);

	public void handleClick(int relativeX, int relativeY, int button);

	public boolean containsSlot(Slot slot);
}
