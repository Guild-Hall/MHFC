package mhfc.net.client.core.registry;

import mhfc.net.MHFCMain;
import mhfc.net.client.render.block.RenderBBQSpit;
import mhfc.net.client.render.block.RenderStunTrap;
import mhfc.net.common.tile.TileBBQSpit;
import mhfc.net.common.tile.TileStunTrap;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class MHFCTileRenderRegistry {
	public static void staticInit() {}

	static {
		MHFCMain.initPhase.registerEntryCallback(e -> init());
	}

	private static void init() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileStunTrap.class, new RenderStunTrap());
		ClientRegistry.bindTileEntitySpecialRenderer(TileBBQSpit.class, new RenderBBQSpit());
	}

}
