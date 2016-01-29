package mhfc.net.common.core.registry;

import mhfc.net.MHFCMain;
import mhfc.net.common.world.WorldProviderQuesting;
import mhfc.net.common.world.gen.ChunkManagerQuesting;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class MHFCDimensionRegistry {
	private static MHFCMain mod;

	private static int dimensionID = 0;
	private static boolean isInit = false;

	private MHFCDimensionRegistry() {}

	static {
		MHFCMain.checkPreInitialized();
		MHFCDimensionRegistry.mod = MHFCMain.instance;
		MHFCDimensionRegistry.mod.getClass();
	}

	public static void init() {
		int dimHandlerId = MHFCMain.config.getDimensionHandlerID();
		MHFCDimensionRegistry.dimensionID = DimensionManager.getNextFreeDimId();

		DimensionManager.registerProviderType(dimHandlerId, WorldProviderQuesting.class, false);
		DimensionManager.registerDimension(MHFCDimensionRegistry.dimensionID, dimHandlerId);

		MHFCDimensionRegistry.isInit = true;
	}

	public static int getQuestingDimensionID() {
		if (!MHFCDimensionRegistry.isInit) {
			throw new IllegalStateException("Not initialized yet");
		}
		return MHFCDimensionRegistry.dimensionID;
	}

	public static ChunkManagerQuesting getQuestingDimensionChunkManager() {
		WorldServer server = MinecraftServer.getServer().worldServerForDimension(dimensionID);
		ChunkManagerQuesting manager = (ChunkManagerQuesting) server.getWorldChunkManager();
		return manager;
	}
}
