package mhfc.net.client.gui;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiScreen;

public abstract class MHFCGui extends GuiScreen implements IMHFCGuiItem {

	public static class ComponentPosition {
		public int positionX, positionY;

		public ComponentPosition(int positionX, int positionY) {
			this.positionX = positionX;
			this.positionY = positionY;
		}
	}

	protected double mouseLastX, mouseLastY;
	protected double mouseClickX, mouseClickY;
	protected int mouseClickButton;

	protected Map<IMHFCGuiItem, ComponentPosition> screenComponents = new HashMap<>();

	@Override
	public void drawScreen(int mouseX, int mouseY, float partial) {
		super.drawScreen(mouseX, mouseY, partial);
		for (IMHFCGuiItem item : screenComponents.keySet()) {
			ComponentPosition pos = screenComponents.get(item);
			GL11.glPushMatrix();
			// GL11.glTranslated(pos.positionX, pos.positionY, 0);
			item.draw(mouseX - pos.positionX, mouseY - pos.positionY, partial);
			GL11.glPopMatrix();
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		super.mouseClicked(mouseX, mouseY, button);
		handleClick(mouseX, mouseY, button);
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int button, long timeDiff) {
		super.mouseClickMove(mouseX, mouseY, button, timeDiff);
		handleMovementMouseDown(mouseX, mouseY, button, timeDiff);
	}

	@Override
	protected void mouseMovedOrUp(int mouseX, int mouseY, int button) {
		super.mouseMovedOrUp(mouseX, mouseY, button);
		if (button < 0) {
			handleMovement(mouseX, mouseY);
		} else {
			handleMouseUp(mouseX, mouseY, button);
		}
	}

	@Override
	public boolean handleClick(int relativeX, int relativeY, int button) {
		mouseClickX = relativeX;
		mouseClickY = relativeY;
		mouseLastX = relativeX;
		mouseLastY = relativeY;
		mouseClickButton = button;
		for (IMHFCGuiItem item : screenComponents.keySet()) {
			ComponentPosition pos = screenComponents.get(item);
			if (item.handleClick(relativeX - pos.positionX, relativeY - pos.positionY, button))
				itemUpdated(item);
		}
		return true;
	}

	@Override
	public void draw(double mouseX, double mouseY, float partialTick) {
		drawScreen((int) mouseX, (int) mouseY, partialTick);
	}

	@Override
	public void handleMovementMouseDown(int mouseX, int mouseY, int button, long timeDiff) {
		for (IMHFCGuiItem item : screenComponents.keySet()) {
			ComponentPosition pos = screenComponents.get(item);
			item.handleMovementMouseDown(mouseX - pos.positionX, mouseY - pos.positionY, button, timeDiff);
		}
		mouseLastX = mouseX;
		mouseLastY = mouseY;
		mouseClickButton = button;
	}

	@Override
	public void handleMouseUp(int mouseX, int mouseY, int id) {
		for (IMHFCGuiItem item : screenComponents.keySet()) {
			ComponentPosition pos = screenComponents.get(item);
			item.handleMouseUp(mouseX - pos.positionX, mouseY - pos.positionY, id);
		}
		mouseLastX = mouseX;
		mouseLastY = mouseY;
	}

	@Override
	public void handleMovement(int mouseX, int mouseY) {
		for (IMHFCGuiItem item : screenComponents.keySet()) {
			ComponentPosition pos = screenComponents.get(item);
			item.handleMovement(mouseX - pos.positionX, mouseY - pos.positionY);
		}
		mouseLastX = mouseX;
		mouseLastY = mouseY;
	}

	protected abstract void itemUpdated(IMHFCGuiItem item);

}
