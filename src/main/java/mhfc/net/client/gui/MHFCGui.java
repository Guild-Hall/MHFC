package mhfc.net.client.gui;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import javax.vecmath.Vector2f;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public abstract class MHFCGui extends GuiScreen implements IMHFCGuiItem {

	protected double mouseLastX, mouseLastY;
	protected double mouseClickX, mouseClickY;
	protected int mouseClickButton;
	boolean clickHandled;

	private Map<IMHFCGuiItem, Vector2f> screenComponents = new IdentityHashMap<>();

	protected void addScreenComponent(IMHFCGuiItem component, Vector2f position) {
		Objects.requireNonNull(position);
		Objects.requireNonNull(component);
		component.initializeContext(mc);
		screenComponents.put(component, position);
	}

	protected Vector2f getPosition(IMHFCGuiItem component) {
		return screenComponents.get(component);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partial) {
		GL11.glPushMatrix();
		draw(mouseX, mouseY, partial);
		GL11.glPopMatrix();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		handleClick(mouseX, mouseY, button);
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int button, long timeDiff) {
		handleMovementMouseDown(mouseX, mouseY, button, timeDiff);
	}

	@Override
	protected void mouseMovedOrUp(int mouseX, int mouseY, int button) {
		if (button < 0) {
			handleMovement(mouseX, mouseY);
		} else {
			handleMouseUp(mouseX, mouseY, button);
		}
	}

	@Override
	protected void actionPerformed(GuiButton p_146284_1_) {
		clickHandled = true;
	}

	@Override
	public boolean handleClick(float relativeX, float relativeY, int button) {
		mouseClickX = relativeX;
		mouseClickY = relativeY;
		mouseLastX = relativeX;
		mouseLastY = relativeY;
		mouseClickButton = button;
		clickHandled = false;
		super.mouseClicked((int) relativeX, (int) relativeY, button);
		for (IMHFCGuiItem item : screenComponents.keySet()) {
			Vector2f pos = screenComponents.get(item);
			if (item.handleClick(relativeX - pos.x, relativeY - pos.y, button)) {
				itemUpdated(item);
				clickHandled = true;
			}
		}
		return clickHandled;
	}

	@Override
	public void draw(double mouseX, double mouseY, float partialTick) {
		super.drawScreen((int) mouseX, (int) mouseY, partialTick);
		Iterator<IMHFCGuiItem> it = screenComponents.keySet().iterator();
		while (it.hasNext()) {
			IMHFCGuiItem item = it.next();
			item.initializeContext(mc);
			Vector2f pos = screenComponents.get(item);
			GL11.glPushMatrix();
			GL11.glTranslated(pos.x, pos.y, 0);
			item.draw(mouseX - pos.x, mouseY - pos.y, partialTick);
			GL11.glPopMatrix();
		}
	}

	@Override
	public void handleMovementMouseDown(float mouseX, float mouseY, int button, long timeDiff) {
		super.mouseClickMove((int) mouseX, (int) mouseY, button, timeDiff);
		for (IMHFCGuiItem item : screenComponents.keySet()) {
			Vector2f pos = screenComponents.get(item);
			item.handleMovementMouseDown(mouseX - pos.x, mouseY - pos.y, button, timeDiff);
		}
		mouseLastX = mouseX;
		mouseLastY = mouseY;
		mouseClickButton = button;
	}

	@Override
	public void handleMouseUp(float mouseX, float mouseY, int id) {
		super.mouseMovedOrUp((int) mouseX, (int) mouseY, id);
		for (IMHFCGuiItem item : screenComponents.keySet()) {
			Vector2f pos = screenComponents.get(item);
			item.handleMouseUp(mouseX - pos.x, mouseY - pos.y, id);
		}
		mouseLastX = mouseX;
		mouseLastY = mouseY;
	}

	@Override
	public void handleMovement(float mouseX, float mouseY) {
		super.mouseMovedOrUp((int) mouseX, (int) mouseY, -1);
		for (IMHFCGuiItem item : screenComponents.keySet()) {
			Vector2f pos = screenComponents.get(item);
			item.handleMovement(mouseX - pos.x, mouseY - pos.y);
		}
		mouseLastX = mouseX;
		mouseLastY = mouseY;
	}

	protected abstract void itemUpdated(IMHFCGuiItem item);

	@Override
	public void initializeContext(Minecraft mc) {
		setWorldAndResolution(mc, width, height);
	}

	@Override
	public void initGui() {
		super.initGui();
		for (IMHFCGuiItem guiItem : screenComponents.keySet()) {
			guiItem.initializeContext(mc);
		}
	}
}
