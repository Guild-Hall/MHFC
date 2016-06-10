package mhfc.net.client.gui.block;

import javax.vecmath.Vector2f;

import mhfc.net.client.gui.ClickableGuiList;
import mhfc.net.client.gui.GuiListItem;
import mhfc.net.client.gui.IMHFCGuiItem;
import mhfc.net.client.gui.MHFCGui;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.MessageExploreTileUpdate;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.tile.TileExploreArea;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

public class GuiBlockExploreArea extends MHFCGui {

	private static class QuestFlairItem extends GuiListItem {

		public final QuestFlair flair;

		public QuestFlairItem(QuestFlair flair) {
			this.flair = flair;
		}

		@Override
		public String getRepresentationString() {
			return flair.name().toLowerCase();
		}

	}

	private TileExploreArea tileEntity;
	private GuiTextField targetAreaText;
	private ClickableGuiList<QuestFlairItem> targetFlairList;
	private GuiButton saveButton;

	public GuiBlockExploreArea(TileExploreArea tE) {
		tileEntity = tE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		targetAreaText = new GuiTextField(this.fontRendererObj, width / 6, height / 4, 2 * width / 3, 20);
		targetFlairList = new ClickableGuiList<>(2 * width / 3, 60, 15);
		for (QuestFlair f : QuestFlair.values()) {
			targetFlairList.add(new QuestFlairItem(f));
		}
		targetFlairList.setSelected(tileEntity.getFlair().ordinal());
		addScreenComponent(targetFlairList, new Vector2f(width / 6, height / 2));
		saveButton = new GuiButton(0, width / 2 - 75, 3 * height / 4, 150, 20, I18n.format("gui.save"));
		buttonList.add(saveButton);
		targetAreaText.setText(tileEntity.getTargetAreaName());
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partial) {
		super.drawDefaultBackground();
		targetAreaText.drawTextBox();
		super.drawScreen(mouseX, mouseY, partial);
	}

	private String getTargetArea() {
		return targetAreaText.getText();
	}

	private QuestFlair getQuestFlair() {
		QuestFlairItem item = targetFlairList.getSelectedItem();
		return item == null ? null : item.flair;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id == 0 && button.enabled) {
			PacketPipeline.networkPipe
					.sendToServer(new MessageExploreTileUpdate(tileEntity, getTargetArea(), getQuestFlair()));
			mc.displayGuiScreen(null);
		}
	}

	@Override
	protected void keyTyped(char keyCode, int modifiers) {
		super.keyTyped(keyCode, modifiers);
		targetAreaText.textboxKeyTyped(keyCode, modifiers);
		saveButton.enabled = getQuestFlair() != null;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		super.mouseClicked(mouseX, mouseY, button);
		targetAreaText.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	protected void itemUpdated(IMHFCGuiItem item) {}

}
