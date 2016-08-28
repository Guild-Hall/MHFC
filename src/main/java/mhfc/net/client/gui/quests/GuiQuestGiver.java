package mhfc.net.client.gui.quests;

import java.util.List;

import mhfc.net.client.gui.MHFCTabbedGui;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class GuiQuestGiver extends MHFCTabbedGui {
	private GuiQuestNew questGet;
	private GuiQuestManagement questMg;

	public GuiQuestGiver(EntityPlayer accessor, GuiQuestNew questGet) {
		super(new Container() {
			@Override
			public boolean canInteractWith(EntityPlayer var1) {
				return false;
			}
		});
		this.questGet = questGet;
		questMg = new GuiQuestManagement(null, accessor);
		addTab(questGet, "");
		addTab(questMg, "");
		tabWidth = 0;
		xSize = 374;
		ySize = 220;
	}

	@Override
	public void setWorldAndResolution(Minecraft p_146280_1_, int p_146280_2_, int p_146280_3_) {
		questGet.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
		questMg.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
		super.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
	}

	@Override
	protected void drawInactiveTabIcons(int posX, int posY, int mouseX, int mouseY) {}

	@Override
	protected void drawActiveTabIcon(int posX, int posY, int mouseX, int mouseY) {}

	@Override
	protected void drawTabBackgroundLayer() {
		setTab(MHFCRegQuestVisual.hasPlayerQuest() ? 1 : 0);
	}

	public static GuiQuestGiver getScreen(int i, EntityPlayer playerEntity) {
		// ignore i for now

		List<String> list = MHFCQuestBuildRegistry.getGroupList();
		GuiQuestNew newQuest = new GuiQuestNew(list, playerEntity);
		return new GuiQuestGiver(playerEntity, newQuest);
	}
}
