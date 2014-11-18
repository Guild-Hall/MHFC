package mhfc.net.common.util.gui;

import net.minecraft.client.renderer.Tessellator;

public class MHFCGuiUtil {
	public static float zLevel;

	public static void drawTexturedBoxFromBorder(int x, int y, int width,
			int height) {
		drawTexturedBoxFromBorder(x, y, zLevel, width, height);
	}

	public static void drawTexturedBoxFromBorder(int x, int y, float zLevel,
			int width, int height) {
		drawTexturedBoxFromBorder(x, y, zLevel, width, height,
				Math.min(Math.min(15, width / 2), height / 2));
	}

	public static void drawTexturedBoxFromBorder(int x, int y, float zLevel,
			int width, int height, int borderSize) {
		drawTexturedBoxFromBorder(x, y, zLevel, width, height, borderSize,
				borderSize / 256f);
	}

	public static void drawTexturedBoxFromBorder(int x, int y, float zLevel,
			int width, int height, int borderSize, float borderUV) {
		drawTexturedBoxFromBorder(x, y, zLevel, width, height, borderSize,
				borderUV, 1, 1);
	}

	public static void drawTexturedBoxFromBorder(int x, int y, float zLevel,
			int width, int height, int borderSize, float borderUV, float maxU,
			float maxV) {
		drawTexturedBoxFromBorder(x, y, zLevel, width, height, borderSize,
				borderUV, borderUV, maxU, maxV);
	}

	public static void drawTexturedBoxFromBorder(int x, int y, float zLevel,
			int width, int height, int borderSize, float borderU,
			float borderV, float maxU, float maxV) {
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y, zLevel, 0, 0);
		tess.addVertexWithUV(x, y + borderSize, zLevel, 0, borderV);
		tess.addVertexWithUV(x + borderSize, y + borderSize, zLevel, borderU,
				borderV);
		tess.addVertexWithUV(x + borderSize, y, zLevel, borderU, 0);
		tess.draw();
		tess.startDrawingQuads();
		tess.addTranslation(width - borderSize, 0, 0);
		tess.addVertexWithUV(x, y, zLevel, maxU - borderU, 0);
		tess.addVertexWithUV(x, y + borderSize, zLevel, maxU - borderU, borderV);
		tess.addVertexWithUV(x + borderSize, y + borderSize, zLevel, maxU,
				borderV);
		tess.addVertexWithUV(x + borderSize, y, zLevel, maxU, 0);
		tess.draw();
		tess.startDrawingQuads();
		tess.addTranslation(0, height - borderSize, 0);
		tess.addVertexWithUV(x, y, zLevel, maxU - borderU, maxV - borderV);
		tess.addVertexWithUV(x, y + borderSize, zLevel, maxU - borderU, maxV);
		tess.addVertexWithUV(x + borderSize, y + borderSize, zLevel, maxU, maxV);
		tess.addVertexWithUV(x + borderSize, y, zLevel, maxU, maxV - borderV);
		tess.draw();
		tess.startDrawingQuads();
		tess.addTranslation(-width + borderSize, 0, 0);
		tess.addVertexWithUV(x, y, zLevel, 0, maxV - borderV);
		tess.addVertexWithUV(x, y + borderSize, zLevel, 0, maxV);
		tess.addVertexWithUV(x + borderSize, y + borderSize, zLevel, borderU,
				maxV);
		tess.addVertexWithUV(x + borderSize, y, zLevel, borderU, maxV - borderV);
		tess.draw();
		tess.addTranslation(0, -height + borderSize, 0);

		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y + borderSize, zLevel, 0, borderV);
		tess.addVertexWithUV(x, y + height - borderSize, zLevel, 0, maxV
				- borderV);
		tess.addVertexWithUV(x + borderSize, y + height - borderSize, zLevel,
				borderU, maxV - borderV);
		tess.addVertexWithUV(x + borderSize, y + borderSize, zLevel, borderU,
				borderV);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x + width - borderSize, y + borderSize, zLevel,
				maxU - borderU, borderV);
		tess.addVertexWithUV(x + width - borderSize, y + height - borderSize,
				zLevel, maxU - borderU, maxV - borderV);
		tess.addVertexWithUV(x + width, y + height - borderSize, zLevel, maxU,
				maxV - borderV);
		tess.addVertexWithUV(x + width, y + borderSize, zLevel, maxU, borderV);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x + borderSize, y, zLevel, borderU, 0);
		tess.addVertexWithUV(x + borderSize, y + borderSize, zLevel, borderU,
				borderV);
		tess.addVertexWithUV(x + width - borderSize, y + borderSize, zLevel,
				maxU - borderU, borderV);
		tess.addVertexWithUV(x + width - borderSize, y, zLevel, maxU - borderU,
				0);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x + borderSize, y + height - borderSize, zLevel,
				borderU, maxV - borderV);
		tess.addVertexWithUV(x + borderSize, y + height, zLevel, borderU, maxV);
		tess.addVertexWithUV(x + width - borderSize, y + height, zLevel, maxU
				- borderU, maxV);
		tess.addVertexWithUV(x + width - borderSize, y + height - borderSize,
				zLevel, maxU - borderU, maxV - borderV);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x + borderSize, y + borderSize, zLevel, borderU,
				borderV);
		tess.addVertexWithUV(x + borderSize, y + height - borderSize, zLevel,
				borderU, maxV - borderV);
		tess.addVertexWithUV(x + width - borderSize, y + height - borderSize,
				zLevel, maxU - borderU, maxV - borderV);
		tess.addVertexWithUV(x + width - borderSize, y + borderSize, zLevel,
				maxU - borderU, borderV);
		tess.draw();

	}

}
