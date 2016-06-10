package mhfc.net.client.gui.quests;

import static mhfc.net.common.util.gui.MHFCGuiUtil.*;

import mhfc.net.client.container.ContainerQuestStatus;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.quests.IVisualInformation;
import mhfc.net.common.util.gui.MHFCGuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;

public class QuestStatusInventory extends GuiContainer {

	private int displayPage;
	private EntityPlayer player;

	public QuestStatusInventory(EntityPlayer player) {
		super(new ContainerQuestStatus());
		this.player = player;
		displayPage = 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		this.xSize = 200;
		this.ySize = 188;
		this.buttonList.add(new GuiButton(2, 0, 0, 100, 20,
			"Inventory screen") {
			@Override
			public boolean mousePressed(Minecraft mc, int p_146116_2_,
				int p_146116_3_) {
				if (super.mousePressed(mc, p_146116_2_, p_146116_3_)) {
					mc.displayGuiScreen(new GuiInventory(player));
				}
				return false;
			}
		});
		super.initGui();
	}

	private boolean isMouseOverInfo(int positionX, int positionY) {
		final int k = positionX;
		final int l = positionY;
		return (k > guiLeft && k < guiLeft + xSize && l > guiTop && l < guiTop
			+ ySize);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		if (isMouseOverInfo(mouseX, mouseY)) {
			displayPage += button == 0 ? 1 : -1;
		} else {
			super.mouseClicked(mouseX, mouseY, button);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_,
		int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(
			MHFCRegQuestVisual.QUEST_STATUS_INVENTORY_BACKGROUND);
		MHFCGuiUtil.drawTexturedBoxFromBorder(this.guiLeft, this.guiTop,
			this.zLevel, this.xSize, this.ySize, 0, 0, 1f, 0.65f);
		IVisualInformation information = MHFCRegQuestVisual.getPlayerVisual();
		if (information == null) {
			String drawn = "No quest accepted";
			int stringPosY = (ySize - mc.fontRenderer.FONT_HEIGHT) / 2,
				stringPosX = (xSize - mc.fontRenderer.getStringWidth(drawn))
					/ 2;
			mc.fontRenderer.drawString(drawn, guiLeft + stringPosX, guiTop
				+ stringPosY, COLOUR_TITLE);
		} else {
			information.drawInformation(guiLeft, guiTop, xSize, ySize,
				displayPage, mc.fontRenderer);
		}
	}

}
