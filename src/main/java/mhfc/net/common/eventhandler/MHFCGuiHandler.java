package mhfc.net.common.eventhandler;

import mhfc.net.client.container.ContainerHunterBench;
import mhfc.net.client.gui.GuiHunterBench;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.tile.TileHunterBench;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class MHFCGuiHandler implements IGuiHandler {
	public static final MHFCGuiHandler instance = new MHFCGuiHandler();

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == MHFCReference.gui_hunterbench_id) {
			TileEntity tE = world.getTileEntity(x, y, z);
			if (tE instanceof TileHunterBench)
				return new ContainerHunterBench(player.inventory, world,
						(TileHunterBench) tE, x, y, z);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (ID) {
			case MHFCReference.gui_hunterbench_id :
				TileEntity tE = world.getTileEntity(x, y, z);
				if (tE instanceof TileHunterBench)
					return new GuiHunterBench(player.inventory, world,
							(TileHunterBench) tE, x, y, z);
				break;
			case MHFCReference.gui_questgiver_id :
				return MHFCRegQuestVisual.getScreen(x, player);
			case MHFCReference.gui_questboard_id :
				return MHFCRegQuestVisual.getQuestBoard(player);
		}
		return null;
	}

}
