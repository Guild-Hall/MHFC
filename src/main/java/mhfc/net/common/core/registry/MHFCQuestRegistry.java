package mhfc.net.common.core.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerConnectionFromClientEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerDisconnectionFromClientEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mhfc.net.common.core.data.KeyToInstanceRegistryData;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.quest.MessageMHFCInteraction;
import mhfc.net.common.network.message.quest.MessageMissionStatus;
import mhfc.net.common.network.message.quest.MessageMissionUpdate;
import mhfc.net.common.network.message.quest.MessageQuestRunningSubscription;
import mhfc.net.common.network.message.quest.MessageRequestMissionUpdate;
import mhfc.net.common.quests.Mission;
import mhfc.net.common.quests.QuestFactories;
import mhfc.net.common.quests.api.QuestDefinition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.ChatComponentText;

public class MHFCQuestRegistry {

	public static class RegistryRequestVisualHandler
			implements
			IMessageHandler<MessageRequestMissionUpdate, MessageMissionUpdate> {

		@Override
		public MessageMissionUpdate onMessage(MessageRequestMissionUpdate message, MessageContext ctx) {
			String identifier = message.getIdentifier();
			Mission runningQuest = MHFCQuestRegistry.getRunningQuest(identifier);
			if (runningQuest == null) {
				return null;
			}
			return runningQuest.createFullUpdateMessage();
		}
	}

	public static class RunningSubscriptionHandler
			implements
			IMessageHandler<MessageQuestRunningSubscription, IMessage> {
		private static Set<EntityPlayerMP> subscribers = new HashSet<>();

		public RunningSubscriptionHandler() {}

		@Override
		public IMessage onMessage(MessageQuestRunningSubscription message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			if (message.isSubscribed()) {
				boolean newSubscription = subscribers.add(player);
				if (newSubscription) {
					sendAllTo(player);
				}
			} else {
				subscribers.remove(player);
			}
			return null;
		}

		public static void sendAllTo(EntityPlayerMP player) {
			for (Mission q : questsRunning) {
				MessageMissionUpdate missionUpdate = q.createFullUpdateMessage();
				PacketPipeline.networkPipe.sendTo(missionUpdate, player);
			}
		}

		public static void sendToAll(IMessage message) {
			Iterator<EntityPlayerMP> iter = subscribers.iterator();
			while (iter.hasNext()) {
				PacketPipeline.networkPipe.sendTo(message, iter.next());
			}
		}
	}

	public static class PlayerConnectionHandler {

		@SubscribeEvent
		public void onPlayerJoin(ServerConnectionFromClientEvent logIn) {
			NetHandlerPlayServer playerNetHandler = (NetHandlerPlayServer) logIn.handler;
			RunningSubscriptionHandler.subscribers.add(playerNetHandler.playerEntity);
		}

		@SubscribeEvent
		public void onPlayerLeave(ServerDisconnectionFromClientEvent logOut) {
			NetHandlerPlayServer playerNetHandler = (NetHandlerPlayServer) logOut.handler;
			EntityPlayerMP player = playerNetHandler.playerEntity;
			Mission quest = getQuestForPlayer(player);
			if (quest == null) {
				return;
			}
			quest.quitPlayer(player);
			RunningSubscriptionHandler.subscribers.remove(player);
		}
	}

	private static int questIDCounter = 0;

	public static void onPlayerInteraction(EntityPlayerMP player, MessageMHFCInteraction message) {
		switch (message.getInteraction()) {
		case NEW_QUEST:
			createNewQuest(player, message);
			return;
		case ACCEPT_QUEST:
			acceptQuest(player, message);
			break;
		case START_QUEST:
			voteStartQuest(player);
			break;
		case END_QUEST:
			voteEndQuest(player);
			break;
		case FORFEIT_QUEST:
			forfeitQuest(player);
			break;
		case MOD_RELOAD:
			for (Mission genQ : questsRunning) {
				RunningSubscriptionHandler.sendToAll(genQ.createFullUpdateMessage());
			}
			break;
		default:
			break;
		}
	}

	private static void forfeitQuest(EntityPlayerMP player) {
		Mission quest = getQuestForPlayer(player);
		if (quest != null) {
			quest.quitPlayer(player);
			player.addChatMessage(new ChatComponentText("You have forfeited a quest"));
		} else {
			sendResetPlayerVisual(player);
		}
	}

	private static void voteEndQuest(EntityPlayerMP player) {
		Mission quest = getQuestForPlayer(player);
		if (quest != null) {
			quest.voteEnd(player);
			player.addChatMessage(new ChatComponentText("You have voted for ending the quest"));
		} else {
			sendResetPlayerVisual(player);
		}
	}

	private static void voteStartQuest(EntityPlayerMP player) {
		Mission quest = getQuestForPlayer(player);
		if (quest != null) {
			quest.voteStart(player);
			player.addChatMessage(new ChatComponentText("You have voted for starting the quest"));
		}
	}

