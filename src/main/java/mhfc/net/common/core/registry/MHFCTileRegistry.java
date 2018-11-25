package mhfc.net.common.core.registry;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.tile.TileBBQSpit;
import mhfc.net.common.tile.TileExploreArea;
import mhfc.net.common.tile.TileHunterBench;
import mhfc.net.common.tile.TileQuestBoard;
import mhfc.net.common.tile.TileStunTrap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MHFCTileRegistry {
	public static void init() {
		GameRegistry.registerTileEntity(TileHunterBench.class, new ResourceLocation(ResourceInterface.tile_huntersbench_id));
		GameRegistry.registerTileEntity(TileStunTrap.class, new ResourceLocation(ResourceInterface.tile_stuntrap_id));
		GameRegistry.registerTileEntity(TileBBQSpit.class, new ResourceLocation(ResourceInterface.tile_bbqspit_id));
		GameRegistry.registerTileEntity(TileQuestBoard.class, new ResourceLocation(ResourceInterface.tile_questboard_id));
		GameRegistry.registerTileEntity(TileExploreArea.class, new ResourceLocation(ResourceInterface.tile_exploreArea_id));
	}
}
