package mhfc.net.client.core;

import mhfc.net.MHFCMain;
import mhfc.net.client.core.registry.MHFCEntityRenderRegistry;
import mhfc.net.client.core.registry.MHFCItemRenderRegistry;
import mhfc.net.client.core.registry.MHFCTileRenderRegistry;
import mhfc.net.client.gui.hud.RenderEventListener;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import net.minecraftforge.common.MinecraftForge;

public class MHFCClientRegistry {
	public static void staticInit() {}

	static {
		MHFCRegQuestVisual.staticInit();
		MHFCItemRenderRegistry.staticInit();
		MHFCTileRenderRegistry.staticInit();
		MHFCEntityRenderRegistry.staticInit();

		MHFCMain.preInitPhase.registerEntryCallback(e -> preInit());
	}

	private static void preInit() {
		MinecraftForge.EVENT_BUS.register(new RenderEventListener());
	}

}
