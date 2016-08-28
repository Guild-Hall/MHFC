package mhfc.net.common.core.registry;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import mhfc.net.MHFCMain;
import mhfc.net.common.core.data.QuestDescriptionRegistry;
import mhfc.net.common.core.directors.DirectorLoadQuestsFromLocal;
import mhfc.net.common.core.registry.MHFCQuestRegistry.RunningSubscriptionHandler;
import mhfc.net.common.eventhandler.MHFCInteractionHandler.MHFCInteractionModReloadEvent;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.quest.MessageQuestInit;
import mhfc.net.common.quests.QuestFactories;
import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.api.QuestDefinition;
import mhfc.net.common.quests.descriptions.DefaultQuestDescription;
import mhfc.net.common.quests.world.QuestFlair;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * The registry for quests and quest goals. It will read some source files on init, these war written in the json
 * format. The name for the primary variable of {@link GoalDefinition} is "type", see {@link QuestFactories} for further
 * information.<br>
 * For {@link DefaultQuestDescription} the names are as following: "goal", "name", "reward", "fee", "areaID",
 * "description", "maxPartySize", "timeLimit", "type", "client", "aims", "fails", only the ones until areaID are
 * mandatory.<br>
 * The allowed flairs can be found at {@link QuestFlair}
 */

public class MHFCQuestBuildRegistry {

	private static QuestDescriptionRegistry dataObject;

	public static final String KEY_TYPE = "type";
	public static final String KEY_DATA = "data";

	public static final String KEY_ORDERED_GROUPS = "groupDisplayOrder";
	public static final String KEY_GROUP_MAPPING = "groups";

	public static final String KEY_GROUPS = "groups";
	public static final String KEY_GOAL_DESCRIPTION = "goalDescription";
	public static final String KEY_QUEST_DESCRIPTION = "questDescription";

	public static final String GOAL_CHAIN_TYPE = "chain";
	public static final String GOAL_FORK_TYPE = "fork";
	public static final String GOAL_DEATH_RESTRICTION_TYPE = "death";
	public static final String GOAL_HUNTING_TYPE = "hunting";
	public static final String GOAL_TIME_TYPE = "time";

	public static final String QUEST_DEFAULT = "default";
	public static final String QUEST_RUNNING = "running";

	public static final String VISUAL_DEFAULT = "default";

	public static final String QUEST_TYPE_HUNTING = "mhfc.quests.type.hunting";
	public static final String QUEST_TYPE_GATHERING = "mhfc.quests.type.gathering";
	public static final String QUEST_TYPE_EPIC_HUNTING = "mhfc.quests.type.epichunting";
	public static final String QUEST_TYPE_KILLING = "mhfc.quests.type.killing";

	public static class PlayerConnectionHandler {

		@SubscribeEvent
		public void onPlayerJoin(PlayerLoggedInEvent logIn) {
			EntityPlayerMP playerMP = (EntityPlayerMP) logIn.player;
			PacketPipeline.networkPipe.sendTo(new MessageQuestInit(dataObject), playerMP);
			RunningSubscriptionHandler.sendAllTo(playerMP);
		}
	}

	public static void init() {
		dataObject = new QuestDescriptionRegistry();
		MHFCQuestBuildRegistry.loadQuestsFromFiles();
		FMLCommonHandler.instance().bus().register(new PlayerConnectionHandler());
		MHFCMain.logger().info("Quest loaded");
	}

	private static void loadQuestsFromFiles() {
		DirectorLoadQuestsFromLocal director = new DirectorLoadQuestsFromLocal();
		director.construct(dataObject);
		MHFCQuestBuildRegistry.logStats(dataObject);
	}

	private static void logStats(QuestDescriptionRegistry dataObject) {
		int numberQuests = dataObject.getFullQuestDescriptionMap().size();
		int numberGroups = dataObject.getGroupsInOrder().size();
		String output = String.format("Server loaded %d quests in %d groups.", numberQuests, numberGroups);
		MHFCMain.logger().info(output);
	}

	@SubscribeEvent
	void onModReload(MHFCInteractionModReloadEvent event) {
		dataObject.clearData();
		MHFCQuestBuildRegistry.loadQuestsFromFiles();

		Iterator<EntityPlayerMP> it = FMLCommonHandler.instance().getMinecraftServerInstance()
				.getConfigurationManager().playerEntityList.iterator();
		while (it.hasNext()) {
			EntityPlayerMP player = it.next();
			PacketPipeline.networkPipe.sendTo(new MessageQuestInit(dataObject), player);
		}
		MHFCMain.logger().info("Quests reloaded");
	}

	public static GoalDefinition getGoalDescription(String id) {
		return dataObject.getGoalDescription(id);
	}

	public static QuestDefinition getQuestDescription(String id) {
		return dataObject.getQuestDescription(id);
	}

	public static List<String> getGroupList() {
		return dataObject.getGroupsInOrder();
	}

	public static Set<String> getQuestIdentifiersFor(String group) {
		return dataObject.getQuestIdentifiersFor(group);
	}
}
