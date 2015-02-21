package mhfc.net.client.gui.quests;

import java.util.ArrayList;
import java.util.List;

import mhfc.net.client.gui.ClickableGuiList;
import mhfc.net.client.gui.ClickableGuiList.GuiListStringItem;
import mhfc.net.client.gui.IMHFCTab;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.packet.MessageQuestInteraction;
import mhfc.net.common.network.packet.MessageQuestInteraction.Interaction;
import mhfc.net.common.quests.QuestVisualInformation;
import mhfc.net.common.util.gui.MHFCGuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

import org.lwjgl.opengl.GL11;

public class GuiQuestNew extends GuiScreen implements IMHFCTab {

	private static final int buttonHeight = 20;
	private static final int yBorder = 15;
	private int questsW = 70, questsX = 15;
	private List<String> groupIDsDisplayed;
	private ClickableGuiList<GuiListStringItem> groupList;
	private GuiButton newQuest, left, right;
	private List<String> questIdentifiers;
	private int selectedIdentifier;
	private int xPos, yPos;
	private int xSize, ySize;
	private int page = 0;
	// Has the current click been handled by any element so that it should not
	// be handled further
	private boolean handled;

	public GuiQuestNew(String[] groupIDs, EntityPlayer accessor) {
		groupIDsDisplayed = new ArrayList<String>();
		questIdentifiers = new ArrayList<String>();
		for (int i = 0; i < groupIDs.length; i++)
			groupIDsDisplayed.add(groupIDs[i]);
		groupList = new ClickableGuiList<ClickableGuiList.GuiListStringItem>(
				width, height);
		for (int i = 0; i < groupIDs.length; i++)
			groupList.add(new GuiListStringItem(groupIDs[i]));
		newQuest = new GuiButton(0, 25, 10, 60, 20, "Take Quest") {
			@Override
			public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_,
					int p_146116_3_) {
				if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
					handled = true;
					if (questIdentifiers != null && selectedIdentifier >= 0
							&& selectedIdentifier < questIdentifiers.size()) {
						String questID = questIdentifiers
								.get(selectedIdentifier);
						PacketPipeline.networkPipe
								.sendToServer(new MessageQuestInteraction(
										Interaction.START_NEW, questID));
					}
					return true;
				}
				return false;
			}
		};
		left = new GuiButton(1, 10, 10, 20, 20, "<") {
			@Override
			public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_,
					int p_146116_3_) {
				if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
					handled = true;
					setIdentifier(selectedIdentifier - 1);
					return true;
				}
				return false;
			}
		};
		right = new GuiButton(2, 10, 10, 20, 20, ">") {
			@Override
			public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_,
					int p_146116_3_) {
				if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
					handled = true;
					setIdentifier(selectedIdentifier + 1);
					return true;
				}
				return false;
			}
		};

		this.width = 374;
		this.height = 220;
		this.xSize = 374;
		this.ySize = 220;
	}

	protected void setIdentifier(int i) {
		selectedIdentifier = Math.max(Math.min(questIdentifiers.size() - 1, i),
				0);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		handled = false;
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if (groupList.handleClick(mouseX - groupList.getPosX(), mouseY
				- groupList.getPosY(), mouseButton)) {
			handled = true;
			GuiListStringItem item = groupList.getSelectedItem();
			String selectedList = item == null ? "" : item
					.getInitializationString();
			questIdentifiers.clear();
			List<String> newIdentifiers = MHFCRegQuestVisual
					.getIdentifierList(selectedList);
			if (newIdentifiers != null)
				questIdentifiers.addAll(newIdentifiers);
		} else if (questIdentifiers.size() > 0
				&& !MHFCRegQuestVisual.hasPlayerQuest() // Is an info displayed
				&& mouseX > xPos + 80 && mouseX < xPos + 300 // x check
				&& mouseY > yPos && mouseY < yPos + ySize - 30) {
			if (!handled) {
				handled = true;
				int add = mouseButton == 0 ? 1 : mouseButton == 1 ? -1 : 0;
				page += add;
				page = (page + 3) % 3;
			}
		}
	}

	@Override
	public void drawScreen(int positionX, int positionY, float partialTick) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 0, 0.0f);
		// TODO unlocalize
		fontRendererObj.drawString("Take a quest:", xPos + 8, yPos + yBorder,
				0x404040);
		groupList.draw();
		left.visible = true;
		right.visible = true;
		newQuest.enabled = false;
		newQuest.visible = true;
		left.enabled = selectedIdentifier > 0;
		right.enabled = questIdentifiers != null
				&& selectedIdentifier < questIdentifiers.size() - 1;
		if (!(questIdentifiers == null || selectedIdentifier < 0 || selectedIdentifier >= questIdentifiers
				.size())) {
			QuestVisualInformation info = MHFCRegQuestVisual
					.getVisualInformation(questIdentifiers
							.get(selectedIdentifier));
			newQuest.enabled = true;
			// TODO set start enabled based on can join
			FontRenderer fontRenderer = mc.fontRenderer;
			if (info != null)
				info.drawInformation(xPos + questsX + questsW, yPos + yBorder,
						xSize - 2 * questsX - questsW, ySize - 30, page,
						fontRenderer);
		}
		super.drawScreen(positionX, positionY, partialTick);
		GL11.glPopMatrix();
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		buttonList.add(newQuest);
		buttonList.add(left);
		buttonList.add(right);
		updateScreen();
		// super.initGui();
	}

	@Override
	public void updateTab(int posX, int posY) {
		this.xPos = posX;
		this.yPos = posY;
		groupList.setPosition(questsX + xPos, yBorder + 10 + yPos);
		groupList.setWidthAndHeight(questsW, ySize - 2 * yBorder - 10);
		right.xPosition = xSize - questsX - right.getButtonWidth() + xPos;
		right.yPosition = yBorder + yPos;
		left.xPosition = questsX + questsW + 5 + xPos;
		left.yPosition = yBorder + yPos;
		newQuest.xPosition = (xSize - questsX - questsW - newQuest
				.getButtonWidth()) / 2 + questsX + questsW + xPos;
		newQuest.yPosition = ySize - yBorder - buttonHeight + yPos;
	}

	@Override
	public void updateScreen() {
		ScaledResolution s = new ScaledResolution(mc.gameSettings,
				mc.displayWidth, mc.displayHeight);
		xPos = (s.getScaledWidth() - xSize) / 2;
		yPos = (s.getScaledHeight() - ySize) / 2;
		updateTab(xPos, yPos);
		super.updateScreen();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void drawBackground(int p_146278_1_) {
		mc.getTextureManager().bindTexture(
				MHFCRegQuestVisual.QUEST_BOARD_BACKGROUND);
		MHFCGuiUtil.drawTexturedBoxFromBorder(xPos, yPos, 0f, xSize, ySize, 10,
				10 / 512f, 10f / 256, 1, 1);
	}

	@Override
	public void drawTab(int posX, int posY, int mousePosX, int mousePosY,
			float partialTick) {
		this.xPos = posX;
		this.yPos = posY;
		updateScreen();
		drawBackground(0);
		drawScreen(mousePosX, mousePosY, partialTick);
	}

	@Override
	public void handleClick(int relativeX, int relativeY, int button) {
		mouseClicked(relativeX, relativeY, button);
	}

	@Override
	public boolean containsSlot(Slot slot) {
		return false;
	}

	@Override
	public void onClose() {
	}

	@Override
	public void onOpen() {
	}

	@Override
	public void handleMovementMouseDown(int mouseX, int mouseY, int button,
			long timeDiff) {
		mouseClickMove(mouseX, mouseY, button, timeDiff);
	}

	@Override
	public void handleMouseUp(int mouseX, int mouseY, int id) {
		mouseMovedOrUp(mouseX, mouseY, id);
	}

	@Override
	public void handleMovement(int mouseX, int mouseY) {
	}
}
