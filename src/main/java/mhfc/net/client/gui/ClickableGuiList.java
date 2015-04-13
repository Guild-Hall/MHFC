package mhfc.net.client.gui;

import java.util.ArrayList;

import mhfc.net.client.gui.GuiListItem.Alignment;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.util.gui.MHFCGuiUtil;
import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

public class ClickableGuiList<Item extends GuiListItem> extends ArrayList<Item>
	implements
		IMouseInteractable {

	private static final float SLIDER_WIDTH = 6;
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
	public ClickableGuiList(int posX, int posY, int width, int height,
		float itemHeight) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.itemHeight = itemHeight;
	}

	public ClickableGuiList(int width, int height, float itemHeight) {
		this(0, 0, width, height, itemHeight);
	}

	/**
	 * Constructs a list that automatically adjusts the height of its items
	 * depending on their count
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
	public ClickableGuiList(int posX, int posY, int width, int height) {
		this(posX, posY, width, height, 0);
		recalculateItemHeightOnDraw = true;
	}

	public ClickableGuiList(int width, int height) {
		this(0, 0, width, height);
	}

	protected int posX, posY;
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

	protected int mouseClickX, mouseClickY;
	protected int mouseClickMoveX, mouseClickMoveY;

	private static final long serialVersionUID = -7451553351083938970L;

	public float getItemWidth() {
		return itemHeight;
	}

	public void setItemWidth(int itemWidth) {
		this.itemHeight = itemWidth;
	}

	public float getSliderWidth() {
		return sliderWidth;
	}

	public void setSliderWidth(float sliderWidth) {
		this.sliderWidth = sliderWidth;
	}

	public void draw(int baseX, int baseY, int mouseX, int mouseY) {
		if (!visible)
			return;
		int posX = this.posX + baseX;
		int posY = this.posY + baseY;
		if (recalculateItemHeightOnDraw)
			recalculateItemHeight();
		int width = Math.max(this.width, 15);
		Minecraft m = Minecraft.getMinecraft();
		int scale = MHFCGuiUtil.guiScaleFactor(m);
		int openGLy = m.displayHeight;
		GL11.glScissor(posX * scale, openGLy - (posY + height) * scale, width
			* scale, height * scale + 1);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);

		drawBackground(posX, posY, mouseX, mouseY);
		drawListItems(posX, posY, mouseX, mouseY);
		drawListSlider(posX, posY, mouseX, mouseY);

		GL11.glDisable(GL11.GL_SCISSOR_TEST);

	}

	protected void drawListSlider(int posX, int posY, int mouseX, int mouseY) {
		float alpha = getSliderAlpha(mouseX, mouseY);
		if (alpha > 0f && !isEmpty()) {
			float extendedHeight = itemHeight * size();
			float sliderHeight = height * height / extendedHeight;
			float sliderPosY = scrollAmount * height / extendedHeight;
			GL11.glColor4f(1, 1, 1, alpha);
			Minecraft.getMinecraft().getTextureManager().bindTexture(
				MHFCRegQuestVisual.CLICKABLE_LIST);
			MHFCGuiUtil.drawTexturedRectangle(posX + width - sliderWidth, posY
				+ sliderPosY, sliderWidth, sliderHeight, 0.5f, 0.5f, 0.125f,
				0.5f);
		}
	}

	/**
	 * Returns the size the list would have if all items were displayed in their
	 * full height
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
	 * Gets the alpha value that should currently be used for the slider. Useful
	 * for effects, that maybe added by subclasses.
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
	protected boolean isMouseOnSlider(int mouseX, int mouseY) {
		return hoversMouseOverSlider(mouseX, mouseY) && isSliderRequired();
	}

	/**
	 * Tells if the mouse position (relative to parent frame, like this list, is
	 * over the area where the scroll bar would be.
	 */
	protected boolean hoversMouseOverSlider(int mouseX, int mouseY) {
		if (mouseY >= 0 && mouseY <= height && mouseX > width - sliderWidth
			&& mouseX <= width) {
			return true;
		}
		return false;
	}

	protected void drawBackground(int posX, int posY, int mouseX, int mouseY) {
		GL11.glDisable(GL11.GL_BLEND);
		Minecraft.getMinecraft().getTextureManager().bindTexture(
			MHFCRegQuestVisual.CLICKABLE_LIST);
		float texheight = height;
		if (drawSmallestBounds)
			texheight = Math.min(size() * itemHeight, texheight);
		MHFCGuiUtil.drawTexturedRectangle(posX, posY, width, texheight, 0, 0,
			0.5f, 1.0f);
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
			MHFCGuiUtil.drawTexturedRectangle(posX + width - BORDER_WIDTH, posY
				+ selectionYMin, BORDER_WIDTH, selectionYHeight, 0.625f, 0.5f,
				0.125f, 0.25f);
		}
		GL11.glEnable(GL11.GL_BLEND);
	}

	protected void drawListItems(int posX, int posY, int mouseX, int mouseY) {
		Minecraft m = Minecraft.getMinecraft();
		for (int i = (int) (scrollAmount / itemHeight); i < this.size(); i++) {
			GuiListItem item = this.get(i);
			item.draw(posX, posY - (int) (scrollAmount - i * itemHeight),
				width, (int) itemHeight, m, i == selected, alignment);
		}
	}

	/**
	 * Sets the item height to a number, so that all elements in the list are
	 * displayed.
	 */
	protected void recalculateItemHeight() {
		itemHeight = (float) height / size();
	}

	@Override
	public boolean handleClick(int relativeX, int relativeY, int button) {
		if (!visible || relativeX < 0 || relativeX >= width || relativeY < 0
			|| relativeY >= height)
			return false;
		if (isMouseOnSlider(relativeX, relativeY)) {
			mouseClickX = relativeX;
			mouseClickY = relativeY;
			mouseClickMoveX = mouseClickX;
			mouseClickMoveY = mouseClickY;
		} else {
			int selec = (int) ((relativeY + scrollAmount) / itemHeight);
			if (selec >= size())
				return false;
			setSelected(selec);
		}
		return true;
	}

	@Override
	public void handleMovementMouseDown(int mouseX, int mouseY, int button,
		long timeDiff) {
		if (isMouseOnSlider(mouseClickX, mouseClickY)) {
			scrollAmount += (mouseY - mouseClickMoveY) / (float) height
				* getFullHeight();
			scrollAmount = Math.min(scrollAmount, getFullHeight() - height);
			scrollAmount = Math.max(scrollAmount, 0f);
			isSliderDragged = true;
			mouseClickMoveX = mouseX;
			mouseClickMoveY = mouseY;
		}
	}

	@Override
	public void handleMouseUp(int mouseX, int mouseY, int id) {
		isSliderDragged = false;
	}

	@Override
	public void handleMovement(int mouseX, int mouseY) {
	}

	/**
	 * Returns which item id is selected or -1 if none
	 * 
	 */
	public int getSelected() {
		return selected;
	}

	/**
	 * Side effect, increase selection index when insertion happened before it.
	 * If you insert before 0 and catch exceptions, the selection index might be
	 * at a different position than expected
	 */
	@Override
	public void add(int index, Item element) {
		if (index <= selected)
			selected++;
		super.add(index, element);
	}

	/**
	 * See {@link ClickableGuiList#remove(int)}
	 */
	@Override
	public boolean remove(Object o) {
		int index = indexOf(o);
		if (index < selected)
			selected--;
		if (index == selected)
			selected = -1;
		return super.remove(o);
	}

	/**
	 * Side effect, decreases selection index when deletion occurs before it.
	 * Selects -1 when the selected element is removed. If you delete before 0
	 * and catch exceptions, the selection index might be at a different
	 * position than expected.
	 */
	@Override
	public Item remove(int index) {
		if (index < selected)
			selected--;
		if (index == selected)
			selected = -1;
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

}
