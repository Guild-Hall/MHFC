package mhfc.net.common.quests.world;

import java.util.concurrent.CompletionStage;

import mhfc.net.common.core.registry.MHFCDimensionRegistry;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class GlobalAreaManager {
	public static final GlobalAreaManager instance = new GlobalAreaManager();

	public static GlobalAreaManager getInstance() {
		return instance;
	}

	private GlobalAreaManager() {}

	public CompletionStage<IActiveArea> getUnusedInstance(IAreaType type, QuestFlair questFlair) {
		return MHFCDimensionRegistry.getQuestingDimensionChunkManager(questFlair).getAreaManager()
				.getUnusedInstance(type);
	}

	public WorldServer getServerFor(QuestFlair flair) {
		int id = MHFCDimensionRegistry.getQuestingDimensionID(flair);
		return MinecraftServer.getServer().worldServerForDimension(id);
	}

	public int getWorldIDFor(QuestFlair flair) {
		return MHFCDimensionRegistry.getQuestingDimensionID(flair);
	}
}
