package mhfc.net.client.gui.quests;

import java.util.HashMap;
import java.util.Map;

import mhfc.net.client.gui.ClickableGuiList;
import mhfc.net.client.gui.ClickableGuiList.GuiListStringItem;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.packet.MessageQuestInteraction;
import mhfc.net.common.network.packet.MessageQuestInteraction.Interaction;
import mhfc.net.common.quests.QuestRunningInformation;
import mhfc.net.common.util.gui.MHFCGuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;

public class GuiQuestBoard extends GuiScreen {

	private static final int startQuestWidth = 120;
	private static final int startQuestHeight = 20;
	private static final int yBorder = 15;
	private int runningW = 70, runningX = 15;
	private ClickableGuiList<GuiListStringItem> runningQuestList;
	private Map<String, GuiListStringItem> mapToListItems;
	private Map<GuiListStringItem, String> mapToIdentifiers;
	private GuiButton joinQuest, cancelQuest, startQuest;
	private int xPos, yPos;
	private int xSize, ySize;
	private int page;
	private EntityPlayer accessor;
	private boolean clickHandled;

	public GuiQuestBoard(EntityPlayer accessor) {
		this.accessor = accessor;
		this.xSize = 374;
		this.ySize = 220;
		runningQuestList = new ClickableGuiList<GuiListStringItem>(width,
				height);
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
			public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_,
					int p_146116_3_) {
				if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
					clickHandled = true;
					GuiListStringItem selectedItem = runningQuestList
							.getSelectedItem();
					if (selectedItem != null) {
						String questID = mapToIdentifiers.get(selectedItem);
						runningQuestList.setVisible(false);
						if (questID == null)
							return true;
						PacketPipeline.networkPipe
								.sendToServer(new MessageQuestInteraction(
										Interaction.ACCEPT, questID));
					}
					return true;
				}
				return false;
			}
		};

		cancelQuest = new GuiButton(0, 25, 10, startQuestWidth,
				startQuestHeight, "Cancel current Quest") {
			@Override
			public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_,
					int p_146116_3_) {
				if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
					clickHandled = true;
					runningQuestList.setVisible(true);
					PacketPipeline.networkPipe
							.sendToServer(new MessageQuestInteraction(
									Interaction.GIVE_UP, new String[0]));
					return true;
				}
				return false;
			}
		};

		startQuest = new GuiButton(0, 25, 10, startQuestWidth,
				startQuestHeight, "Start current Quest") {
			@Override
			public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_,
					int p_146116_3_) {
				if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
					clickHandled = true;
					runningQuestList.setVisible(true);
					PacketPipeline.networkPipe
							.sendToServer(new MessageQuestInteraction(
									Interaction.VOTE_START, new String[0]));
					GuiQuestBoard.this.accessor.closeScreen();
					return true;
				}
				return false;
			}
		};
	}

	@Override
	public void updateScreen() {
		ScaledResolution s = new ScaledResolution(mc.gameSettings,
				mc.displayWidth, mc.displayHeight);
		xPos = (s.getScaledWidth() - xSize) / 2;
		yPos = (s.getScaledHeight() - ySize) / 2;

		runningQuestList.setPosition(runningX + xPos, yBorder + 10 + yPos);
		runningQuestList.setWidthAndHeight(runningW, ySize - 2 * yBorder - 10);

		joinQuest.xPosition = (xSize - runningX - runningW - joinQuest
				.getButtonWidth()) / 2 + runningX + runningW + xPos;
		joinQuest.yPosition = ySize - yBorder - startQuestHeight + yPos;
		cancelQuest.xPosition = xPos + xSize / 2 - startQuestWidth / 2;
		cancelQuest.yPosition = yPos + ySize / 2 + 5;
		startQuest.xPosition = xPos + xSize / 2 - startQuestWidth / 2;
		startQuest.yPosition = yPos + ySize / 2 - startQuestHeight - 5;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick) {
		drawDefaultBackground();
		drawBackground(0);
		if (!MHFCRegQuestVisual.hasPlayerQuest()) {
			fontRendererObj.drawString("Currently running:", xPos + 9, yPos
					+ yBorder, 0x404040);
			runningQuestList.setVisible(true);
			runningQuestList.draw();
			joinQuest.visible = true;
			cancelQuest.visible = false;
			cancelQuest.enabled = false;
			startQuest.visible = false;
			GuiListStringItem item = runningQuestList.getSelectedItem();
			if (item != null) {
				String id = mapToIdentifiers.get(item);
				QuestRunningInformation info = MHFCRegQuestVisual
						.getRunningInformation(id);
				if (info != null)
					info.drawInformation(xPos + runningW + runningX, yPos,
							xSize - runningW - 2 * runningX, ySize - 30, page,
							fontRendererObj);
			}
		} else {
			joinQuest.visible = false;
			cancelQuest.visible = true;
			cancelQuest.enabled = true;
			startQuest.visible = true;
			runningQuestList.setVisible(false);
			String notice = "You are already on a quest";
			int drawX = (xSize - fontRendererObj.getStringWidth(notice)) / 2;
			fontRendererObj.drawString(notice, drawX + xPos, 60, 0x404040);
		}
		super.drawScreen(mouseX, mouseY, partialTick);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		clickHandled = false;
		super.mouseClicked(mouseX, mouseY, button);
		if (runningQuestList.handleClick(mouseX - runningQuestList.getPosX(),
				mouseY - runningQuestList.getPosY(), button)) {

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
		mc.getTextureManager().bindTexture(
				MHFCRegQuestVisual.QUEST_BOARD_BACKGROUND);
		MHFCGuiUtil.drawTexturedBoxFromBorder(xPos, yPos, 0f, xSize, ySize, 10,
				8f / 512, 8f / 256, 1f, 1f);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		ScaledResolution s = new ScaledResolution(mc.gameSettings,
				mc.displayWidth, mc.displayHeight);
		xPos = (s.getScaledWidth() - xSize) / 2;
		yPos = (s.getScaledHeight() - ySize) / 2;
		super.initGui();
		buttonList.add(joinQuest);
		buttonList.add(cancelQuest);
		buttonList.add(startQuest);
		this.accessor = mc.thePlayer;
		MHFCRegQuestVisual.setAndSendRunningListenStatus(true);
		updateScreen();
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		MHFCRegQuestVisual.setAndSendRunningListenStatus(false);
	}

	public void addQuest(String id, QuestRunningInformation info) {
		GuiListStringItem item = new GuiListStringItem(info.getName());
		if (!mapToListItems.containsKey(id) && item != null) {
			mapToListItems.put(id, item);
			mapToIdentifiers.put(item, id);
			runningQuestList.add(item);
		} else {
		}
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
}
