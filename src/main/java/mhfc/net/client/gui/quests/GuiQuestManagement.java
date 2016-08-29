package mhfc.net.client.gui.quests;

import java.util.List;

import mhfc.net.client.gui.IMHFCGuiItem;
import mhfc.net.client.gui.IMHFCTab;
import mhfc.net.client.gui.MHFCGui;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.client.util.gui.MHFCGuiUtil;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.quest.MessageMHFCInteraction;
import mhfc.net.common.network.message.quest.MessageMHFCInteraction.Interaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class GuiQuestManagement extends MHFCGui implements IMHFCTab {

	private static final int startQuestHeight = 20;
	private static final int startQuestWidth = 120;
	private List<Slot> chestSlots;
	private int xSize, ySize;
	// TODO Change functionality of start button to a switch to vote that
	// actually does not just asume stuff
	private GuiButton cancelQuest, startQuest;
	private final EntityPlayer accessor;

	public GuiQuestManagement(List<Slot> chestSlots, EntityPlayer player) {
		this.accessor = player;
		this.chestSlots = chestSlots;
		this.xSize = 374;
		this.ySize = 220;
		cancelQuest = new GuiButton(0, 25, 10, 120, 20, "Cancel current Quest") {
			@Override
			public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
				if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
					PacketPipeline.networkPipe
							.sendToServer(new MessageMHFCInteraction(Interaction.FORFEIT_QUEST, new String[0]));
					return true;
				}
				return false;
			}
		};
		startQuest = new GuiButton(0, 25, 10, startQuestWidth, startQuestHeight, "Set ready status") {
			// FIXME this assumes that have voted when you have a quest
			boolean voted = MHFCRegQuestVisual.hasPlayerQuest();

			@Override
			public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
				if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
					if (!voted) {
						PacketPipeline.networkPipe
								.sendToServer(new MessageMHFCInteraction(Interaction.START_QUEST, new String[0]));
						accessor.closeScreen();
						voted = true;
					} else {
						PacketPipeline.networkPipe
								.sendToServer(new MessageMHFCInteraction(Interaction.END_QUEST, new String[0]));
						voted = false;
					}
					return true;
				}
				return false;
			}

			@Override
			public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
				this.displayString = voted ? "Unset ready status" : "Set ready status";
				super.drawButton(p_146112_1_, p_146112_2_, p_146112_3_);
			}
		};
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		buttonList.add(cancelQuest);
		buttonList.add(startQuest);
		super.initGui();
	}

	@Override
	public void drawBackground(int p_146278_1_) {
		mc.getTextureManager().bindTexture(MHFCRegQuestVisual.QUEST_BOARD_BACKGROUND);
		MHFCGuiUtil.drawTexturedBoxFromBorder(0, 0, this.zLevel, this.xSize, this.ySize, 0, 0, 1f, 1f);
	}

	@Override
	public void draw(double mousePosX, double mousePosY, float partialTick) {
		updateScreen();
		drawBackground(0);
		String warning = "You are already on a quest";
		int warnX = (xSize - fontRendererObj.getStringWidth(warning)) / 2, warnY = 60;
		fontRendererObj.drawString(warning, warnX, warnY, MHFCGuiUtil.COLOUR_TEXT);
		super.draw(mousePosX, mousePosY, partialTick);
	}

	@Override
	public boolean containsSlot(Slot slot) {
		if (slot.inventory instanceof InventoryPlayer)
			return true;
		return chestSlots == null ? false : chestSlots.contains(slot);
	}

	@Override
	public void updateTab() {
		cancelQuest.xPosition = xSize / 2 - cancelQuest.getButtonWidth() / 2;
		cancelQuest.yPosition = ySize / 2 + 5;
		startQuest.xPosition = xSize / 2 - startQuest.getButtonWidth() / 2;
		startQuest.yPosition = ySize / 2 - startQuestHeight - 10;
	}

	@Override
	public void updateScreen() {
		updateTab();
	}

	@Override
	public void onClose() {
		super.onGuiClosed();
	}

	@Override
	public void onOpen() {
		super.initGui();
	}

	@Override
	protected void itemUpdated(IMHFCGuiItem item) {}

}
