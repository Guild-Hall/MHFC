package mhfc.net.client.gui.hud;

import static mhfc.net.client.util.gui.MHFCGuiUtil.COLOUR_TEXT;

import java.util.Optional;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import mhfc.net.MHFCMain;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.client.quests.api.IMissionInformation;
import mhfc.net.client.util.gui.MHFCGuiUtil;
import mhfc.net.common.core.registry.MHFCContainerRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.util.stringview.Viewable;
import mhfc.net.common.util.stringview.Viewables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class QuestStatusDisplay {

	private static final Minecraft mc = Minecraft.getMinecraft();
	public static final ResourceLocation QUEST_STATUS_INVENTORY_BACKGROUND = new ResourceLocation(
			ResourceInterface.gui_status_inventory_tex);
	public static final ResourceLocation QUEST_STATUS_ONSCREEN_BACKGROUND = new ResourceLocation(
			ResourceInterface.gui_status_onscreen_tex);

	private static final StringBuilder buffer = new StringBuilder();

	@SubscribeEvent
	public void onInventoryOpened(InitGuiEvent.Post event) {
		if (event.getGui() instanceof GuiInventory) {
			event.getButtonList().add(new GuiButton(2, 0, 0, 80, 20, "Quest screen") {
				@Override
				public boolean mousePressed(Minecraft mc, int p_146116_2_, int p_146116_3_) {
					if (super.mousePressed(mc, p_146116_2_, p_146116_3_)) {
						mc.player.openGui(
								MHFCMain.instance(),
								MHFCContainerRegistry.gui_queststatus_id,
								mc.world,
								0,
								0,
								0);
						return true;
					}
					return false;
				}
			});
		}
	}

	private static Viewable shortStatusHeader = Viewables
			.parse("§4[[" + ResourceInterface.unlocalized_tag_status_short + "]]§4\n\n", null);

	@SubscribeEvent
	public void onDraw(RenderGameOverlayEvent.Post overlayEvent) {
		Optional<IMissionInformation> playerInformation = MHFCRegQuestVisual.getPlayerVisual();
		if (overlayEvent.getType() != ElementType.FOOD || !playerInformation.isPresent()) {
			return;
		}
		IMissionInformation activeInformation = playerInformation.get();

		mc.getTextureManager().bindTexture(QuestStatusDisplay.QUEST_STATUS_ONSCREEN_BACKGROUND);

		GL11.glEnable(GL11.GL_BLEND);
		int width = 85;
		int height = 200;
		GL14.glBlendColor(0.0f, 0.0f, 0.0f, 0.6f);
		GL11.glBlendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
		int posX = MHFCGuiUtil.minecraftWidth(mc) - width, posY = (MHFCGuiUtil.minecraftHeight(mc) - height) / 2;
		MHFCGuiUtil.drawTexturedBoxFromBorder(posX, posY, 0, width, height, 40, 30f / 256, 248f / 256, 166f / 256);
		GL11.glDisable(GL11.GL_BLEND);

		int lineHeight = mc.fontRenderer.FONT_HEIGHT + 2;

		Viewable shortMissionStatus = activeInformation.getShortStatus();
		Viewable shortStatus = shortStatusHeader.concat(shortMissionStatus);
		MHFCGuiUtil.drawViewable(
				shortStatus,
				buffer,
				0,
				0,
				width - 10,
				height,
				posX + 5,
				posY + 5,
				lineHeight,
				COLOUR_TEXT,
				mc.fontRenderer);
	}

}
