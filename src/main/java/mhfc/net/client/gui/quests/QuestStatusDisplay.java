package mhfc.net.client.gui.quests;

import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.quests.QuestRunningInformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class QuestStatusDisplay extends Gui {

	private int[] xTransformsFromScale = {50, 100, 200, 240};
	private int[] yTransformsFromScale = {40, 40, 40, 40};
	private int[] widthFromScale = {40, 40, 40, 40};
	private int[] heightFromScale = {40, 40, 40, 40};

	private Minecraft mc = Minecraft.getMinecraft();

	protected QuestRunningInformation information;

	@SubscribeEvent
	public void onDrawOfGUI(DrawScreenEvent.Post screenEvent) {
		if (screenEvent.gui instanceof GuiInventory
				&& screenEvent instanceof DrawScreenEvent.Post) {
			mc.getTextureManager().bindTexture(
					MHFCRegQuestVisual.QUEST_STATUS_INVENTORY_BACKGROUND);
			int scale = mc.gameSettings.guiScale & 3;
			int xTrans = xTransformsFromScale[scale];
			int yTrans = yTransformsFromScale[scale];
			int width = widthFromScale[scale];
			int height = heightFromScale[scale];
			this.drawTexturedBoxFromBorder((mc.displayWidth + width) / 4,
					(mc.displayHeight - height) / 4, width + 20, height + 100,
					15);
		}
	}

	@SubscribeEvent
	public void onDraw(RenderGameOverlayEvent.Post overlayEvent) {
		if (overlayEvent.type == ElementType.FOOD) {
			mc.getTextureManager().bindTexture(
					MHFCRegQuestVisual.QUEST_STATUS_ONSCREEN_BACKGROUND);
			ScaledResolution scaledresolution = new ScaledResolution(mc,
					mc.displayWidth, mc.displayHeight);
			int width = scaledresolution.getScaledWidth();
			int height = scaledresolution.getScaledHeight();
			GL11.glEnable(GL11.GL_BLEND);
			GL14.glBlendColor(0.0f, 0.0f, 0.0f, 0.3f);
			GL11.glBlendFunc(GL11.GL_CONSTANT_ALPHA,
					GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
			this.drawTexturedBoxFromBorder(width - 130,
					(mc.displayHeight - 100) / 4, 100, 200, 20);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	private void drawTexturedBoxFromBorder(int x, int y, int width, int height,
			int borderSize) {
		ScaledResolution res = new ScaledResolution(mc, x, y);
		x = res.getScaledWidth();
		y = res.getScaledHeight();
		res = new ScaledResolution(mc, width, height);
		width = res.getScaledWidth();
		height = res.getScaledHeight();
		this.drawTexturedModalRect(x, y, 0, 0, borderSize, borderSize);
		this.drawTexturedModalRect(x + width - borderSize, y, 256 - borderSize,
				0, borderSize, borderSize);
		this.drawTexturedModalRect(x, y + height - borderSize, 0,
				256 - borderSize, borderSize, borderSize);
		this.drawTexturedModalRect(x + width - borderSize, y + height
				- borderSize, 256 - borderSize, 256 - borderSize, borderSize,
				borderSize);
		this.drawTexturedModalRect(x + borderSize, y, borderSize, 0, width
				- borderSize * 2, borderSize);
		this.drawTexturedModalRect(x + borderSize, y + height - borderSize, 0,
				256 - borderSize, width - borderSize * 2, borderSize);
		this.drawTexturedModalRect(x, y + borderSize, 0, borderSize,
				borderSize, height - 2 * borderSize);
		this.drawTexturedModalRect(x + width - borderSize, y + borderSize,
				256 - borderSize, borderSize, borderSize, height - 2
						* borderSize);
		this.drawTexturedModalRect(x + borderSize, y + borderSize, borderSize,
				borderSize, width - 2 * borderSize, height - 2 * borderSize);
	}

	public void setRunningInformation(QuestRunningInformation information) {
		this.information = information;
	}
}
