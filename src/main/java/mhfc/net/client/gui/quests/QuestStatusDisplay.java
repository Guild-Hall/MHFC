package mhfc.net.client.gui.quests;

import static mhfc.net.common.util.gui.MHFCGuiUtil.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mhfc.net.MHFCMain;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.quests.QuestRunningInformation;
import mhfc.net.common.util.gui.MHFCGuiUtil;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class QuestStatusDisplay extends Gui {

	private Minecraft mc = Minecraft.getMinecraft();

	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public void onInventoryOpened(InitGuiEvent.Post event) {
		if (event.gui instanceof GuiInventory) {
			event.buttonList.add(new GuiButton(2, 0, 0, 80, 20,
				"Quest screen") {
				@Override
				public boolean mousePressed(Minecraft mc, int p_146116_2_,
					int p_146116_3_) {
					if (super.mousePressed(mc, p_146116_2_, p_146116_3_)) {
						mc.thePlayer.openGui(MHFCMain.instance,
							MHFCReference.gui_queststatus_id, mc.theWorld, 0, 0,
							0);
						return true;
					}
					return false;
				}
			});
		}
	}

	@SubscribeEvent
	public void onDraw(RenderGameOverlayEvent.Post overlayEvent) {
		QuestRunningInformation information = MHFCRegQuestVisual
			.getPlayerVisual();
		if (overlayEvent.type == ElementType.FOOD && information != null) {
			mc.getTextureManager().bindTexture(
				MHFCRegQuestVisual.QUEST_STATUS_ONSCREEN_BACKGROUND);
			GL11.glEnable(GL11.GL_BLEND);
			int width = 85;
			int height = 200;
			GL14.glBlendColor(0.0f, 0.0f, 0.0f, 0.6f);
			GL11.glBlendFunc(GL11.GL_CONSTANT_ALPHA,
				GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
			int posX = MHFCGuiUtil.minecraftWidth(mc) - width,
				posY = (MHFCGuiUtil.minecraftHeight(mc) - height) / 2;
			MHFCGuiUtil.drawTexturedBoxFromBorder(posX, posY, this.zLevel,
				width, height, 40, 30f / 256, 248f / 256, 166f / 256);
			GL11.glDisable(GL11.GL_BLEND);
			String localizedStat = StatCollector.translateToLocal(
				MHFCReference.unlocalized_tag_status_short);
			mc.fontRenderer.drawString(localizedStat, posX + 5, posY + 5,
				0x804040);
			int lineHeight = mc.fontRenderer.FONT_HEIGHT + 2;
			mc.fontRenderer.drawSplitString(information.getShortStatus(), posX
				+ 5, posY + lineHeight + 5, width - 10, COLOUR_TEXT);
		}
	}

}
