package mhfc.net.client.gui;

public interface IMouseInteractable {

	/**
	 * Handle a click by the mouse from a specified button at a specified position and returns if it was handled
	 */
	public boolean handleClick(float f, float g, int button);

	/**
	 * Called when the mouse is moved inside the tab with a mouse button down
	 */
	public void handleMovementMouseDown(float f, float g, int button, long timeDiff);

	/**
	 * The function to handle the release of a mouse button
	 */
	public void handleMouseUp(float f, float g, int id);

	/**
	 * The function to handle movement of the mouse, without any information about the mouse
	 */
	public void handleMovement(float f, float g);
}
