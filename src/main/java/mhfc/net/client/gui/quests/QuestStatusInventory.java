package mhfc.net.client.gui.quests;

import java.util.Optional;

import mhfc.net.client.container.ContainerQuestStatus;
import mhfc.net.client.gui.hud.QuestStatusDisplay;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.client.quests.api.IMissionInformation;
import mhfc.net.client.util.gui.MHFCGuiUtil;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.util.stringview.Viewable;
import mhfc.net.common.util.stringview.Viewables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;

public class QuestStatusInventory extends GuiContainer {

	private final StringBuilder viewBuffer = new StringBuilder();
	private int displayPage;
	private EntityPlayer player;

	public QuestStatusInventory(EntityPlayer player) {
		super(new ContainerQuestStatus());
		this.player = player;
		displayPage = 0;
	}

	@Override
	public void initGui() {
		this.xSize = 200;
		this.ySize = 188;
		this.buttonList.add(new GuiButton(2, 0, 0, 100, 20, "Inventory screen") {
			@Override
			public boolean mousePressed(Minecraft mc, int p_146116_2_, int p_146116_3_) {
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
		return (k > guiLeft && k < guiLeft + xSize && l > guiTop && l < guiTop + ySize);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		if (isMouseOverInfo(mouseX, mouseY)) {
			displayPage += button == 0 ? 1 : -1;
		} else {
			super.mouseClicked(mouseX, mouseY, button);
		}
	}

	private static final Viewable statusHeader = Viewables
			.parse("§4§n[[" + MHFCReference.unlocalized_tag_status_long + "]]§r\n\n", null);

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(QuestStatusDisplay.QUEST_STATUS_INVENTORY_BACKGROUND);
		MHFCGuiUtil.drawTexturedBoxFromBorder(
				this.guiLeft,
				this.guiTop,
				this.zLevel,
				this.xSize,
				this.ySize,
				0,
				0,
				1f,
				0.65f);
		Optional<IMissionInformation> optionalInfo = MHFCRegQuestVisual.getPlayerVisual();
		if (!optionalInfo.isPresent()) {
			String drawn = "No quest accepted";
			int stringPosY = (ySize - mc.fontRenderer.FONT_HEIGHT) / 2,
					stringPosX = (xSize - mc.fontRenderer.getStringWidth(drawn)) / 2;
			mc.fontRenderer.drawString(drawn, guiLeft + stringPosX, guiTop + stringPosY, MHFCGuiUtil.COLOUR_TITLE);
			return;
		}
		IMissionInformation information = optionalInfo.get();
		int pageCount = information.getPageCount();
		int margin = 5;
		Viewable infoSummary = statusHeader.concat(information.getView());
		MHFCGuiUtil.drawViewable(
				infoSummary,
				viewBuffer,
				displayPage % pageCount,
				0,
				xSize - margin * 2,
				ySize - margin * 2,
				guiLeft + margin,
				guiTop + margin,
				mc.fontRenderer.FONT_HEIGHT + 2,
				MHFCGuiUtil.COLOUR_TEXT,
				mc.fontRenderer);
	}

}
