package mhfc.net.common.core.registry;

import mhfc.net.client.render.block.RenderArmorStand;
import mhfc.net.client.render.block.RenderArmorStandBase;
import mhfc.net.client.render.block.RenderBBQSpit;
import mhfc.net.client.render.block.RenderHunterBench;
import mhfc.net.client.render.block.RenderStunTrap;
import mhfc.net.common.tile.TileArmorStand;
import mhfc.net.common.tile.TileArmorStandBase;
import mhfc.net.common.tile.TileBBQSpit;
import mhfc.net.common.tile.TileHunterBench;
import mhfc.net.common.tile.TileStunTrap;
import cpw.mods.fml.client.registry.ClientRegistry;

public class MHFCRegRenderTile {
	
	public static void render(){
		
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileHunterBench.class, new RenderHunterBench());
		ClientRegistry.bindTileEntitySpecialRenderer(TileStunTrap.class, new RenderStunTrap());
		ClientRegistry.bindTileEntitySpecialRenderer(TileArmorStandBase.class, new RenderArmorStandBase());
		ClientRegistry.bindTileEntitySpecialRenderer(TileArmorStand.class, new RenderArmorStand());
		ClientRegistry.bindTileEntitySpecialRenderer(TileBBQSpit.class, new RenderBBQSpit());
	}

}
