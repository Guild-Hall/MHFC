package mhfc.net.client.core.registry;

import mhfc.net.client.render.block.RenderBBQSpit;
import mhfc.net.client.render.block.RenderHunterBench;
import mhfc.net.client.render.block.RenderQuestBoard;
import mhfc.net.client.render.block.RenderStunTrap;
import mhfc.net.common.tile.TileBBQSpit;
import mhfc.net.common.tile.TileHunterBench;
import mhfc.net.common.tile.TileQuestBoard;
import mhfc.net.common.tile.TileStunTrap;
import cpw.mods.fml.client.registry.ClientRegistry;

public class MHFCTileRenderRegistry {

	public static void init() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileHunterBench.class,
				new RenderHunterBench());
		ClientRegistry.bindTileEntitySpecialRenderer(TileStunTrap.class,
				new RenderStunTrap());
		ClientRegistry.bindTileEntitySpecialRenderer(TileBBQSpit.class,
				new RenderBBQSpit());
		ClientRegistry.bindTileEntitySpecialRenderer(TileQuestBoard.class,
				new RenderQuestBoard());
	}
}
