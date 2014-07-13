package mhfc.net.common.core.registry;

import mhfc.net.common.tile.TileArmorStand;
import mhfc.net.common.tile.TileArmorStandBase;
import mhfc.net.common.tile.TileBBQSpit;
import mhfc.net.common.tile.TileHunterBench;
import mhfc.net.common.tile.TileStunTrap;
import cpw.mods.fml.common.registry.GameRegistry;

public class MHFCRegTile {

	public static void init() {

		GameRegistry
				.registerTileEntity(TileHunterBench.class, "Hunter's Bench");
		GameRegistry.registerTileEntity(TileStunTrap.class, "Stun Trap");
		GameRegistry.registerTileEntity(TileArmorStandBase.class,
				"Armor Stand Base");
		GameRegistry.registerTileEntity(TileArmorStand.class, "Armor Stand");
		GameRegistry.registerTileEntity(TileBBQSpit.class, "BBQ Spit");

	}

}
