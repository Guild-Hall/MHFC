package mhfc.net.client.gui.quests;

import java.util.HashMap;
import java.util.Map;

import mhfc.net.client.gui.ClickableGuiList;
import mhfc.net.client.gui.ClickableGuiList.GuiListStringItem;
import mhfc.net.client.gui.IMHFCTab;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.packet.MessageMHFCInteraction;
import mhfc.net.common.network.packet.MessageMHFCInteraction.Interaction;
import mhfc.net.common.quests.IVisualInformation;
import mhfc.net.common.util.gui.MHFCGuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class GuiQuestJoin extends GuiScreen implements IMHFCTab {

	private static final int buttonHeight = 20;
	private static final int yBorder = 15;
	private int runningW = 70, runningX = 15;
	private ClickableGuiList<GuiListStringItem> runningQuestList;
	private Map<String, GuiListStringItem> mapToListItems;
	private Map<GuiListStringItem, String> mapToIdentifiers;
	private GuiButton joinQuest;
	private int xPos, yPos;
	private int xSize, ySize;
	private int page;
	private boolean clickHandled;

	public GuiQuestJoin(EntityPlayer accessor) {
		this.xSize = 374;
		this.ySize = 220;
		runningQuestList = new ClickableGuiList<GuiListStringItem>(width, height);
		runningQuestList.setDrawSmallestBounds(false);
		runningQuestList.setRecalculateItemHeightOnDraw(false);
		runningQuestList.setItemWidth(22);

		runningQuestList.setPosition(5 + xPos, 20 + yPos);
		runningQuestList.setWidthAndHeight(70, ySize - 30);

		mapToListItems = new HashMap<String, GuiListStringItem>();
		mapToIdentifiers = new HashMap<GuiListStringItem, String>();
		page = 0;
		joinQuest = new GuiButton(0, 25, 10, 185 + yPos, 20, "Take Quest") {
			@Override
			public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
				if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
					clickHandled = true;
					GuiListStringItem selectedItem = runningQuestList.getSelectedItem();
					if (selectedItem != null) {
						String questID = mapToIdentifiers.get(selectedItem);
						if (questID == null)
							return true;
						PacketPipeline.networkPipe
								.sendToServer(new MessageMHFCInteraction(Interaction.ACCEPT_QUEST, questID));
					}
					return true;
				}
				return false;
			}
		};

	}

	@Override
	public void updateTab(int posX, int posY) {
		this.xPos = posX;
		this.yPos = posY;

		runningQuestList.setPosition(runningX, yBorder + 10);
		runningQuestList.setWidthAndHeight(runningW, ySize - 2 * yBorder - 10);

		joinQuest.xPosition = (xSize - runningX - runningW - joinQuest.getButtonWidth()) / 2 + runningX + runningW
				+ xPos;
		joinQuest.yPosition = ySize - yBorder - buttonHeight + yPos;
	}

	@Override
	public void updateScreen() {
		xPos = (width - xSize) / 2;
		yPos = (height - ySize) / 2;
		updateTab(xPos, yPos);

	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick) {
		fontRendererObj.drawString("Currently running:", xPos + 9, yPos + yBorder, MHFCGuiUtil.COLOUR_TEXT);
		runningQuestList.setVisible(true);
		runningQuestList.draw(xPos, yPos, mouseX - xPos, mouseY - yPos);
		joinQuest.visible = true;
		joinQuest.enabled = MHFCRegQuestVisual.hasPlayerQuest();
		GuiListStringItem item = runningQuestList.getSelectedItem();
		if (item != null) {
			String id = mapToIdentifiers.get(item);
			IVisualInformation info = MHFCRegQuestVisual.getQuestVisualInformation(id);
			if (info != null)
				info.drawInformation(
						xPos + runningW + runningX,
						yPos + yBorder,
						xSize - runningW - 2 * runningX,
						ySize - 2 * yBorder,
						page,
						fontRendererObj);
		}

		super.drawScreen(mouseX, mouseY, partialTick);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		clickHandled = false;
		super.mouseClicked(mouseX, mouseY, button);
		if (runningQuestList.handleClick(
				mouseX - xPos - runningQuestList.getPosX(),
				mouseY - yPos - runningQuestList.getPosY(),
				button)) {

		} else if (!MHFCRegQuestVisual.hasPlayerQuest() // Is an info displayed
				&& mouseX > xPos + 80 && mouseX < xPos + 300 // x check
				&& mouseY > yPos && mouseY < yPos + ySize - 30) {
			if (!clickHandled) {
				clickHandled = true;
				int add = button == 0 ? 1 : button == 1 ? -1 : 0;
				page += add;
				page = (page + 3) % 3;
			}
		}
	}

	@Override
	public void drawBackground(int p_146278_1_) {
		mc.getTextureManager().bindTexture(MHFCRegQuestVisual.QUEST_BOARD_BACKGROUND);
		MHFCGuiUtil.drawTexturedBoxFromBorder(xPos, yPos, this.zLevel, this.xSize, this.ySize, 0, 0, 1f, 1f);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		xPos = (MHFCGuiUtil.minecraftWidth(mc) - xSize) / 2;
		yPos = (MHFCGuiUtil.minecraftHeight(mc) - ySize) / 2;
		super.initGui();
		buttonList.add(joinQuest);
		updateScreen();
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
	}

	public void addQuest(String id, IVisualInformation info) {
		GuiListStringItem item = new GuiListStringItem(info.getName());
		if (!mapToListItems.containsKey(id) && item != null) {
			mapToListItems.put(id, item);
			mapToIdentifiers.put(item, id);
			runningQuestList.add(item);
		} else {}
	}

	public void removeQuest(String id) {
		GuiListStringItem item = mapToListItems.get(id);
		runningQuestList.remove(item);
		mapToListItems.remove(id);
		if (item != null)
			mapToIdentifiers.remove(item);
	}

	public void clearList() {
		runningQuestList.clear();
		mapToListItems.clear();
	}

	@Override
	public void drawTab(int posX, int posY, int mousePosX, int mousePosY, float partialTick) {
		this.xPos = posX;
		this.yPos = posY;
		updateTab(xPos, yPos);
		drawBackground(0);
		drawScreen(mousePosX + xPos, mousePosY + yPos, partialTick);
	}

	@Override
	public boolean handleClick(int relativeX, int relativeY, int button) {
		mouseClicked(relativeX + xPos, relativeY + yPos, button);
		return true;
	}

	@Override
	public boolean containsSlot(Slot slot) {
		return false;
	}

	@Override
	public void onClose() {}

	@Override
	public void onOpen() {}

	@Override
	public void handleMovementMouseDown(int mouseX, int mouseY, int button, long timeDiff) {
		mouseClickMove(mouseX + xPos, mouseY + yPos, button, timeDiff);
	}

	@Override
	public void handleMouseUp(int mouseX, int mouseY, int id) {
		mouseMovedOrUp(mouseX + xPos, mouseY + yPos, id);
	}

	@Override
	public void handleMovement(int mouseX, int mouseY) {}
}