	private static void acceptQuest(EntityPlayerMP player, MessageMHFCInteraction message) {
		Mission quest = getRunningQuest(message.getOptions()[0]);
		if (quest != null) {
			quest.joinPlayer(player);
			player.addChatMessage(new ChatComponentText("You have accepted a quest"));
		} else {
			player.addChatMessage(new ChatComponentText("The quest you wanted to accept does not exist"));
		}
	}

	private static void createNewQuest(EntityPlayerMP player, MessageMHFCInteraction message) {
		Mission quest = getQuestForPlayer(player);
		if (quest != null) {
			player.addChatMessage(new ChatComponentText("You already are on quest " + getIdentifierForQuest(quest)));
			PacketPipeline.networkPipe.sendTo(quest.createFullUpdateMessage(), player);
			return;
		}
		String questID = message.getOptions()[0];
		QuestDefinition questDescription = MHFCQuestBuildRegistry.getQuestDescription(questID);
		if (questDescription == null) {
			player.addChatMessage(new ChatComponentText("Quest with id[" + questID + "] not found"));
			return;
		}
		String missionID = questID + "@" + player.getDisplayName() + "@" + questIDCounter++;
		Mission newQuest = QuestFactories.constructQuest(questDescription, missionID);
		if (newQuest == null) {
			player.addChatMessage(new ChatComponentText("Quest with id[" + questID + "] could not be constructed"));
			return;
		}
		if (!newQuest.canJoin(player)) {
			newQuest.close();
			return;
		}
		MHFCQuestRegistry.startMission(questID, newQuest, missionID);
		newQuest.joinPlayer(player);
	}

	private static void sendResetPlayerVisual(EntityPlayerMP player) {
		String missionID = getMissionIDForPlayer(player);
		if (missionID == null) {
			return;
		}
		PacketPipeline.networkPipe.sendTo(MessageMissionStatus.departing(missionID), player);
	}

	protected static Map<EntityPlayer, String> playerQuest = new HashMap<>();
	protected static List<Mission> questsRunning = new ArrayList<>();
	protected static KeyToInstanceRegistryData<String, Mission> runningQuestRegistry = new KeyToInstanceRegistryData<>();

	public static void init() {
		FMLCommonHandler.instance().bus().register(new PlayerConnectionHandler());
	}

	public static Mission getRunningQuest(String string) {
		return runningQuestRegistry.getData(string);
	}

	/**
	 * Get the quest on which a player is on. If the player is on no quest then null is returned.
	 */
	public static Mission getQuestForPlayer(EntityPlayer player) {
		return runningQuestRegistry.getData(playerQuest.get(player));
	}

	/**
	 * Get the id of the mission the player is on. If the player is not currently on a mission, null is returned.
	 *
	 * @param player
	 * @return
	 */
	public static String getMissionIDForPlayer(EntityPlayer player) {
		return playerQuest.get(player);
	}

	/**
	 * Returns all quests that are running at the moment.
	 */
	public static List<Mission> getRunningQuests() {
		return questsRunning;
	}

	/**
	 * Returns the identifier for a quest
	 */
	public static String getIdentifierForQuest(Mission quest) {
		return runningQuestRegistry.getKey(quest);
	}

	/**
	 * Sets the quest for a player, use null to remove the entry
	 *
	 */
	public static void setQuestForPlayer(EntityPlayer player, Mission generalQuest) {
		if (generalQuest == null) {
			playerQuest.remove(player);
		} else {
			playerQuest.put(player, runningQuestRegistry.getKey(generalQuest));
		}
	}

	/**
	 * Should be called when a new quest was started
	 */
	private static boolean startMission(String questID, Mission mission, String missionID) {
		if (!runningQuestRegistry.offerMapping(missionID, mission)) {
			return false;
		}
		questsRunning.add(mission);
		MessageMissionStatus message = MessageMissionStatus.creation(questID, missionID);
		RunningSubscriptionHandler.sendToAll(message);
		return true;
	}

	/**
	 * Should be called when a quest is no longer running and was terminated. Might also be called to ensure client
	 * sync. Returns true if the quest was registered.
	 */
	public static boolean endMission(Mission mission) {
		boolean wasRunning = questsRunning.remove(mission);
		if (wasRunning) {
			String missionID = runningQuestRegistry.removeKey(mission);
			MessageMissionStatus message = MessageMissionStatus.destruction(missionID);
			RunningSubscriptionHandler.sendToAll(message);
		}
		mission.close();
		return wasRunning;
	}

	/**
	 * Should be called when major changes to a quest were made that require resending the information to all clients
	 */
	public static void questUpdated(Mission quest) {
		String missionID = runningQuestRegistry.getKey(quest);
		if (quest == null || missionID == null) {
			return;
		}
		MessageMissionUpdate message = quest.createFullUpdateMessage();
		RunningSubscriptionHandler.sendToAll(message);
	}
}
