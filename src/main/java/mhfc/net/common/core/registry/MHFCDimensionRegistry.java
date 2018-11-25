package mhfc.net.common.core.registry;

import mhfc.net.MHFCMain;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.MHFCWorldData;
import mhfc.net.common.world.WorldProviderQuesting;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.controller.IAreaManager;
import mhfc.net.common.world.village.QuestGiverHut;
import mhfc.net.common.world.village.VillageCreationHandler;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletionStage;

public class MHFCDimensionRegistry {
	private static Map<Integer, QuestFlair> worldIDToFlair;
	private static Map<QuestFlair, Integer> flairToWorldID;
	private static DimensionType questingType;

	private MHFCDimensionRegistry() {}

	static {
		worldIDToFlair = new HashMap<>(QuestFlair.values().length);
		flairToWorldID = new EnumMap<>(QuestFlair.class);
	}

	public static void init() {
		int dimHandlerId = MHFCMain.config().getDimensionHandlerID();
		questingType = DimensionType
				.register("MHFC questing", "_mhfc", dimHandlerId, WorldProviderQuesting.class, false);

		for (QuestFlair flair : QuestFlair.values()) {
			Integer worldID = DimensionManager.getNextFreeDimId();
			worldIDToFlair.put(worldID, flair);
			flairToWorldID.put(flair, worldID);
			DimensionManager.registerDimension(worldID, questingType);
		}
		VillagerRegistry.instance().registerVillageCreationHandler(new VillageCreationHandler());
		MapGenStructureIO.registerStructureComponent(QuestGiverHut.class, "mhfc.questGiverHut");
	}

	public static DimensionType getDimensionType() {
		return questingType;
	}

	public static int getQuestingDimensionID(QuestFlair flair) {
		return flairToWorldID.get(Objects.requireNonNull(flair));
	}

	public static QuestFlair getQuestingFlair(int worldId) {
		return worldIDToFlair.get(worldId);
	}

	public static WorldServer getServerFor(QuestFlair flair) {
		int id = getQuestingDimensionID(flair);
		return FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(id);
	}

	public static IAreaManager getAreaManager(QuestFlair flair) {
		WorldServer server = getServerFor(flair);
		return MHFCWorldData.retrieveManagerForWorld(server);
	}

	public static CompletionStage<IActiveArea> getUnusedInstance(IAreaType type, QuestFlair questFlair) {
		return getAreaManager(questFlair).getUnusedInstance(type);
	}

	public static int getWorldIDFor(QuestFlair flair) {
		return getQuestingDimensionID(flair);
	}
}
