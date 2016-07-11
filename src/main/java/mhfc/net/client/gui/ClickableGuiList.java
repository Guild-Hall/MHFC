package mhfc.net.client.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.gui.GuiListItem.Alignment;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.client.util.gui.MHFCGuiUtil;
import net.minecraft.client.Minecraft;

public class ClickableGuiList<Item extends GuiListItem> extends ArrayList<Item>
		implements
		IMouseInteractable,
		IMHFCGuiItem {

	private static final float SLIDER_WIDTH = 8;
	private static final float BORDER_WIDTH = 2;

	public static class GuiListStringItem extends GuiListItem {

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

	/**
	 * Constructs a list with fixed list item height.
	 *
	 * @param posX
	 *            The X-Position of the list
	 * @param posY
	 *            The Y-Position of the list
	 * @param width
	 *            The width of the list
	 * @param height
	 *            The height of the list
	 * @param itemHeight
	 *            The height to be used for list items
	 */
	public ClickableGuiList(int width, int height, float itemHeight) {
		this.width = width;
		this.height = height;
		this.itemHeight = itemHeight;
	}

	/**
	 * Constructs a list that automatically adjusts the height of its items depending on their count
	 *
	 * @param posX
	 *            The X-position in the parent frame
	 * @param posY
	 *            The Y-position in the parent frame
	 * @param width
	 *            The width of the list
	 * @param height
	 *            The height of the list
	 */
	public ClickableGuiList(int width, int height) {
		this(width, height, 0);
		recalculateItemHeightOnDraw = true;
	}

	protected int width, height;

	protected float itemHeight;

	protected float scrollAmount = 0;
	protected int selected = -1;
	protected float sliderWidth = SLIDER_WIDTH;

	protected boolean recalculateItemHeightOnDraw = false;
	protected boolean visible = true;
	private boolean drawSmallestBounds = false;
	private Alignment alignment = Alignment.LEFT;

	protected boolean isSliderDragged = false;

	protected float mouseClickX, mouseClickY;
	protected float mouseClickMoveX, mouseClickMoveY;
	private Minecraft mc;

	private static final long serialVersionUID = -7451553351083938970L;

	public float getItemWidth() {
		return itemHeight;
	}

	public void setItemWidth(int itemWidth) {
		this.itemHeight = itemWidth;
		this.recalculateItemHeightOnDraw = false;
	}

	public float getSliderWidth() {
		return sliderWidth;
	}

	public void setSliderWidth(float sliderWidth) {
		this.sliderWidth = sliderWidth;
	}

	protected void draw(int baseX, int baseY, int mouseX, int mouseY) {
		if (!visible) {
			return;
		}
		int posX = baseX;
		int posY = baseY;
		if (recalculateItemHeightOnDraw) {
			recalculateItemHeight();
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);

		drawBackground(posX, posY, mouseX, mouseY);
		drawListItems(posX, posY, mouseX, mouseY);
		drawListSlider(posX, posY, mouseX, mouseY);

		GL11.glEnable(GL11.GL_BLEND);
	}

	protected void drawListSlider(int posX, int posY, int mouseX, int mouseY) {
		float alpha = getSliderAlpha(mouseX, mouseY);
		if (alpha > 0f && !isEmpty()) {
			float sliderHeight = getSliderHeight();
			float sliderPosY = getSliderVerticalDisplacement();
			GL11.glColor4f(1, 1, 1, alpha);
			mc.getTextureManager().bindTexture(MHFCRegQuestVisual.CLICKABLE_LIST);
			MHFCGuiUtil.drawTexturedRectangle(
					posX + width - sliderWidth,
					posY + sliderPosY,
					sliderWidth,
					sliderHeight,
					0.5f,
					0.5f,
					0.0625f,
					0.5f);
		}
	}

	/**
	 * Returns the size the list would have if all items were displayed in their full height
	 */
	public final float getFullHeight() {
		return itemHeight * size();
	}

	/**
	 * Returns the current height of the default slider
	 */
	public final float getSliderHeight() {
		return height * height / getFullHeight();
	}

	/**
	 * Returns the current vertical displacement of the default slider
	 */
	public final float getSliderVerticalDisplacement() {
		return scrollAmount * height / getFullHeight();
	}

	/**
	 * Gets the alpha value that should currently be used for the slider. Useful for effects, that maybe added by
	 * subclasses.
	 */
	protected float getSliderAlpha(int mouseX, int mouseY) {
		if (isMouseOnSlider(mouseX, mouseY) || isSliderDragged) {
			return 1.0f;
		}
		return 0.0f;
	}

	/**
	 * Checks if a slider should be present
	 */
	protected boolean isSliderRequired() {
		return itemHeight * size() > height;
	}

	/**
	 * Return if the mouse actually hit the slider with its positioning
	 */
	protected boolean isMouseOnSlider(float relativeX, float relativeY) {
		return hoversMouseOverSlider(relativeX, relativeY) && isSliderRequired();
	}

	/**
	 * Tells if the mouse position (relative to parent frame, like this list, is over the area where the scroll bar
	 * would be.
	 */
	protected boolean hoversMouseOverSlider(float relativeX, float relativeY) {
		if (relativeY >= 0 && relativeY <= height && relativeX > width - sliderWidth && relativeX <= width) {
			return true;
		}
		return false;
	}

	protected void drawBackground(int posX, int posY, int mouseX, int mouseY) {
		GL11.glColor4f(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(MHFCRegQuestVisual.CLICKABLE_LIST);
		float texheight = height;
		if (drawSmallestBounds) {
			texheight = Math.min(size() * itemHeight, texheight);
		}
		MHFCGuiUtil.drawTexturedRectangle(posX, posY, width, texheight, 0, 0, 0.5f, 1.0f);
		if (selected >= 0) {
			float selectionYMin = selected * itemHeight - scrollAmount;
			float selectionYHeight = itemHeight;
			if (selectionYMin < BORDER_WIDTH) {
				float sm = BORDER_WIDTH - selectionYMin;
				selectionYMin += sm;
				selectionYHeight -= sm;
			}
			if (selectionYMin + selectionYHeight > height) {
				float sm = selectionYMin + selectionYHeight - height;
				selectionYHeight -= sm;
			}
			MHFCGuiUtil.drawTexturedRectangle(
					posX + width - BORDER_WIDTH,
					posY + selectionYMin,
					BORDER_WIDTH,
					selectionYHeight,
					0.625f,
					0.5f,
					0.125f,
					0.25f);
		}
	}

	protected void drawListItems(int posX, int posY, int mouseX, int mouseY) {
		int startIndex = (int) (scrollAmount / itemHeight + 0.5f);
		for (int i = startIndex; i < this.size(); i++) {
			GuiListItem item = this.get(i);
			int itemPosY = posY - (int) (scrollAmount - i * itemHeight);
			item.draw(posX, itemPosY, width, (int) itemHeight, mc, i == selected, alignment);
			if (i - startIndex >= (int) (height / itemHeight)) {
				break;
			}
		}
	}

	/**
	 * Sets the item height to a number, so that all elements in the list are displayed.
	 */
	protected void recalculateItemHeight() {
		itemHeight = (float) height / size();
	}

	@Override
	public boolean handleClick(float relativeX, float relativeY, int button) {
		if (!visible || relativeX < 0 || relativeX >= width || relativeY < 0 || relativeY >= height) {
			return false;
		}
		if (isMouseOnSlider(relativeX, relativeY)) {
			mouseClickX = relativeX;
			mouseClickY = relativeY;
			mouseClickMoveX = mouseClickX;
			mouseClickMoveY = mouseClickY;
		} else {
			int selec = (int) ((relativeY + scrollAmount) / itemHeight);
			if (selec >= size()) {
				return false;
			}
			setSelected(selec);
		}
		return true;
	}

	@Override
	public void handleMovementMouseDown(float mouseX, float mouseY, int button, long timeDiff) {
		if (isMouseOnSlider(mouseClickX, mouseClickY)) {
			scrollAmount += (mouseY - mouseClickMoveY) / height * getFullHeight();
			scrollAmount = Math.min(scrollAmount, getFullHeight() - height);
			scrollAmount = Math.max(scrollAmount, 0f);
			isSliderDragged = true;
			mouseClickMoveX = mouseX;
			mouseClickMoveY = mouseY;
		}
	}

	@Override
	public void handleMouseUp(float mouseX, float mouseY, int id) {
		isSliderDragged = false;
	}

	@Override
	public void handleMovement(float mouseX, float mouseY) {}

	/**
	 * Returns which item id is selected or -1 if none
	 *
	 */
	public int getSelected() {
		return selected;
	}

	/**
	 * Side effect, increase selection index when insertion happened before it. If you insert before 0 and catch
	 * exceptions, the selection index might be at a different position than expected
	 */
	@Override
	public void add(int index, Item element) {
		if (index <= selected) {
			selected++;
		}
		super.add(index, element);
	}

	/**
	 * See {@link ClickableGuiList#remove(int)}
	 */
	@Override
	public boolean remove(Object o) {
		int index = indexOf(o);
		if (index < selected) {
			selected--;
		}
		if (index == selected) {
			selected = -1;
		}
		return super.remove(o);
	}

	/**
	 * Side effect, decreases selection index when deletion occurs before it. Selects -1 when the selected element is
	 * removed. If you delete before 0 and catch exceptions, the selection index might be at a different position than
	 * expected.
	 */
	@Override
	public Item remove(int index) {
		if (index < selected) {
			selected--;
		}
		if (index == selected) {
			selected = -1;
		}
		return super.remove(index);
	}

	@Override
	public void clear() {
		selected = -1;
		super.clear();
	}

	public Item getSelectedItem() {
		if (selected >= 0 && selected < size()) {
			return get(selected);
		}
		return null;
	}

	/**
	 * Sets the currently selected index
	 */
	public void setSelected(int newSelected) {
		selected = newSelected;
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

	/**
	 * Gets the currently used {@link Alignment} for list items
	 */
	public Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;

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

	@Override
	public void draw(double mouseX, double mouseY, float partialTick) {
		draw(0, 0, (int) mouseX, (int) mouseY);
	}

	@Override
	public void initializeContext(Minecraft mc) {
		this.mc = mc;
	}

}
