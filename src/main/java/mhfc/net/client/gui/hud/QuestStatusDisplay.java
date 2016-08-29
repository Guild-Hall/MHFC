package mhfc.net.client.gui.hud;

import static mhfc.net.client.util.gui.MHFCGuiUtil.COLOUR_TEXT;

import java.util.Optional;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.client.quests.api.IMissionInformation;
import mhfc.net.client.util.gui.MHFCGuiUtil;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.util.stringview.Viewable;
import mhfc.net.common.util.stringview.Viewables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

@SideOnly(Side.CLIENT)
public class QuestStatusDisplay {

	private static final Minecraft mc = Minecraft.getMinecraft();
	public static final ResourceLocation QUEST_STATUS_INVENTORY_BACKGROUND = new ResourceLocation(
			MHFCReference.gui_status_inventory_tex);
	public static final ResourceLocation QUEST_STATUS_ONSCREEN_BACKGROUND = new ResourceLocation(
			MHFCReference.gui_status_onscreen_tex);

	private static final StringBuilder buffer = new StringBuilder();

	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public void onInventoryOpened(InitGuiEvent.Post event) {
		if (event.gui instanceof GuiInventory) {
			event.buttonList.add(new GuiButton(2, 0, 0, 80, 20, "Quest screen") {
				@Override
				public boolean mousePressed(Minecraft mc, int p_146116_2_, int p_146116_3_) {
					if (super.mousePressed(mc, p_146116_2_, p_146116_3_)) {
						mc.thePlayer
								.openGui(MHFCMain.instance(), MHFCReference.gui_queststatus_id, mc.theWorld, 0, 0, 0);
						return true;
					}
					return false;
				}
			});
		}
	}

	private static Viewable shortStatusHeader = Viewables
			.parse("§4§n[[" + MHFCReference.unlocalized_tag_status_short + "]]§r\n\n", null);

	@SubscribeEvent
	public void onDraw(RenderGameOverlayEvent.Post overlayEvent) {
		Optional<IMissionInformation> playerInformation = MHFCRegQuestVisual.getPlayerVisual();
		if (overlayEvent.type != ElementType.FOOD || !playerInformation.isPresent()) {
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
