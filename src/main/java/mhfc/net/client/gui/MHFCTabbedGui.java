package mhfc.net.client.gui;

import java.util.ArrayList;
import java.util.List;

import mhfc.net.common.util.gui.MHFCGuiUtil;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class MHFCTabbedGui extends GuiContainer {

	protected String[] tabNames;
	protected int tabIndex = 0;
	protected int tabDelta[];
	protected int tabHeight;
	protected int tabSplit;
	protected int tabWidth;
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
	public void drawScreen(int mousePosX, int mousePosY, float partialTick) {
		int posX = (this.width - this.xSize) / 2;
		int posY = (this.height - this.ySize) / 2;
		GL11.glPushMatrix();
		super.drawScreen(mousePosX, mousePosY, partialTick);
		GL11.glTranslatef(0, 0, 1.5f);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		drawTabIcons(posX, posY, mousePosX, mousePosY);
		drawTab(tabIndex, posX, posY, mousePosX, mousePosY, partialTick);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	protected void drawTabIcons(int posX, int posY, int mouseX, int mouseY) {
		posX += 3;
		posY += 5;
		int diff = tabHeight + tabSplit;
		int realWidth = 0;
		for (int i = 0; i < tabNames.length; i++) {
			GL11.glPushMatrix();
			realWidth = tabWidth + tabDelta[i];
			if (mouseX >= posX - realWidth && mouseX <= posX
					&& mouseY >= posY + i * diff
					&& mouseY <= posY + i * diff + tabHeight) {
				tabDelta[i] = Math.min(tabDelta[i] + 2, 10);
			} else {
				tabDelta[i] = Math.max(tabDelta[i] - 2, 0);
			}
			int colorOfTabName;
			if (i == tabIndex) {
				colorOfTabName = 0x404040;
			} else {
				colorOfTabName = 0x808080;
				GL11.glTranslatef(0, 0, -0.5f);
			}
			GL11.glColor4f(1f, 1f, 1f, 1f);
			mc.getTextureManager().bindTexture(
					new ResourceLocation(MHFCReference.gui_tab_texture));
			MHFCGuiUtil
					.drawTexturedBoxFromBorder(posX - realWidth, posY + i
							* diff, realWidth, tabHeight, 3, 8f / 256,
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
			tabList.get(tabIndex).drawTab(posX, posY, mousePosX, mousePosY,
					partialTick);
	}

	protected void setTab(int index) {
		if (index >= 0 && index < tabList.size()) {
			tabIndex = index;
		}
		@SuppressWarnings("unchecked")
		List<Slot> slots = inventorySlots.inventorySlots;
		for (Slot slot : slots) {
			if (index >= 0 && index < tabList.size()
					&& tabList.get(tabIndex).containsSlot(slot)) {
				slot.xDisplayPosition = Math.abs(slot.xDisplayPosition);
				slot.yDisplayPosition = Math.abs(slot.yDisplayPosition);
			} else {
				slot.xDisplayPosition = -Math.abs(slot.xDisplayPosition);
				slot.yDisplayPosition = -Math.abs(slot.yDisplayPosition);
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
					&& mouseY >= guiTop + 5 + i * diff
					&& mouseY <= guiTop + 5 + i * diff + tabHeight) {
				setTab(i);
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if (tabIndex >= 0 && tabIndex < tabList.size())
			tabList.get(tabIndex).handleClick(mouseX - guiLeft,
					mouseY - guiTop, mouseButton);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
		Tessellator.instance.addTranslation(0, 0, 1f);
		this.mc.getTextureManager().bindTexture(
				new ResourceLocation(MHFCReference.gui_hunterbench_back_tex));
		int posX = (this.width - this.xSize) / 2;
		int posY = (this.height - this.ySize) / 2;

		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(posX, posY, this.zLevel, 0, 0);
		tess.addVertexWithUV(posX, posY + ySize, this.zLevel, 0, 1);
		tess.addVertexWithUV(posX + xSize, posY + ySize, this.zLevel, 1, 1);
		tess.addVertexWithUV(posX + xSize, posY, this.zLevel, 1, 0);
		tess.draw();

		Tessellator.instance.addTranslation(0, 0, -1f);
	}
}
