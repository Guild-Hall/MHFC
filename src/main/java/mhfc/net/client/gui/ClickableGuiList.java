package mhfc.net.client.gui;

import java.util.ArrayList;
import java.util.List;

import mhfc.net.client.gui.ClickableGuiList.GuiListItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.opengl.GL11;

public class ClickableGuiList<Item extends GuiListItem> extends ArrayList<Item> {

	public interface GuiListItem {
		public String getRepresentationString();
	}

	public static class GuiListStringItem implements GuiListItem {

		private String string;

		public GuiListStringItem(String itemString) {
			this.string = itemString;
		}

		@Override
		public String getRepresentationString() {
			return string;
		}

		public String getInitializationString() {
			return string;
		}

	}

	public ClickableGuiList(int posX, int posY, int width, int height,
			float itemHeight) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.itemHeight = itemHeight;
		scrollAmount = 0;
		selected = -1;
		recalculateItemHeightOnDraw = false;
		setAlignmentMid(false);
		setDrawSmallestBounds(false);
	}

	public ClickableGuiList(int width, int height, float itemHeight) {
		this(0, 0, width, height, itemHeight);
	}

	public ClickableGuiList(int posX, int posY, int width, int height) {
		this(posX, posY, width, height, 0);
		recalculateItemHeightOnDraw = true;
		visible = true;
	}

	public ClickableGuiList(int width, int height) {
		this(0, 0, width, height);
	}

	protected int posX, posY;
	protected int width, height;

	protected float itemHeight;

	protected float scrollAmount;
	protected int selected;
	protected boolean recalculateItemHeightOnDraw;
	protected boolean visible;
	private boolean alignmentMid, drawSmallestBounds;

	private static final long serialVersionUID = -7451553351083938970L;

	public float getItemWidth() {
		return itemHeight;
	}

	public void setItemWidth(int itemWidth) {
		this.itemHeight = itemWidth;
	}

	public void draw() {
		if (!visible)
			return;
		// if (size() == 0)
		// return;
		if (recalculateItemHeightOnDraw)
			recalculateItemHeight();
		Minecraft m = Minecraft.getMinecraft();
		int scale = new ScaledResolution(m, m.displayWidth, m.displayHeight)
				.getScaleFactor();
		int openGLy = m.displayHeight;
		GL11.glScissor(posX * scale, openGLy - (posY + height) * scale, width
				* scale, height * scale + 1);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		for (int i = (int) (scrollAmount / itemHeight); i < this.size(); i++) {
			GuiListItem item = this.get(i);
			String representation = item.getRepresentationString();
			FontRenderer fRend = Minecraft.getMinecraft().fontRenderer;
			List<String> lineWrapped = fRend.listFormattedStringToWidth(
					representation, width - 5);
			int lines = lineWrapped.size();
			int stringLength = 0;
			for (String s : lineWrapped) {
				stringLength = Math.max(stringLength, fRend.getStringWidth(s));
			}
			int color = (i == selected) ? 0x404040 : 0x808080;
			fRend.drawSplitString(representation, posX
					+ (isAlignmentMid() ? (width - stringLength) / 2 : 3), posY
					- (int) (scrollAmount - i * itemHeight)
					+ ((int) itemHeight - (fRend.FONT_HEIGHT) * lines) / 2 + 1,
					width - 5, color);
		}
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(2.0f);
		GL11.glColor3f(0.25f, 0.25f, 0.25f);
		int i;
		for (i = (int) (scrollAmount / itemHeight); i <= this.size(); i++) {
			GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex3f(posX, posY - scrollAmount + i * itemHeight, 0f);
			GL11.glVertex3f(posX + width, posY - scrollAmount + i * itemHeight,
					0f);
			GL11.glEnd();
		}
		i--;
		float maxY = posY + height - 0.5f;
		Math.min(posY - scrollAmount + i * itemHeight, posY + height - 0.5f);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex3d(posX - 0.5f + width, posY - scrollAmount + (selected)
				* itemHeight, 0f);
		GL11.glVertex3d(posX - 0.5f + width, posY, 0f);
		GL11.glVertex3d(posX + 0.5f, posY, 0f);
		GL11.glVertex3d(posX + 0.5f, maxY, 0f);
		GL11.glVertex3d(posX - 0.5f + width, maxY, 0f);
		GL11.glVertex3d(posX - 0.5f + width, posY - scrollAmount
				+ (selected + 1) * itemHeight, 0f);
		if (size() == 0)
			GL11.glVertex3d(posX + 0.5f, maxY, 0f);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);

	}

	protected void recalculateItemHeight() {
		itemHeight = (float) height / size();
	}

	public boolean handleClick(int relativeX, int relativeY, int button) {
		if (!visible)
			return false;
		if (relativeX < 0 || relativeX >= width || relativeY < 0
				|| relativeY >= height)
			return false;
		int selec = (int) ((relativeY + scrollAmount) / itemHeight);
		if (selec >= size())
			return false;
		setSelected(selec);
		return true;
	}

	/**
	 * Returns which item id is selected or -1 if none
	 * 
	 */
	public int getSelected() {
		return selected;
	}

	public Item getSelectedItem() {
		if (selected >= 0 && selected < size()) {
			return get(selected);
		}
		return null;
	}

	protected void setSelected(int newSelected) {
		selected = newSelected;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosition(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidthAndHeight(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public boolean isAlignmentMid() {
		return alignmentMid;
	}

	public void setAlignmentMid(boolean alignmentMid) {
		this.alignmentMid = alignmentMid;
	}

	public void setRecalculateItemHeightOnDraw(boolean recalculate) {
		this.recalculateItemHeightOnDraw = recalculate;
	}

	public boolean isDrawSmallestBounds() {
		return drawSmallestBounds;
	}

	public void setDrawSmallestBounds(boolean drawSmallestBounds) {
		this.drawSmallestBounds = drawSmallestBounds;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean newVisible) {
		this.visible = newVisible;
	}

}
