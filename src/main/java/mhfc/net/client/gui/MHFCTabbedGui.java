package mhfc.net.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.util.gui.MHFCGuiUtil;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public abstract class MHFCTabbedGui extends GuiContainer {

	private static class TabAttribute {
		IMHFCTab tab;
		String name;
		int tabDelta;

		public TabAttribute(IMHFCTab tab, String name) {
			this.tab = tab;
			this.name = name;
			this.tabDelta = 0;
		}
	}

	private List<TabAttribute> tabs;
	protected int tabIndex = 0;
	protected int tabHeight;
	protected int tabSplit;
	protected int tabWidth;
	protected int tabX, tabY;

	public MHFCTabbedGui(Container p_i1072_1_) {
		super(p_i1072_1_);
		tabs = new ArrayList<>();
		tabHeight = 30;
		tabSplit = 1;
		tabWidth = 47;
		setTab(0);
	}

	protected void addTab(IMHFCTab tab, String name) {
		addTab(tab, name, tabs.size());
	}

	protected void addTab(IMHFCTab tab, String name, int index) {
		tabs.add(new TabAttribute(tab, name));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int mousePosX, int mousePosY) {
		int posX = (this.width - this.xSize - tabWidth) / 2 + tabWidth;
		int posY = (this.height - this.ySize) / 2;
		tabX = posX;
		tabY = posY;
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		drawInactiveTabIcons(posX, posY, mousePosX, mousePosY);
		drawTabBackgroundLayer();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		drawTab(tabIndex, posX, posY, mousePosX, mousePosY, partialTick);
		drawActiveTabIcon(posX, posY, mousePosX, mousePosY);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	protected void drawActiveTabIcon(int posX, int posY, int mouseX, int mouseY) {
		posX += 5;
		posY += 14;
		int diff = tabHeight + tabSplit;
		int realWidth = 0;
		TabAttribute activeAttribute = tabs.get(tabIndex);
		GL11.glPushMatrix();
		realWidth = tabWidth + activeAttribute.tabDelta;
		if (mouseX >= posX - realWidth && mouseX <= posX && mouseY >= posY + tabIndex * diff
				&& mouseY <= posY + tabIndex * diff + tabHeight) {
			activeAttribute.tabDelta = Math.min(activeAttribute.tabDelta + 2, 10);
		} else {
			activeAttribute.tabDelta = Math.max(activeAttribute.tabDelta - 2, 0);
		}
		int colorOfTabName = 0x404040;
		GL11.glColor4f(1f, 1f, 1f, 1f);
		mc.getTextureManager().bindTexture(new ResourceLocation(MHFCReference.gui_tab_texture));
		MHFCGuiUtil.drawTexturedBoxFromBorder(
				posX - realWidth,
				posY + tabIndex * diff,
				this.zLevel,
				realWidth,
				tabHeight,
				3,
				8f / 256,
				8f / 128,
				1f,
				1f);
		int nameHeight = fontRendererObj.splitStringWidth(activeAttribute.name, tabWidth - 2);
		fontRendererObj.drawSplitString(
				activeAttribute.name,
				posX - realWidth + 3,
				posY + tabIndex * diff + (tabHeight - nameHeight) / 2,
				tabWidth - 2,
				colorOfTabName);
		GL11.glPopMatrix();
	}

	protected abstract void drawTabBackgroundLayer();

	protected void drawInactiveTabIcons(int posX, int posY, int mouseX, int mouseY) {
		posX += 5;
		posY += 14;
		int diff = tabHeight + tabSplit;
		int realWidth = 0;
		for (int i = 0; i < tabs.size(); i++) {
			if (i == tabIndex) {
				continue;
			}
			GL11.glPushMatrix();
			TabAttribute tabAtt = tabs.get(i);
			realWidth = tabWidth + tabAtt.tabDelta;
			if (mouseX >= posX - realWidth && mouseX <= posX && mouseY >= posY + i * diff
					&& mouseY <= posY + i * diff + tabHeight) {
				tabAtt.tabDelta = Math.min(tabAtt.tabDelta + 2, 10);
			} else {
				tabAtt.tabDelta = Math.max(tabAtt.tabDelta - 2, 0);
			}
			int colorOfTabName = 0x808080;
			GL11.glColor4f(1f, 1f, 1f, 1f);
			mc.getTextureManager().bindTexture(new ResourceLocation(MHFCReference.gui_tab_texture));
			MHFCGuiUtil.drawTexturedBoxFromBorder(
					posX - realWidth,
					posY + i * diff,
					this.zLevel,
					realWidth,
					tabHeight,
					3,
					8f / 256,
					8f / 128,
					1f,
					1f);
			int nameHeight = fontRendererObj.splitStringWidth(tabAtt.name, tabWidth - 2);
			fontRendererObj.drawSplitString(
					tabAtt.name,
					posX - realWidth + 3,
					posY + i * diff + (tabHeight - nameHeight) / 2,
					tabWidth - 2,
					colorOfTabName);
			GL11.glPopMatrix();
		}
	}

	protected void drawTab(int tabIndex, int posX, int posY, int mousePosX, int mousePosY, float partialTick) {
		if (tabIndex >= 0 && tabIndex < tabs.size()) {
			GL11.glPushMatrix();
			GL11.glTranslated(posX, posY, 0);
			tabs.get(tabIndex).tab.draw(mousePosX - tabX, mousePosY - tabY, partialTick);
			GL11.glPopMatrix();
		}
	}

	protected void setTab(int index) {
		if (tabIndex >= 0 && tabIndex < tabs.size()) {
			tabs.get(tabIndex).tab.onClose();
		}
		if (index >= 0 && index < tabs.size()) {
			tabIndex = index;
			tabs.get(tabIndex).tab.onOpen();
		}
		@SuppressWarnings("unchecked")
		List<Slot> slots = inventorySlots.inventorySlots;
		for (Slot slot : slots) {
			if (index >= 0 && index < tabs.size() && tabs.get(tabIndex).tab.containsSlot(slot)) {
				slot.xDisplayPosition = Math.abs(slot.xDisplayPosition + 200) - 200;
				slot.yDisplayPosition = Math.abs(slot.yDisplayPosition + 200) - 200;
			} else {
				slot.xDisplayPosition = -Math.abs(slot.xDisplayPosition + 200) - 200;
				slot.yDisplayPosition = -Math.abs(slot.yDisplayPosition + 200) - 200;
			}
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		int diff = tabHeight + tabSplit;
		int realWidth = 0;
		for (int i = 0; i < tabs.size(); i++) {
			TabAttribute tabAtt = tabs.get(i);
			realWidth = tabWidth + tabAtt.tabDelta;
			if (mouseX >= guiLeft + 3 - realWidth && mouseX <= guiLeft + 3 && mouseY >= guiTop + 14 + i * diff
					&& mouseY <= guiTop + 14 + i * diff + tabHeight) {
				setTab(i);
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if (tabIndex >= 0 && tabIndex < tabs.size()) {
			tabs.get(tabIndex).tab.handleClick(mouseX - tabX, mouseY - tabY, mouseButton);
		}
	}

	@Override
	protected void mouseMovedOrUp(int mouseX, int mouseY, int id) {
		super.mouseMovedOrUp(mouseX, mouseY, id);
		if (tabIndex >= 0 && tabIndex < tabs.size()) {
			IMHFCTab tab = tabs.get(tabIndex).tab;
			if (id < 0) {
				tab.handleMovement(mouseX - tabX, mouseY - tabY);
			} else {
				tab.handleMouseUp(mouseX - tabX, mouseY - tabY, id);
			}
		}
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int button, long timeDiff) {
		super.mouseClickMove(mouseX, mouseY, button, timeDiff);
		if (tabIndex >= 0 && tabIndex < tabs.size()) {
			tabs.get(tabIndex).tab.handleMovementMouseDown(mouseX - tabX, mouseY - tabY, button, timeDiff);
		}
	}

	@Override
	public void updateScreen() {
		IMHFCTab tab = tabs.get(tabIndex).tab;
		if (tab != null) {
			tab.updateTab();
		}
		super.updateScreen();
	}

	@Override
	public void onGuiClosed() {
		for (int i = 0; i < tabs.size(); i++) {
			tabs.get(i).tab.onClose();
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		for (TabAttribute tabAtt : tabs) {
			tabAtt.tab.initializeContext(mc);
		}
	}

}
