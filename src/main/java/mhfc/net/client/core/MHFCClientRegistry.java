package mhfc.net.client.core;

import mhfc.net.MHFCMain;
import mhfc.net.client.core.registry.MHFCCapeRegistry;
import mhfc.net.client.core.registry.MHFCEntityRenderRegistry;
import mhfc.net.client.core.registry.MHFCItemRenderRegistry;
import mhfc.net.client.core.registry.MHFCRenderIDRegistry;
import mhfc.net.client.core.registry.MHFCSoundRegistry;
import mhfc.net.client.core.registry.MHFCTileRenderRegistry;
import mhfc.net.client.core.registry.MHFCWeaponRenderRegistry;

public class MHFCClientRegistry {

	static {
		MHFCMain.checkPreInitialized();
	}

	public static void init() {
		addRenderers();
		addSounds();
		addCapes();
	}

	private static void addRenderers() {
		MHFCTileRenderRegistry.init();
		MHFCEntityRenderRegistry.init();
		MHFCWeaponRenderRegistry.init();
		MHFCItemRenderRegistry.init();
		MHFCRenderIDRegistry.init();
		MHFCMain.logger.info("Renderers registerd");
	}

	private static void addSounds() {
		MHFCSoundRegistry.init();
		MHFCMain.logger.info("Sounds registerd");
	}

	private static void addCapes() {
		MHFCCapeRegistry.init();
		MHFCMain.logger.info("Capes registerd");
	}
}
