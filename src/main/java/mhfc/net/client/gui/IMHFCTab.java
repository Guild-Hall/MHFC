package mhfc.net.client.gui;

import net.minecraft.inventory.Slot;

public interface IMHFCTab extends IMouseInteractable {
	/**
	 * Main draw function of the tab
	 */
	public void drawTab(int posX, int posY, int mousePosX, int mousePosY,
			float partialTick);

	/**
	 * Whether the tab contains a slot, so it can be moved out of the screen
	 */
	public boolean containsSlot(Slot slot);

	/**
	 * Called whenever the windows is resized or the screen has to be updated
	 * otherwise
	 */
	public void updateTab(int posX, int posY);

	/**
	 * Gets called when the tab should be closed
	 */
	public void onClose();

	/**
	 * Gets called when the tab is opened
	 */
	public void onOpen();

}
