package mhfc.net.client.util.gui;

import static org.lwjgl.opengl.GL11.glColor4f;

import java.util.List;
import java.util.Objects;

import com.google.common.base.Preconditions;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.stringview.Viewable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.StatCollector;

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
		s = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
	}

	/**
	 * Draws the content of the view according to the following rules:<br>
	 * A '\n' character represents a line break. A '\f' represents a page break.<br>
	 * page decides which page to render. When page refers to an illegal page, nothing is rendered.<br>
	 * scrolledHeight refers to the portion of text that is skipped by rendering. Every line break will add one
	 * lineheight of height to the text. Every line that is not fully in view will not be rendered.<br>
	 * width refers to the maximal width of the rendered text. A linebreak is inserted before a word if the line would
	 * be longer than width if it were rendered.<br>
	 * height sets the height of text that is being render after scrolledHeight has been skipped.<br>
	 * color is the color of the text to render with.
	 *
	 * @param view
	 *            the view to render
	 * @param buffer
	 *            the buffer to use to store strings (to keep memory profile small)
	 * @param pageNbr
	 *            the page to render, see 'page break'
	 * @param scrolledHeight
	 *            the skipped height, that is not rendered
	 * @param width
	 *            the width to render the page with
	 * @param height
	 *            the height to render
	 * @param lineHeight
	 *            the height of one line
	 * @param posX
	 *            the pos to render at
	 * @param posY
	 *            the pos to render at
	 * @param color
	 *            the color to render the text with
	 * @param fRend
	 *            the font renderer to render with
	 */
	public static void drawViewable(
			Viewable view,
			StringBuilder buffer,
			int pageNbr,
			int scrolledHeight,
			int width,
			int height,
			int posX,
			int posY,
			int lineHeight,
			int color,
			FontRenderer fRend) {
		Preconditions.checkArgument(width >= 0, "width must be positive");
		Preconditions.checkArgument(height >= 0, "height must be positive");
		if (pageNbr < 0) {
			return;
		}
		buffer.setLength(0);
		view.appendTo(buffer);
		String rawContent = buffer.toString();

		String[] pages = rawContent.split("\f");
		if (pageNbr >= pages.length) {
			return;
		}
		String pageContent = pages[pageNbr];
		String[] paragraphs = pageContent.split("\n");
		int relPosY = -scrolledHeight;
		for (String pgrh : paragraphs) {
			relPosY = renderParagraph(pgrh, width, height, posX, posY, relPosY, lineHeight, color, fRend);
			relPosY += lineHeight; // Line break after paragraph
			if (relPosY + lineHeight > height) {
				// Shortcurcuit, we are beyond the rendered area
				return;
			}
		}
	}

	/**
	 * Renders a paragraph
	 *
	 * @param paragraph
	 *            the content of the paragraph
	 * @param width
	 * @param height
	 * @param posX
	 * @param posY
	 * @param relPosY
	 * @param lineHeight
	 * @param color
	 * @param fRend
	 * @return the new relPosY
	 */
	private static int renderParagraph(
			String paragraph,
			int width,
			int height,
			int posX,
			int posY,
			int relPosY,
			int lineHeight,
			int color,
			FontRenderer fRend) {
		List<String> lines = fRend.listFormattedStringToWidth(paragraph, width);
		int lineSize = lines.size();
		for (int i = 0; i < lineSize; i++, relPosY += lineHeight) {
			String line = lines.get(i);
			if (relPosY + lineHeight > height) {
				break;
			}
			if (relPosY >= 0) {
				fRend.drawString(line, posX, posY + relPosY, color);
			}
		}
		relPosY -= lineHeight;
		return relPosY;
	}

	public static int drawTextLocalizedAndReturnHeight(
			FontRenderer fRend,
			String string,
			int posX,
			int posY,
			int width,
			int colour) {
		String localized = StatCollector.translateToLocal(string);
		return drawTextAndReturnHeight(fRend, localized, posX, posY, width, colour);
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
		Objects.requireNonNull(fRend);
		Objects.requireNonNull(string);
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
		Tessellator t = Tessellator.instance;

		t.startDrawingQuads();
		t.addVertexWithUV(xMin, yMin, zLevel, u, v);
		t.addVertexWithUV(xMin, yMin + height, zLevel, u, v + vHeight);
		t.addVertexWithUV(xMin + width, yMin + height, zLevel, u + uWidth, v + vHeight);
		t.addVertexWithUV(xMin + width, yMin, zLevel, u + uWidth, v);
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
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y, zLevel, 0, 0);
		tess.addVertexWithUV(x, y + borderSize, zLevel, 0, borderV);
		tess.addVertexWithUV(x + borderSize, y + borderSize, zLevel, borderU, borderV);
		tess.addVertexWithUV(x + borderSize, y, zLevel, borderU, 0);
		tess.draw();
		tess.startDrawingQuads();
		tess.addTranslation(width - borderSize, 0, 0);
		tess.addVertexWithUV(x, y, zLevel, maxU - borderU, 0);
		tess.addVertexWithUV(x, y + borderSize, zLevel, maxU - borderU, borderV);
		tess.addVertexWithUV(x + borderSize, y + borderSize, zLevel, maxU, borderV);
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
		tess.addVertexWithUV(x + borderSize, y + borderSize, zLevel, borderU, maxV);
		tess.addVertexWithUV(x + borderSize, y, zLevel, borderU, maxV - borderV);
		tess.draw();
		tess.addTranslation(0, -height + borderSize, 0);

		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y + borderSize, zLevel, 0, borderV);
		tess.addVertexWithUV(x, y + height - borderSize, zLevel, 0, maxV - borderV);
		tess.addVertexWithUV(x + borderSize, y + height - borderSize, zLevel, borderU, maxV - borderV);
		tess.addVertexWithUV(x + borderSize, y + borderSize, zLevel, borderU, borderV);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x + width - borderSize, y + borderSize, zLevel, maxU - borderU, borderV);
		tess.addVertexWithUV(x + width - borderSize, y + height - borderSize, zLevel, maxU - borderU, maxV - borderV);
		tess.addVertexWithUV(x + width, y + height - borderSize, zLevel, maxU, maxV - borderV);
		tess.addVertexWithUV(x + width, y + borderSize, zLevel, maxU, borderV);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x + borderSize, y, zLevel, borderU, 0);
		tess.addVertexWithUV(x + borderSize, y + borderSize, zLevel, borderU, borderV);
		tess.addVertexWithUV(x + width - borderSize, y + borderSize, zLevel, maxU - borderU, borderV);
		tess.addVertexWithUV(x + width - borderSize, y, zLevel, maxU - borderU, 0);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x + borderSize, y + height - borderSize, zLevel, borderU, maxV - borderV);
		tess.addVertexWithUV(x + borderSize, y + height, zLevel, borderU, maxV);
		tess.addVertexWithUV(x + width - borderSize, y + height, zLevel, maxU - borderU, maxV);
		tess.addVertexWithUV(x + width - borderSize, y + height - borderSize, zLevel, maxU - borderU, maxV - borderV);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x + borderSize, y + borderSize, zLevel, borderU, borderV);
		tess.addVertexWithUV(x + borderSize, y + height - borderSize, zLevel, borderU, maxV - borderV);
		tess.addVertexWithUV(x + width - borderSize, y + height - borderSize, zLevel, maxU - borderU, maxV - borderV);
		tess.addVertexWithUV(x + width - borderSize, y + borderSize, zLevel, maxU - borderU, borderV);
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
