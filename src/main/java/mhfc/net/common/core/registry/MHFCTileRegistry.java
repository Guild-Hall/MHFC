package mhfc.net.common.core.registry;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.tile.TileBBQSpit;
import mhfc.net.common.tile.TileExploreArea;
import mhfc.net.common.tile.TileHunterBench;
import mhfc.net.common.tile.TileQuestBoard;
import mhfc.net.common.tile.TileStunTrap;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MHFCTileRegistry {
	public static void init() {
		GameRegistry.registerTileEntity(TileHunterBench.class, ResourceInterface.tile_huntersbench_id);
		GameRegistry.registerTileEntity(TileStunTrap.class, ResourceInterface.tile_stuntrap_id);
		GameRegistry.registerTileEntity(TileBBQSpit.class, ResourceInterface.tile_bbqspit_id);
		GameRegistry.registerTileEntity(TileQuestBoard.class, ResourceInterface.tile_questboard_id);
		GameRegistry.registerTileEntity(TileExploreArea.class, ResourceInterface.tile_exploreArea_id);
	}
}
