package mhfc.net.client.gui.quests;

import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Vector2f;

import mhfc.net.client.gui.ClickableGuiList;
import mhfc.net.client.gui.ClickableGuiList.GuiListStringItem;
import mhfc.net.client.gui.IMHFCGuiItem;
import mhfc.net.client.gui.IMHFCTab;
import mhfc.net.client.gui.MHFCGui;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.client.quests.api.IMissionInformation;
import mhfc.net.client.quests.api.IVisualDefinition;
import mhfc.net.client.util.gui.MHFCGuiUtil;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.quest.MessageMHFCInteraction;
import mhfc.net.common.network.message.quest.MessageMHFCInteraction.Interaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class GuiQuestJoin extends MHFCGui implements IMHFCTab {

	private static final int buttonHeight = 20;
	private static final int yBorder = 15;

	private final StringBuilder viewBuffer = new StringBuilder();
	private int runningW = 70, runningX = 15;
	private ClickableGuiList<GuiListStringItem> runningQuestList;
	private Map<String, GuiListStringItem> mapToListItems;
	private Map<GuiListStringItem, String> mapToIdentifiers;
	private GuiButton joinQuest;
	private int xSize, ySize;
	private int page;
	private boolean clickHandled;

	public GuiQuestJoin(EntityPlayer accessor) {
		this.xSize = 374;
		this.ySize = 220;
		runningQuestList = new ClickableGuiList<>(width, height);
		runningQuestList.setDrawSmallestBounds(false);
		runningQuestList.setRecalculateItemHeightOnDraw(false);
		runningQuestList.setItemWidth(22);

		runningQuestList.setWidthAndHeight(70, ySize - 30);
		addScreenComponent(runningQuestList, new Vector2f(5, 20));

		mapToListItems = new HashMap<>();
		mapToIdentifiers = new HashMap<>();
		page = 0;
		joinQuest = new GuiButton(0, 25, 10, 185, 20, "Take Quest") {
			@Override
			public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
				if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
					clickHandled = true;
					GuiListStringItem selectedItem = runningQuestList.getSelectedItem();
					if (selectedItem != null) {
						String questID = mapToIdentifiers.get(selectedItem);
						if (questID == null) {
							return true;
						}
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
	public void updateTab() {
		Vector2f position = getPosition(runningQuestList);
		position.set(runningX, yBorder + 10);
		runningQuestList.setWidthAndHeight(runningW, ySize - 2 * yBorder - 10);

		joinQuest.xPosition = (xSize - runningX - runningW - joinQuest.getButtonWidth()) / 2 + runningX + runningW;
		joinQuest.yPosition = ySize - yBorder - buttonHeight;
		joinQuest.visible = true;
		joinQuest.enabled = MHFCRegQuestVisual.hasPlayerQuest();
	}

	@Override
	public void updateScreen() {
		updateTab();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void drawBackground(int p_146278_1_) {
		mc.getTextureManager().bindTexture(MHFCRegQuestVisual.QUEST_BOARD_BACKGROUND);
		MHFCGuiUtil.drawTexturedBoxFromBorder(0, 0, this.zLevel, this.xSize, this.ySize, 0, 0, 1f, 1f);
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(joinQuest);
		updateScreen();
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
	}

	public void addQuest(String id, IMissionInformation info) {
		if (mapToListItems.containsKey(id)) {
			return;
		}
		GuiListStringItem item = new GuiListStringItem(info.getOriginalDefinition().getDisplayName());
		mapToListItems.put(id, item);
		mapToIdentifiers.put(item, id);
		runningQuestList.add(item);
	}

	public void removeQuest(String id) {
		GuiListStringItem item = mapToListItems.get(id);
		runningQuestList.remove(item);
		mapToListItems.remove(id);
		if (item != null) {
			mapToIdentifiers.remove(item);
		}
	}

	public void clearList() {
		runningQuestList.clear();
		mapToListItems.clear();
		mapToIdentifiers.clear();
	}

	@Override
	public void draw(double mousePosX, double mousePosY, float partialTick) {
		updateTab();
		drawBackground(0);
		super.draw(mousePosX, mousePosY, partialTick);
		fontRendererObj.drawString("Currently running:", 9, yBorder, MHFCGuiUtil.COLOUR_TEXT);
		runningQuestList.setVisible(true);
		GuiListStringItem item = runningQuestList.getSelectedItem();
		if (item == null) {
			return;
		}
		String id = mapToIdentifiers.get(item);
		IMissionInformation missionInfo = MHFCRegQuestVisual.getMissionInformation(id);
		if (missionInfo == null) {
			return;
		}
		IVisualDefinition staticInfo = missionInfo.getOriginalDefinition();
		if (staticInfo == null) {
			return;
		}
		int pageCount = staticInfo.getPageCount();
		staticInfo.drawInformation(
				runningW + runningX,
				yBorder,
				xSize - runningW - 2 * runningX,
				ySize - 2 * yBorder,
				page % pageCount,
				fontRendererObj);
	}

	@Override
	public boolean handleClick(float relativeX, float relativeY, int button) {
		clickHandled = false;
		clickHandled |= super.handleClick(relativeX, relativeY, button);
		if (!MHFCRegQuestVisual.hasPlayerQuest() // Is an info displayed
				&& relativeX > 80 && relativeY < 300 // x check
				&& relativeX > 0 && relativeY < ySize - 30) {
			if (!clickHandled) {
				int add = button == 0 ? 1 : button == 1 ? -1 : 0;
				page += add;
			}
		}
		return clickHandled;
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
	protected void itemUpdated(IMHFCGuiItem item) {}

}
