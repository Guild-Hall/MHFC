package mhfc.net.common.core.registry;

import java.util.*;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mhfc.net.MHFCMain;
import mhfc.net.common.eventhandler.MHFCInteractionHandler.MHFCInteractionModReloadEvent;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.packet.MessageQuestScreenInit;
import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.QuestDescription;
import mhfc.net.common.quests.api.QuestFactory;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * The registry for quests and quest goals. It will read some source files on
 * init, these war written in the json format. The name for the primary variable
 * of {@link GoalDescription} is "type", see {@link QuestFactory} for further
 * information.<br>
 * For {@link QuestDescription} the names are as following: "goal", "name",
 * "reward", "fee", "areaID", "description", "maxPartySize", "timeLimit",
 * "type", "client", "aims", "fails", only the ones until areaID are mandatory.
 */

public class MHFCQuestBuildRegistry {

	public static final HashMap<String, QuestDescription> questDescriptions = new HashMap<String, QuestDescription>();
	public static final Map<String, List<String>> groupMapping = new HashMap<String, List<String>>();
	public static final List<String> groupIDs = new ArrayList<String>();
	public static final HashMap<String, GoalDescription> goalDescriptions = new HashMap<String, GoalDescription>();

	public static final String GOAL_CHAIN_TYPE = "chain";
	public static final String GOAL_FORK_TYPE = "fork";
	public static final String GOAL_DEATH_RESTRICTION_TYPE = "death";
	public static final String GOAL_HUNTING_TYPE = "hunting";
	public static final String GOAL_TIME_TYPE = "time";

	public static final String QUEST_TYPE_HUNTING = "mhfc.quests.type.hunting";
	public static final String QUEST_TYPE_GATHERING = "mhfc.quests.type.gathering";
	public static final String QUEST_TYPE_EPIC_HUNTING = "mhfc.quests.type.epichunting";
	public static final String QUEST_TYPE_KILLING = "mhfc.quests.type.killing";

	public static void init() {
		loadQuests();
		FMLCommonHandler.instance().bus().register(
			new MHFCQuestBuildRegistry());
		MHFCMain.logger.info("Quest loaded");
	}

	@SubscribeEvent
	void onModReload(MHFCInteractionModReloadEvent event) {
		MHFCQuestBuildRegistry.questDescriptions.clear();
		MHFCQuestBuildRegistry.goalDescriptions.clear();
		MHFCQuestBuildRegistry.groupIDs.clear();
		MHFCQuestBuildRegistry.groupMapping.clear();
		MHFCQuestBuildRegistry.loadQuests();

		Iterator<?> it = FMLCommonHandler.instance()
			.getMinecraftServerInstance()
			.getConfigurationManager().playerEntityList.iterator();
		while (it.hasNext()) {
			EntityPlayerMP player = (EntityPlayerMP) it.next();
			PacketPipeline.networkPipe.sendTo(new MessageQuestScreenInit(
				MHFCQuestBuildRegistry.groupMapping,
				MHFCQuestBuildRegistry.groupIDs), player);
		}
	}
}
