package mhfc.net.common.core.registry;

import mhfc.net.common.tile.TileArmorStand;
import mhfc.net.common.tile.TileArmorStandBase;
import mhfc.net.common.tile.TileBBQSpit;
import mhfc.net.common.tile.TileHunterBench;
import mhfc.net.common.tile.TileStunTrap;
import mhfc.net.common.util.lib.MHFCReference;
import cpw.mods.fml.common.registry.GameRegistry;

public class MHFCRegTile {

	public static void init() {

		GameRegistry.registerTileEntity(TileHunterBench.class,
				MHFCReference.tile_huntersbench_id);
		GameRegistry.registerTileEntity(TileStunTrap.class,
				MHFCReference.tile_stuntrap_id);
		GameRegistry.registerTileEntity(TileArmorStandBase.class,
				MHFCReference.tile_armorstandbase_id);
		GameRegistry.registerTileEntity(TileArmorStand.class,
				MHFCReference.tile_armorstand_id);
		GameRegistry.registerTileEntity(TileBBQSpit.class,
				MHFCReference.tile_bbqspit_id);

	}

}
