package mhfc.net.common.core.registry;

import mhfc.net.common.tile.TileBBQSpit;
import mhfc.net.common.tile.TileHunterBench;
import mhfc.net.common.tile.TileStunTrap;
import mhfc.net.common.util.lib.MHFCReference;
import cpw.mods.fml.common.registry.GameRegistry;

public class MHFCTileRegistry {

	public static void init() {
		GameRegistry.registerTileEntity(TileHunterBench.class,
				MHFCReference.tile_huntersbench_id);
		GameRegistry.registerTileEntity(TileStunTrap.class,
				MHFCReference.tile_stuntrap_id);
		GameRegistry.registerTileEntity(TileBBQSpit.class,
				MHFCReference.tile_bbqspit_id);
	}
}
