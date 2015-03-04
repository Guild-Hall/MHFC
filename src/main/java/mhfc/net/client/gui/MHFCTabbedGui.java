package mhfc.net.client.gui;

import java.util.ArrayList;
import java.util.List;

import mhfc.net.common.util.gui.MHFCGuiUtil;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public abstract class MHFCTabbedGui extends GuiContainer {

	protected String[] tabNames;
	protected int tabIndex = 0;
	protected int tabDelta[];
	protected int tabHeight;
	protected int tabSplit;
	protected int tabWidth;
	protected int tabX, tabY;
	protected List<IMHFCTab> tabList;

	public MHFCTabbedGui(Container p_i1072_1_, int tabCount) {
		super(p_i1072_1_);
		tabNames = new String[tabCount];
		tabDelta = new int[tabCount];
		tabHeight = 30;
		tabSplit = 1;
		tabWidth = 47;
		tabList = new ArrayList<IMHFCTab>();
		setTab(0);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick,
			int mousePosX, int mousePosY) {
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
		int i = tabIndex;
		GL11.glPushMatrix();
		realWidth = tabWidth + tabDelta[i];
		if (mouseX >= posX - realWidth && mouseX <= posX
				&& mouseY >= posY + i * diff
				&& mouseY <= posY + i * diff + tabHeight) {
			tabDelta[i] = Math.min(tabDelta[i] + 2, 10);
		} else {
			tabDelta[i] = Math.max(tabDelta[i] - 2, 0);
		}
		int colorOfTabName = 0x404040;
		GL11.glColor4f(1f, 1f, 1f, 1f);
		mc.getTextureManager().bindTexture(
				new ResourceLocation(MHFCReference.gui_tab_texture));
		MHFCGuiUtil.drawTexturedBoxFromBorder(posX - realWidth,
				posY + i * diff, this.zLevel, realWidth, tabHeight, 3,
				8f / 256, 8f / 128, 1f, 1f);
		int nameHeight = fontRendererObj.splitStringWidth(tabNames[i],
				tabWidth - 2);
		fontRendererObj.drawSplitString(tabNames[i], posX - realWidth + 3, posY
				+ i * diff + (tabHeight - nameHeight) / 2, tabWidth - 2,
				colorOfTabName);
		GL11.glPopMatrix();
	}

	protected abstract void drawTabBackgroundLayer();

	protected void drawInactiveTabIcons(int posX, int posY, int mouseX,
			int mouseY) {
		posX += 5;
		posY += 14;
		int diff = tabHeight + tabSplit;
		int realWidth = 0;
		for (int i = 0; i < tabNames.length; i++) {
			if (i == tabIndex)
				continue;
			GL11.glPushMatrix();
			realWidth = tabWidth + tabDelta[i];
			if (mouseX >= posX - realWidth && mouseX <= posX
					&& mouseY >= posY + i * diff
					&& mouseY <= posY + i * diff + tabHeight) {
				tabDelta[i] = Math.min(tabDelta[i] + 2, 10);
			} else {
				tabDelta[i] = Math.max(tabDelta[i] - 2, 0);
			}
			int colorOfTabName = 0x808080;
			GL11.glColor4f(1f, 1f, 1f, 1f);
			mc.getTextureManager().bindTexture(
					new ResourceLocation(MHFCReference.gui_tab_texture));
			MHFCGuiUtil.drawTexturedBoxFromBorder(posX - realWidth, posY + i
					* diff, this.zLevel, realWidth, tabHeight, 3, 8f / 256,
					8f / 128, 1f, 1f);
			int nameHeight = fontRendererObj.splitStringWidth(tabNames[i],
					tabWidth - 2);
			fontRendererObj.drawSplitString(tabNames[i], posX - realWidth + 3,
					posY + i * diff + (tabHeight - nameHeight) / 2,
					tabWidth - 2, colorOfTabName);
			GL11.glPopMatrix();
		}
	}

	protected void drawTab(int tabIndex, int posX, int posY, int mousePosX,
			int mousePosY, float partialTick) {
		if (tabIndex >= 0 && tabIndex < tabList.size())
			tabList.get(tabIndex).drawTab(tabX, tabY, mousePosX - tabX,
					mousePosY - tabY, partialTick);
	}

	protected void setTab(int index) {
		if (tabIndex >= 0 && tabIndex < tabList.size()) {
			tabList.get(tabIndex).onClose();
		}
		if (index >= 0 && index < tabList.size()) {
			tabIndex = index;
			tabList.get(tabIndex).onOpen();
		}
		@SuppressWarnings("unchecked")
		List<Slot> slots = inventorySlots.inventorySlots;
		for (Slot slot : slots) {
			if (index >= 0 && index < tabList.size()
					&& tabList.get(tabIndex).containsSlot(slot)) {
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
		for (int i = 0; i < tabNames.length; i++) {
			realWidth = tabWidth + tabDelta[i];
			if (mouseX >= guiLeft + 3 - realWidth && mouseX <= guiLeft + 3
					&& mouseY >= guiTop + 14 + i * diff
					&& mouseY <= guiTop + 14 + i * diff + tabHeight) {
				setTab(i);
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if (tabIndex >= 0 && tabIndex < tabList.size())
			tabList.get(tabIndex).handleClick(mouseX - tabX, mouseY - tabY,
					mouseButton);
	}

	@Override
	protected void mouseMovedOrUp(int mouseX, int mouseY, int id) {
		super.mouseMovedOrUp(mouseX, mouseY, id);
		if (tabIndex >= 0 && tabIndex < tabList.size()) {
			IMHFCTab tab = tabList.get(tabIndex);
			if (id < 0) {
				tab.handleMovement(mouseX - tabX, mouseY - tabY);
			} else {
				tab.handleMouseUp(mouseX - tabX, mouseY - tabY, id);
			}
		}
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int button,
			long timeDiff) {
		super.mouseClickMove(mouseX, mouseY, button, timeDiff);
		if (tabIndex >= 0 && tabIndex < tabList.size()) {
			tabList.get(tabIndex).handleMovementMouseDown(mouseX - tabX,
					mouseY - tabY, button, timeDiff);
		}
	}

	public List<IMHFCTab> getTabList() {
		return tabList;
	}

	@Override
	public void updateScreen() {
		IMHFCTab tab = tabList.get(tabIndex);
		if (tab != null)
			tab.updateTab(tabX, tabY);
		super.updateScreen();
	}

	@Override
	public void onGuiClosed() {
		for (int i = 0; i < tabList.size(); i++) {
			tabList.get(i).onClose();
		}
	}

}
