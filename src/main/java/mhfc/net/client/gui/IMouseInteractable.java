package mhfc.net.client.gui;

public interface IMouseInteractable {

	/**
	 * Handle a click by the mouse from a specified button at a specified position and returns if it was handled
	 */
	public boolean handleClick(int relativeX, int relativeY, int button);

	/**
	 * Called when the mouse is moved inside the tab with a mouse button down
	 */
	public void handleMovementMouseDown(int mouseX, int mouseY, int button, long timeDiff);

	/**
	 * The function to handle the release of a mouse button
	 */
	public void handleMouseUp(int mouseX, int mouseY, int id);

	/**
	 * The function to handle movement of the mouse, without any information about the mouse
	 */
	public void handleMovement(int mouseX, int mouseY);
}
