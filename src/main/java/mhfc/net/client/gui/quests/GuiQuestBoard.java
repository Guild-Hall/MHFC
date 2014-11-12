package mhfc.net.client.gui.quests;

import java.util.HashMap;
import java.util.Map;

import mhfc.net.client.gui.ClickableGuiList;
import mhfc.net.client.gui.ClickableGuiList.GuiListStringItem;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.core.registry.MHFCRegQuests;
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

	private ClickableGuiList<GuiListStringItem> runningQuestList;
	private Map<String, GuiListStringItem> mapToListItems;
	private GuiButton cancelQuest, startQuest;
	private int xPos, yPos;
	private int xSize, ySize;
	private int page;
	private EntityPlayer accessor;
	private boolean clickHandled;

	public GuiQuestBoard(EntityPlayer accessor) {
		this.accessor = accessor;
		this.xSize = 320;
		this.ySize = 220;
		runningQuestList = new ClickableGuiList<GuiListStringItem>(width,
				height);
		mapToListItems = new HashMap<String, GuiListStringItem>();
		page = 0;
		cancelQuest = new GuiButton(0, 25, 10, 120, 20, "Cancel current Quest") {
			@Override
			public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_,
					int p_146116_3_) {
				if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
					clickHandled = true;
					runningQuestList.setVisible(true);
					MHFCRegQuests.networkWrapper
							.sendToServer(new MessageQuestInteraction(
									Interaction.GIVE_UP,
									GuiQuestBoard.this.accessor, new String[0]));
					return true;
				}
				return false;
			}
		};
		startQuest = new GuiButton(0, 25, 10, 120, 20, "Start current Quest") {
			@Override
			public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_,
					int p_146116_3_) {
				if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
					clickHandled = true;
					runningQuestList.setVisible(true);
					MHFCRegQuests.networkWrapper
							.sendToServer(new MessageQuestInteraction(
									Interaction.VOTE_START,
									GuiQuestBoard.this.accessor, new String[0]));
					GuiQuestBoard.this.accessor.closeScreen();
					return true;
				}
				return false;
			}
		};
	}

	@Override
	public void updateScreen() {
		ScaledResolution s = new ScaledResolution(mc, mc.displayWidth,
				mc.displayHeight);
		xPos = (s.getScaledWidth() - xSize) / 2;
		yPos = (s.getScaledHeight() - ySize) / 2;

		runningQuestList.setPosition(5 + xPos, 5 + yPos);
		runningQuestList.setWidthAndHeight(70, ySize - 10);

		cancelQuest.xPosition = xPos + xSize / 2 - cancelQuest.width / 2;
		cancelQuest.yPosition = yPos + ySize / 2 + 5;
		startQuest.xPosition = xPos + xSize / 2 - startQuest.width / 2;
		startQuest.yPosition = yPos + ySize / 2 - startQuest.height - 5;
	}

	public boolean doesGuiPauseScreen() {
		return false;
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
		MHFCGuiUtil.drawTexturedBoxFromBorder(xPos, yPos, 0f, xSize, ySize, 15,
				15 / 265f, 1, 166f / 256f);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.accessor = mc.thePlayer;
		MHFCRegQuestVisual.setAndSendRunningListenStatus(true, accessor);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		MHFCRegQuestVisual.setAndSendRunningListenStatus(false, accessor);
	}

	public void addQuest(String id, QuestRunningInformation info) {
		GuiListStringItem item = new GuiListStringItem(info.getName());
		if (!mapToListItems.containsKey(id)) {
			mapToListItems.put(id, item);
			runningQuestList.add(item);
		} else {
		}
	}

	public void removeQuest(String id) {
		runningQuestList.remove(mapToListItems.get(id));
		mapToListItems.remove(id);
	}

	public void clearList() {
		runningQuestList.clear();
		mapToListItems.clear();
	}
}
