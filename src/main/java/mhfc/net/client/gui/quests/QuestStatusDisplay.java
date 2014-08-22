package mhfc.net.client.gui.quests;

import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.quests.QuestRunningInformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class QuestStatusDisplay extends Gui {

	private int[] widthFromScale = {140, 140, 140, 120};
	private int[] heightFromScale = {166, 166, 166, 166};
	private Minecraft mc = Minecraft.getMinecraft();

	public QuestStatusDisplay() {
		super();
	}

	protected QuestRunningInformation information;

	@SubscribeEvent
	public void onDrawOfGUI(DrawScreenEvent.Pre screenEvent) {
		if (screenEvent.gui instanceof GuiInventory) {
			mc.getTextureManager().bindTexture(
					MHFCRegQuestVisual.QUEST_STATUS_INVENTORY_BACKGROUND);
			int scale = mc.gameSettings.guiScale & 3;
			if (scale == 0) {
				scale = 3;
			}
			int width = 200;
			int height = heightFromScale[scale];
			// Tessellator.instance.addTranslation(0f, 0f, 0.5f);
			ScaledResolution res = new ScaledResolution(mc, mc.displayWidth,
					mc.displayHeight);
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
			int positionX = (res.getScaledWidth()) / 2 + 96;
			int positionY = (res.getScaledHeight() - height) / 2;
			width = Math.min(width, res.getScaledWidth() - positionX - 10);
			GL11.glTranslatef(0, 0, 0.5f);
			this.drawTexturedBoxFromBorder(positionX, positionY, width, height,
					40, 40f / 256, 248f / 256, 166f / 256);

			if (information == null) {
				String drawn = "No quest accepted";
				int stringPosY = positionY
						+ (height - mc.fontRenderer.FONT_HEIGHT) / 2, stringPosX = positionX
						+ (width - mc.fontRenderer.getStringWidth(drawn)) / 2;
				mc.fontRenderer.drawString(drawn, stringPosX, stringPosY,
						0x000000);
			} else {
				information.drawInformation(positionX, positionY, width,
						height, mc.fontRenderer,
						(int) (System.currentTimeMillis() % Integer.MAX_VALUE));
			}
			GL11.glTranslatef(0, 0, -0.5f);
		}
	}

	@SubscribeEvent
	public void onDraw(RenderGameOverlayEvent.Post overlayEvent) {
		if (overlayEvent.type == ElementType.FOOD && mc.currentScreen == null
				&& information != null) {
			mc.getTextureManager().bindTexture(
					MHFCRegQuestVisual.QUEST_STATUS_ONSCREEN_BACKGROUND);
			GL11.glEnable(GL11.GL_BLEND);
			int width = 85;
			int height = 200;
			GL14.glBlendColor(0.0f, 0.0f, 0.0f, 0.6f);
			GL11.glBlendFunc(GL11.GL_CONSTANT_ALPHA,
					GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
			ScaledResolution res = new ScaledResolution(mc, mc.displayWidth,
					mc.displayHeight);
			int posX = res.getScaledWidth() - width, posY = (res
					.getScaledHeight() - height) / 2;
			this.drawTexturedBoxFromBorder(posX, posY, width, height, 40,
					30f / 256, 248f / 256, 166f / 256);
			GL11.glDisable(GL11.GL_BLEND);
			String localizedStat = StatCollector
					.translateToLocal(MHFCRegQuestVisual.UNLOCALIZED_TAG_STATUS_SHORT);
			mc.fontRenderer.drawString(localizedStat, posX + 5, posY + 5,
					0x804040);
			int lineHeight = mc.fontRenderer.FONT_HEIGHT + 2;
			mc.fontRenderer.drawSplitString(information.getShortStatus(),
					posX + 5, posY + lineHeight + 5, width - 10, 0x404040);
		}
	}

	public void setRunningInformation(QuestRunningInformation information) {
		this.information = information;
	}

	private void drawTexturedBoxFromBorder(int x, int y, int width, int height) {
		this.drawTexturedBoxFromBorder(x, y, width, height,
				Math.min(Math.min(15, width / 2), height / 2));
	}

	private void drawTexturedBoxFromBorder(int x, int y, int width, int height,
			int borderSize) {
		this.drawTexturedBoxFromBorder(x, y, width, height, borderSize,
				borderSize / 256f);
	}

	private void drawTexturedBoxFromBorder(int x, int y, int width, int height,
			int borderSize, float borderUV) {
		this.drawTexturedBoxFromBorder(x, y, width, height, borderSize,
				borderUV, 1, 1);
	}

	private void drawTexturedBoxFromBorder(int x, int y, int width, int height,
			int borderSize, float borderUV, float maxU, float maxV) {
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y, this.zLevel, 0, 0);
		tess.addVertexWithUV(x, y + borderSize, this.zLevel, 0, borderUV);
		tess.addVertexWithUV(x + borderSize, y + borderSize, this.zLevel,
				borderUV, borderUV);
		tess.addVertexWithUV(x + borderSize, y, this.zLevel, borderUV, 0);
		tess.draw();
		tess.startDrawingQuads();
		tess.addTranslation(width - borderSize, 0, 0);
		tess.addVertexWithUV(x, y, this.zLevel, maxU - borderUV, 0);
		tess.addVertexWithUV(x, y + borderSize, this.zLevel, maxU - borderUV,
				borderUV);
		tess.addVertexWithUV(x + borderSize, y + borderSize, this.zLevel, maxU,
				borderUV);
		tess.addVertexWithUV(x + borderSize, y, this.zLevel, maxU, 0);
		tess.draw();
		tess.startDrawingQuads();
		tess.addTranslation(0, height - borderSize, 0);
		tess.addVertexWithUV(x, y, this.zLevel, maxU - borderUV, maxV
				- borderUV);
		tess.addVertexWithUV(x, y + borderSize, this.zLevel, maxU - borderUV,
				maxV);
		tess.addVertexWithUV(x + borderSize, y + borderSize, this.zLevel, maxU,
				maxV);
		tess.addVertexWithUV(x + borderSize, y, this.zLevel, maxU, maxV
				- borderUV);
		tess.draw();
		tess.startDrawingQuads();
		tess.addTranslation(-width + borderSize, 0, 0);
		tess.addVertexWithUV(x, y, this.zLevel, 0, maxV - borderUV);
		tess.addVertexWithUV(x, y + borderSize, this.zLevel, 0, maxV);
		tess.addVertexWithUV(x + borderSize, y + borderSize, this.zLevel,
				borderUV, maxV);
		tess.addVertexWithUV(x + borderSize, y, this.zLevel, borderUV, maxV
				- borderUV);
		tess.draw();
		tess.addTranslation(0, -height + borderSize, 0);

		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y + borderSize, this.zLevel, 0, borderUV);
		tess.addVertexWithUV(x, y + height - borderSize, this.zLevel, 0, maxV
				- borderUV);
		tess.addVertexWithUV(x + borderSize, y + height - borderSize,
				this.zLevel, borderUV, maxV - borderUV);
		tess.addVertexWithUV(x + borderSize, y + borderSize, this.zLevel,
				borderUV, borderUV);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x + width - borderSize, y + borderSize,
				this.zLevel, maxU - borderUV, borderUV);
		tess.addVertexWithUV(x + width - borderSize, y + height - borderSize,
				this.zLevel, maxU - borderUV, maxV - borderUV);
		tess.addVertexWithUV(x + width, y + height - borderSize, this.zLevel,
				maxU, maxV - borderUV);
		tess.addVertexWithUV(x + width, y + borderSize, this.zLevel, maxU,
				borderUV);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x + borderSize, y, this.zLevel, borderUV, 0);
		tess.addVertexWithUV(x + borderSize, y + borderSize, this.zLevel,
				borderUV, borderUV);
		tess.addVertexWithUV(x + width - borderSize, y + borderSize,
				this.zLevel, maxU - borderUV, borderUV);
		tess.addVertexWithUV(x + width - borderSize, y, this.zLevel, maxU
				- borderUV, 0);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x + borderSize, y + height - borderSize,
				this.zLevel, borderUV, maxV - borderUV);
		tess.addVertexWithUV(x + borderSize, y + height, this.zLevel, borderUV,
				maxV);
		tess.addVertexWithUV(x + width - borderSize, y + height, this.zLevel,
				maxU - borderUV, maxV);
		tess.addVertexWithUV(x + width - borderSize, y + height - borderSize,
				this.zLevel, maxU - borderUV, maxV - borderUV);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x + borderSize, y + borderSize, this.zLevel,
				borderUV, borderUV);
		tess.addVertexWithUV(x + borderSize, y + height - borderSize,
				this.zLevel, borderUV, maxV - borderUV);
		tess.addVertexWithUV(x + width - borderSize, y + height - borderSize,
				this.zLevel, maxU - borderUV, maxV - borderUV);
		tess.addVertexWithUV(x + width - borderSize, y + borderSize,
				this.zLevel, maxU - borderUV, borderUV);
		tess.draw();

	}
}
