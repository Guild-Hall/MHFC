package mhfc.net.client.gui;

import net.minecraft.inventory.Slot;

public interface IMHFCTab {
	/**
	 * Main draw function of the tab
	 */
	public void drawTab(int posX, int posY, int mousePosX, int mousePosY,
			float partialTick);

	/**
	 * Handle a click by the mouse from a specified button at a specified
	 * position
	 */
	public void handleClick(int relativeX, int relativeY, int button);

	/**
	 * Whether the tab contains a slot, so it can be moved out of the screen
	 */
	public boolean containsSlot(Slot slot);

	/**
	 * Called whenever the windows is resized or the screen has to be updated
	 * otherwise
	 */
	public void updateScreen();

	/**
	 * Gets called when the tab should be closed
	 */
	public void onClose();

	/**
	 * Gets called when the tab is opened
	 */
	public void onOpen();

	/**
	 * Called when the mouse is moved inside the tab with a mouse button down
	 */
	public void handleMovementMouseDown(int mouseX, int mouseY, int button,
			long timeDiff);

	/**
	 * The function to handle the release of a mouse button
	 */
	public void handleMouseUp(int mouseX, int mouseY, int id);

	/**
	 * The function to handle movement of the mouse, without any information
	 * about the mouse
	 */
	public void handleMovement(int mouseX, int mouseY);
}
