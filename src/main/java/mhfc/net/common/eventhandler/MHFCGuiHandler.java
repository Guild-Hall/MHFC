package mhfc.net.common.eventhandler;

import mhfc.net.MHFCMain;
import mhfc.net.client.container.ContainerHunterBench;
import mhfc.net.client.gui.GuiHunterBench;
import mhfc.net.client.gui.block.GuiBlockExploreArea;
import mhfc.net.client.gui.quests.GuiQuestBoard;
import mhfc.net.client.gui.quests.GuiQuestGiver;
import mhfc.net.client.gui.quests.QuestStatusInventory;
import mhfc.net.common.core.registry.MHFCContainerRegistry;
import mhfc.net.common.tile.TileExploreArea;
import mhfc.net.common.tile.TileHunterBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class MHFCGuiHandler implements IGuiHandler {
	public static final MHFCGuiHandler instance = new MHFCGuiHandler();

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == MHFCContainerRegistry.gui_hunterbench_id) {
			TileEntity tE = world.getTileEntity(new BlockPos(x, y, z));
			if (tE instanceof TileHunterBench) {
				return new ContainerHunterBench(player.inventory, world, (TileHunterBench) tE, x, y, z);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case MHFCContainerRegistry.gui_hunterbench_id:
			TileEntity tE = world.getTileEntity(new BlockPos(x, y, z));
			if (tE instanceof TileHunterBench) {
				return new GuiHunterBench(player.inventory, world, (TileHunterBench) tE, x, y, z);
			} else {
				MHFCMain.logger().debug(
						"Tried to open hunter bench gui for block at {} {} {} which does not have a hunter bench tile entity",
						x,
						y,
						z);
			}
			break;
		case MHFCContainerRegistry.gui_questgiver_id:
			return GuiQuestGiver.getScreen(x, player);
		case MHFCContainerRegistry.gui_questboard_id:
			return GuiQuestBoard.getQuestBoard(player);
		case MHFCContainerRegistry.gui_queststatus_id:
			return new QuestStatusInventory(player);
		case MHFCContainerRegistry.gui_changearea_id:
			return getChangeAreaGui(world, x, y, z);
		}
		return null;
	}

	private GuiBlockExploreArea getChangeAreaGui(World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		if (!(tileEntity instanceof TileExploreArea)) {
			MHFCMain.logger().debug(
					"Tried to open change area gui for block at {} {} {} which does not have a change area tile entity",
					x,
					y,
					z);
			return null;
		}
		return new GuiBlockExploreArea((TileExploreArea) tileEntity);
	}

}
