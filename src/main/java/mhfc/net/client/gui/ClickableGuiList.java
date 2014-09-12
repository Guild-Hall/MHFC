package mhfc.net.client.gui;

import java.util.ArrayList;

import mhfc.net.client.gui.ClickableGuiList.GuiListItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class ClickableGuiList<Item extends GuiListItem> extends ArrayList<Item> {

	public interface GuiListItem {
		public String getRepresentationString();
	}

	public ClickableGuiList(int posX, int posY, int width, int height,
			int itemWidth) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.itemWidth = itemWidth;
		scrollAmount = 0;
		selected = -1;
	}

	protected int posX, posY;
	protected int width, height;

	protected int itemWidth;

	protected float scrollAmount;
	protected int selected;

	private static final long serialVersionUID = -7451553351083938970L;

	public int getItemWidth() {
		return itemWidth;
	}

	public void setItemWidth(int itemWidth) {
		this.itemWidth = itemWidth;
	}

	public void draw() {
		for (int i = (int) (scrollAmount / itemWidth); i < this.size(); i++) {
			GuiListItem item = this.get(i);
			FontRenderer fRend = Minecraft.getMinecraft().fontRenderer;
			fRend.drawSplitString(item.getRepresentationString(), posX + 5,
					posY - (int) scrollAmount + i * itemWidth
							+ (itemWidth - fRend.FONT_HEIGHT) / 2, width - 5,
					4210752);
		}
	}

	public boolean handleClick(int relativeX, int relativeY, int button) {
		if (relativeX < 0 && relativeX >= width && relativeY < 0
				&& relativeY >= height)
			return false;
		int selec = (int) ((relativeX + scrollAmount) / itemWidth);
		if (selec > size())
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

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
