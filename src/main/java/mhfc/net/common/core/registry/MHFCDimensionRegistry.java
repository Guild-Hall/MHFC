package mhfc.net.common.core.registry;

import mhfc.net.MHFCMain;
import mhfc.net.common.world.QuestingWorldProvider;
import net.minecraftforge.common.DimensionManager;

public class MHFCDimensionRegistry {
	private static MHFCMain mod;

	static {
		MHFCMain.checkPreInitialized();
		MHFCDimensionRegistry.mod = MHFCMain.instance;
		MHFCDimensionRegistry.mod.getClass();
	}

	public static void init() {
		int dimHandlerId = MHFCMain.config.getDimensionHandlerID();
		int dimensionID = DimensionManager.getNextFreeDimId();

		DimensionManager.registerProviderType(dimHandlerId, QuestingWorldProvider.class, false);
		DimensionManager.registerDimension(dimensionID, dimHandlerId);
	}
}
