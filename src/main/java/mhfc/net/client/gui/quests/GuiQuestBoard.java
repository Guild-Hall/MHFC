package mhfc.net.client.gui.quests;

import mhfc.net.client.gui.MHFCTabbedGui;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class GuiQuestBoard extends MHFCTabbedGui {

	private GuiQuestJoin join;
	private GuiQuestManagement questMg;

	public GuiQuestBoard(GuiQuestJoin join, EntityPlayer accessor) {
		super(new Container() {
			@Override
			public boolean canInteractWith(EntityPlayer var1) {
				return false;
			}
		}, 2);
		questMg = new GuiQuestManagement(null, accessor);
		this.join = join;
		tabList.add(join);
		tabList.add(questMg);
		tabWidth = 0;
	}

	@Override
	public void setWorldAndResolution(Minecraft p_146280_1_, int p_146280_2_,
			int p_146280_3_) {
		join.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
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
