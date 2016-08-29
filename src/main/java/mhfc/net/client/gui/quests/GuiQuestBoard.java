package mhfc.net.client.gui.quests;

import mhfc.net.client.gui.MHFCTabbedGui;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class GuiQuestBoard extends MHFCTabbedGui {
	public static final GuiQuestJoin questBoard = new GuiQuestJoin(Minecraft.getMinecraft().thePlayer);

	private GuiQuestJoin join;
	private GuiQuestManagement questMg;

	public GuiQuestBoard(GuiQuestJoin join, EntityPlayer accessor) {
		super(new Container() {
			@Override
			public boolean canInteractWith(EntityPlayer var1) {
				return false;
			}
		});
		questMg = new GuiQuestManagement(null, accessor);
		this.join = join;
		addTab(join, "");
		addTab(questMg, "");
		tabWidth = 0;
		xSize = 374;
		ySize = 220;
	}

	@Override
	public void setWorldAndResolution(Minecraft p_146280_1_, int p_146280_2_, int p_146280_3_) {
		join.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
		questMg.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
		super.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
	}

	@Override
	protected void drawInactiveTabIcons(int posX, int posY, int mouseX, int mouseY) {}

	@Override
	protected void drawActiveTabIcon(int posX, int posY, int mouseX, int mouseY) {}

	@Override
	protected void drawTabBackgroundLayer() {
		if (MHFCRegQuestVisual.hasPlayerQuest()) {
			setTab(1);
		} else {
			setTab(0);
		}
	}

	public static GuiQuestBoard getQuestBoard(EntityPlayer player) {
		return new GuiQuestBoard(questBoard, player);
	}
}
