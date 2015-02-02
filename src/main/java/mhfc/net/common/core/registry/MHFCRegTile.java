package mhfc.net.common.core.registry;

import mhfc.net.common.tile.TileArmorStand;
import mhfc.net.common.tile.TileArmorStandBase;
import mhfc.net.common.tile.TileBBQSpit;
import mhfc.net.common.tile.TileHunterBench;
import mhfc.net.common.tile.TileStunTrap;
import cpw.mods.fml.common.registry.GameRegistry;

public class MHFCRegTile {
	
	private static GameRegistry tile;
	
	public static void init(){
		
		tile.registerTileEntity(TileHunterBench.class, "Hunter's Bench");
		tile.registerTileEntity(TileStunTrap.class, "Stun Trap");
		tile.registerTileEntity(TileArmorStandBase.class, "Armor Stand Base");
		tile.registerTileEntity(TileArmorStand.class, "Armor Stand");
		tile.registerTileEntity(TileBBQSpit.class, "BBQ Spit");
		
	}

}
