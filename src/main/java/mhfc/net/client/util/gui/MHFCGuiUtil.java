package mhfc.net.client.util.gui;

import static org.lwjgl.opengl.GL11.glColor4f;

import mhfc.net.MHFCMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class MHFCGuiUtil {

	public static final int COLOUR_FOREGROUND = 0xd8953c;
	public static final int COLOUR_TEXT = 0x404040;
	public static final int COLOUR_TITLE = 0x000000;

	private static final String WIDTH_WARNING = "The width of the draw was smaller than the first character. This creates a stack overflow.\n Please don't do this, track it down. Stacktrace:";
	public static float zLevel;
	private static ScaledResolution s;

	public static int realScreenWidth(Minecraft mc) {
		if (mc == null) {
			throw new IllegalArgumentException("Gui utils may only be accessed with valid minecraft");
		}
		return mc.displayWidth;
	}

	public static int realScreenHeight(Minecraft mc) {
		if (mc == null) {
			throw new IllegalArgumentException("Gui utils may only be accessed with valid minecraft");
		}
		return mc.displayHeight;
	}

	public static int minecraftWidth(Minecraft mc) {
		refreshScaled(mc);
		return s.getScaledWidth();
	}

	public static int minecraftHeight(Minecraft mc) {
		refreshScaled(mc);
		return s.getScaledHeight();
	}

	public static int guiScaleFactor(Minecraft mc) {
		refreshScaled(mc);
		return s.getScaleFactor();
	}

	private static void refreshScaled(Minecraft mc) {
		if (mc == null) {
			throw new IllegalArgumentException("Gui utils may only be accessed with valid minecraft");
		}
		s = new ScaledResolution(mc);
		// Not sure if this will work but i read some help that i must add this lel.
		double scaleW = mc.displayWidth;
		double scaleH = mc.displayHeight;
	}

	/**
	 * Draws a string onto the screen at the desired position. If width is > 0, then the draw split string method is
	 * used instead. The amount if vertical space occupied by the draw is calculated and returned. If one attempts to
	 * draw a null String or with a null renderer, a warning is printed (including a stack trace) and 0 is returned.
	 *
	 * @return The drawn height of the string. Always line height for valid parameters and width==0
	 */
	public static int drawTextAndReturnHeight(
			FontRenderer fRend,
			String string,
			int posX,
			int posY,
			int width,
			int colour) {
		if (fRend == null || string == null) {
			MHFCMain.logger().warn(fRend == null ? "Null renderer used as argument" : "Render request for a null string");
			Thread.dumpStack();
			return 0;
		}
		int lines = 1;
		if (width <= 0) {
			fRend.drawString(string, posX, posY, colour);
		} else if (isDrawWidthTooSmall(fRend, width, string)) {
			MHFCMain.logger().info(WIDTH_WARNING);
			Thread.dumpStack();
			lines = 0;
		} else {
			lines = fRend.listFormattedStringToWidth(string, width).size();
			fRend.drawSplitString(string, posX, posY, width, colour);
		}
		return lines * fRend.FONT_HEIGHT;
	}

	/**
	 * Considers if the draw width would cause Minecraft to crash using the given string. This can happen when the first
	 * character can't even fit into the width.
	 *
	 * @return If the font renderer will crash the game with this string and this width
	 */
	public static boolean isDrawWidthTooSmall(FontRenderer fRend, int width, String string) {
		return !string.isEmpty() && width < fRend.getStringWidth(string.substring(0, 1));
	}

	public static void drawTexturedRectangle(
			double xMin,
			double yMin,
			double width,
			double height,
			float u,
			float v,
			float uWidth,
			float vHeight) {
		Tessellator t = Tessellator.getInstance();
		VertexBuffer buffer = t.getBuffer();
		buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(xMin, yMin, zLevel).tex(u, v).endVertex();;
		buffer.pos(xMin, yMin + height, zLevel).tex( u, v + vHeight).endVertex();;
		buffer.pos(xMin + width, yMin + height, zLevel).tex(u + uWidth, v + vHeight).endVertex();;
		buffer.pos(xMin + width, yMin, zLevel).tex(u + uWidth, v).endVertex();;
		t.draw();
	}

	public static void drawTexturedBoxFromBorder(float x, float y, float width, float height) {
		drawTexturedBoxFromBorder(x, y, zLevel, width, height);
	}

	public static void drawTexturedBoxFromBorder(float x, float y, float zLevel, float width, float height) {
		drawTexturedBoxFromBorder(x, y, zLevel, width, height, Math.min(Math.min(15, width / 2), height / 2));
	}

	public static void drawTexturedBoxFromBorder(
			float x,
			float y,
			float zLevel,
			float width,
			float height,
			float borderSize) {
		drawTexturedBoxFromBorder(x, y, zLevel, width, height, borderSize, borderSize / 256f);
	}

	public static void drawTexturedBoxFromBorder(
			float x,
			float y,
			float zLevel,
			float width,
			float height,
			float borderSize,
			float borderUV) {
		drawTexturedBoxFromBorder(x, y, zLevel, width, height, borderSize, borderUV, 1, 1);
	}

	public static void drawTexturedBoxFromBorder(
			float x,
			float y,
			float zLevel,
			float width,
			float height,
			float borderSize,
			float borderUV,
			float maxU,
			float maxV) {
		drawTexturedBoxFromBorder(x, y, zLevel, width, height, borderSize, borderUV, borderUV, maxU, maxV);
	}

	public static void drawTexturedBoxFromBorder(
			float x,
			float y,
			float zLevel,
			float width,
			float height,
			float borderSize,
			float borderU,
			float borderV,
			float maxU,
			float maxV) {
		Tessellator tess = Tessellator.getInstance();
		VertexBuffer buff = tess.getBuffer();
		buff.begin(7, DefaultVertexFormats.POSITION_TEX);
		buff.pos(x, y + borderSize, zLevel).tex(0, 0);
		buff.pos(x + borderSize, y + borderSize, zLevel);
		buff.pos(x + borderSize, y, zLevel);
		tess.draw();
		buff.begin(7, DefaultVertexFormats.POSITION_TEX);
		buff.setTranslation(width - borderSize, 0, 0);
		buff.pos(x, y, zLevel);
		buff.pos(x, y + borderSize, zLevel);
		buff.pos(x + borderSize, y + borderSize, zLevel);
		buff.pos(x + borderSize, y, zLevel);
		tess.draw();
		buff.begin(7, DefaultVertexFormats.POSITION_TEX);
		buff.setTranslation(0, height - borderSize, 0);
		buff.pos(x, y, zLevel);
		buff.pos(x, y + borderSize, zLevel);
		buff.pos(x + borderSize, y + borderSize, zLevel);
		buff.pos(x + borderSize, y, zLevel);
		tess.draw();
		buff.begin(7, DefaultVertexFormats.POSITION_TEX);
		buff.setTranslation(-width + borderSize, 0, 0);
		buff.pos(x, y, zLevel);
		buff.pos(x, y + borderSize, zLevel);
		buff.pos(x + borderSize, y + borderSize, zLevel);
		buff.pos(x + borderSize, y, zLevel);
		tess.draw();
		buff.setTranslation(0, -height + borderSize, 0);

		buff.begin(7, DefaultVertexFormats.POSITION_TEX);
		buff.pos(x, y + borderSize, zLevel);
		buff.pos(x, y + height - borderSize, zLevel);
		buff.pos(x + borderSize, y + height - borderSize, zLevel);
		buff.pos(x + borderSize, y + borderSize, zLevel);
		tess.draw();

		buff.begin(7, DefaultVertexFormats.POSITION_TEX);
		buff.pos(x + width - borderSize, y + borderSize, zLevel);
		buff.pos(x + width - borderSize, y + height - borderSize, zLevel);
		buff.pos(x + width, y + height - borderSize, zLevel);
		buff.pos(x + width, y + borderSize, zLevel);
		tess.draw();

		buff.begin(7, DefaultVertexFormats.POSITION_TEX);
		buff.pos(x + borderSize, y, zLevel);
		buff.pos(x + borderSize, y + borderSize, zLevel);
		buff.pos(x + width - borderSize, y + borderSize, zLevel);
		buff.pos(x + width - borderSize, y, zLevel);
		tess.draw();

		buff.begin(7, DefaultVertexFormats.POSITION_TEX);
		buff.pos(x + borderSize, y + height - borderSize, zLevel);
		buff.pos(x + borderSize, y + height, zLevel);
		buff.pos(x + width - borderSize, y + height, zLevel);
		buff.pos(x + width - borderSize, y + height - borderSize, zLevel);
		tess.draw();

		buff.begin(7, DefaultVertexFormats.POSITION_TEX);
		buff.pos(x + borderSize, y + borderSize, zLevel);
		buff.pos(x + borderSize, y + height - borderSize, zLevel);
		buff.pos(x + width - borderSize, y + height - borderSize, zLevel);
		buff.pos(x + width - borderSize, y + borderSize, zLevel);
		tess.draw();

	}

	public static void setColor(int colorRGB) {
		setColor(colorRGB, 1.0f);
	}

	public static void setColor(int colorRGB, float alpha) {
		float r = ((colorRGB >> 16) & 0xFF) / 255.f, g = ((colorRGB >> 8) & 0xFF) / 255.f,
				b = (colorRGB & 0xFF) / 255.f;
		glColor4f(r, g, b, alpha);
	}

}
