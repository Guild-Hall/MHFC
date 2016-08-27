package mhfc.net.client.core;

import mhfc.net.MHFCMain;
import mhfc.net.client.core.registry.MHFCEntityRenderRegistry;
import mhfc.net.client.core.registry.MHFCItemRenderRegistry;
import mhfc.net.client.core.registry.MHFCTileRenderRegistry;
import mhfc.net.client.core.registry.MHFCWeaponRenderRegistry;
import mhfc.net.client.gui.hud.RenderEventListener;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import net.minecraftforge.common.MinecraftForge;

public class MHFCClientRegistry {

	public static void init() {
		addRenderers();
	}

	public static void staticInit() {
		addQuestDisplay();
		MHFCMain.initPhase.registerEntryCallback(e -> init());
	}

	private static void addQuestDisplay() {
		MHFCRegQuestVisual.staticInit();
		MHFCMain.logger().info("Quest Client registered");
	}

	private static void addRenderers() {
		MHFCTileRenderRegistry.init();
		MHFCEntityRenderRegistry.init();
		MHFCWeaponRenderRegistry.init();
		MHFCItemRenderRegistry.init();
		MinecraftForge.EVENT_BUS.register(new RenderEventListener());
		MHFCMain.logger().info("Renderers registered");
	}
}
