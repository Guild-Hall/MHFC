package mhfc.heltrato.common.core.registry;

import mhfc.heltrato.client.render.block.RenderBBQSpit;
import mhfc.heltrato.client.render.block.RenderHunterBench;
import mhfc.heltrato.client.render.block.RenderStunTrap;
import mhfc.heltrato.common.tile.TileBBQSpit;
import mhfc.heltrato.common.tile.TileHunterBench;
import mhfc.heltrato.common.tile.TileStunTrap;
import cpw.mods.fml.client.registry.ClientRegistry;

public class MHFCRegRenderTile {
	
	public static void render(){
		
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileHunterBench.class, new RenderHunterBench());
		ClientRegistry.bindTileEntitySpecialRenderer(TileStunTrap.class, new RenderStunTrap());
		ClientRegistry.bindTileEntitySpecialRenderer(TileBBQSpit.class, new RenderBBQSpit());
	}

}
