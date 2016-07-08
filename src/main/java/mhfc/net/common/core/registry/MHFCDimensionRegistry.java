package mhfc.net.common.core.registry;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import mhfc.net.MHFCMain;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.WorldProviderQuesting;
import mhfc.net.common.world.gen.ChunkManagerQuesting;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class MHFCDimensionRegistry {
	private static Map<Integer, QuestFlair> worldIDToFlair;
	private static Map<QuestFlair, Integer> flairToWorldID;

	private MHFCDimensionRegistry() {}

	static {
		worldIDToFlair = new HashMap<>(QuestFlair.values().length);
		flairToWorldID = new EnumMap<>(QuestFlair.class);
	}

	public static void init() {
		int dimHandlerId = MHFCMain.config().getDimensionHandlerID();
		DimensionManager.registerProviderType(dimHandlerId, WorldProviderQuesting.class, false);

		for (QuestFlair flair : QuestFlair.values()) {
			Integer worldID = DimensionManager.getNextFreeDimId();
			worldIDToFlair.put(worldID, flair);
			flairToWorldID.put(flair, worldID);
			DimensionManager.registerDimension(worldID, dimHandlerId);
		}
	}

	public static int getQuestingDimensionID(QuestFlair flair) {
		return flairToWorldID.get(Objects.requireNonNull(flair));
	}

	public static QuestFlair getQuestingFlair(int worldId) {
		return worldIDToFlair.get(worldId);
	}

	public static ChunkManagerQuesting getQuestingDimensionChunkManager(QuestFlair flair) {
		WorldServer server = MinecraftServer.getServer()
				.worldServerForDimension(MHFCDimensionRegistry.getQuestingDimensionID(flair));
		ChunkManagerQuesting manager = (ChunkManagerQuesting) server.getWorldChunkManager();
		return manager;
	}
}
