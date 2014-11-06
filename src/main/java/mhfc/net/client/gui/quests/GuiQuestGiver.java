package mhfc.net.client.gui.quests;

import java.util.ArrayList;
import java.util.List;

import mhfc.net.client.gui.ClickableGuiList;
import mhfc.net.client.gui.ClickableGuiList.GuiListStringItem;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.core.registry.MHFCRegQuests;
import mhfc.net.common.network.packet.MessageQuestInteraction;
import mhfc.net.common.network.packet.MessageQuestInteraction.Interaction;
import mhfc.net.common.quests.QuestVisualInformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

public class GuiQuestGiver extends GuiScreen {

	private List<String> groupIDsDisplayed;
	private ClickableGuiList<GuiListStringItem> groupList;
	private GuiButton start, left, right;
	private GuiButton cancel;
	private List<String> questIdentifiers;
	private int selectedIdentifier;
	private int xPos, yPos;
	private int xSize, ySize;
	private final EntityPlayer accessor;

	public GuiQuestGiver(String[] groupIDs, EntityPlayer accessor) {
		groupIDsDisplayed = new ArrayList<String>();
		questIdentifiers = new ArrayList<String>();
		for (int i = 0; i < groupIDs.length; i++)
			groupIDsDisplayed.add(groupIDs[i]);
		groupList = new ClickableGuiList<ClickableGuiList.GuiListStringItem>(
				width, height);
		for (int i = 0; i < groupIDs.length; i++)
			groupList.add(new GuiListStringItem(groupIDs[i]));
		start = new GuiButton(0, 25, 10, 60, 20, "Take Quest") {
			@Override
			public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_,
					int p_146116_3_) {
				if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
					groupList.setVisible(false);
					if (questIdentifiers != null && selectedIdentifier >= 0
							&& selectedIdentifier < questIdentifiers.size()) {
						String questID = questIdentifiers
								.get(selectedIdentifier);
						MHFCRegQuests.networkWrapper
								.sendToServer(new MessageQuestInteraction(
										Interaction.START_NEW,
										GuiQuestGiver.this.accessor, questID));
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
					setIdentifier(selectedIdentifier + 1);
					return true;
				}
				return false;
			}
		};
		cancel = new GuiButton(0, 25, 10, 60, 20, "Cancel current Quest") {
			@Override
			public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_,
					int p_146116_3_) {
				if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
					groupList.setVisible(true);
					MHFCRegQuests.networkWrapper
							.sendToServer(new MessageQuestInteraction(
									Interaction.GIVE_UP,
									GuiQuestGiver.this.accessor, new String[0]));
					GuiQuestGiver.this.accessor.closeScreen();
					return true;
				}
				return false;
			}
		};
		this.width = 220;
		this.height = 220;
		this.xSize = 320;
		this.ySize = 220;
		this.accessor = accessor;
	}

	protected void setIdentifier(int i) {
		selectedIdentifier = Math.max(Math.min(questIdentifiers.size() - 1, i),
				0);
	}

	@Override
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
		super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		if (groupList.handleClick(p_73864_1_ - groupList.getPosX(), p_73864_2_
				- groupList.getPosY(), p_73864_3_)) {
			GuiListStringItem item = groupList.getSelectedItem();
			String selectedList = item == null ? "" : item
					.getInitializationString();
			questIdentifiers.clear();
			List<String> newIdentifiers = MHFCRegQuestVisual
					.getIdentifierList(selectedList);
			if (newIdentifiers != null)
				questIdentifiers.addAll(newIdentifiers);
			selectedIdentifier = questIdentifiers.size() > 0 ? 0 : -1;
		}
	}

	@Override
	public void drawScreen(int positionX, int positionY, float partialTick) {
		drawWorldBackground(0);
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 0, 0.0f);
		boolean hasQuest = MHFCRegQuestVisual.hasPlayerQuest();
		groupList.setVisible(!hasQuest);
		if (!hasQuest) {
			groupList.draw();
			left.visible = true;
			right.visible = true;
			start.enabled = false;
			start.visible = true;
			cancel.visible = false;
			left.enabled = selectedIdentifier > 0;
			right.enabled = questIdentifiers != null
					&& selectedIdentifier < questIdentifiers.size() - 1;
			if (!(questIdentifiers == null || selectedIdentifier < 0 || selectedIdentifier >= questIdentifiers
					.size())) {
				QuestVisualInformation info = MHFCRegQuestVisual
						.getVisualInformation(questIdentifiers
								.get(selectedIdentifier));
				start.enabled = true;
				// TODO set start enabled based on can join
				FontRenderer fontRenderer = mc.fontRenderer;
				int page = 0;
				if (info != null)
					info.drawInformation(xPos + 80, yPos, 220, ySize - 30,
							page, fontRenderer);
			}
		} else {
			// The player already has a quest, give him a cancel option
			start.visible = false;
			start.enabled = false;
			left.visible = false;
			right.visible = false;
			cancel.visible = true;
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
		buttonList.add(start);
		buttonList.add(left);
		buttonList.add(right);
		buttonList.add(cancel);
		updateScreen();
		// super.initGui();
	}

	@Override
	public void updateScreen() {
		ScaledResolution s = new ScaledResolution(mc, mc.displayWidth,
				mc.displayHeight);
		xPos = (s.getScaledWidth() - xSize) / 2;
		yPos = (s.getScaledHeight() - ySize) / 2;
		groupList.setPosition(5 + xPos, 5 + yPos);
		groupList.setWidthAndHeight(70, ySize - 10);
		right.xPosition = 280 + xPos;
		right.yPosition = 5 + yPos;
		left.xPosition = 80 + xPos;
		left.yPosition = 5 + yPos;
		start.xPosition = 160 + xPos;
		start.yPosition = 195 + yPos;
		cancel.xPosition = xPos + xSize / 2 - cancel.width / 2;
		cancel.yPosition = yPos + ySize / 2 - cancel.height / 2;
		super.updateScreen();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
