package mhfc.net.client.gui.quests;

import mhfc.net.client.gui.MHFCTabbedGui;
import mhfc.net.client.quests.MHFCRegQuestVisual;
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
		}, 2);
		this.questGet = questGet;
		questMg = new GuiQuestManagement(null, accessor);
		tabList.add(questGet);
		tabList.add(questMg);
		tabWidth = 0;
		xSize = 374;
		ySize = 220;
	}

	@Override
	public void setWorldAndResolution(Minecraft p_146280_1_, int p_146280_2_,
			int p_146280_3_) {
		questGet.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
		questMg.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
		super.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
	}

	@Override
	protected void drawInactiveTabIcons(int posX, int posY, int mouseX,
			int mouseY) {
	}

	@Override
	protected void drawActiveTabIcon(int posX, int posY, int mouseX, int mouseY) {
	}

	@Override
	protected void drawTabBackgroundLayer() {
		if (MHFCRegQuestVisual.hasPlayerQuest())
			setTab(1);
		else
			setTab(0);

	}

}
