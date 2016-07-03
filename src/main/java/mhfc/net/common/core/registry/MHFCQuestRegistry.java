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
import mhfc.net.MHFCMain;
import mhfc.net.common.core.data.KeyToInstanceRegistryData;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.packet.MessageMHFCInteraction;
import mhfc.net.common.network.packet.MessageQuestRunningSubscription;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.network.packet.MessageQuestVisual.VisualType;
import mhfc.net.common.network.packet.MessageRequestQuestVisual;
import mhfc.net.common.quests.GeneralQuest;
import mhfc.net.common.quests.IVisualInformation;
import mhfc.net.common.quests.QuestVisualInformation;
import mhfc.net.common.quests.api.QuestDescription;
import mhfc.net.common.quests.api.QuestFactory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.ChatComponentText;

public class MHFCQuestRegistry {

	public static class RegistryRequestVisualHandler
			implements
			IMessageHandler<MessageRequestQuestVisual, MessageQuestVisual> {

		@Override
		public MessageQuestVisual onMessage(MessageRequestQuestVisual message, MessageContext ctx) {
			String identifier = message.getIdentifier();
			QuestDescription description = MHFCQuestBuildRegistry.getQuestDescription(identifier);
			IVisualInformation info = (description == null
					? QuestVisualInformation.IDENTIFIER_ERROR
					: description.getVisualInformation());
			return new MessageQuestVisual(VisualType.RUNNING_QUEST, identifier, info);
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
				subscribers.add(player);
				sendAllTo(player);
			} else {
				subscribers.remove(player);
			}
			return null;
		}

		public static void sendAllTo(EntityPlayerMP player) {
			for (GeneralQuest q : questsRunning) {
				String identifier = runningQuestRegistry.getKey(q);
				MessageQuestVisual messageSent = new MessageQuestVisual(
						VisualType.RUNNING_QUEST,
						identifier,
						q.getRunningInformation());
				PacketPipeline.networkPipe.sendTo(messageSent, player);
			}
		}

		public static void sendToAll(MessageQuestVisual visual) {
			Iterator<EntityPlayerMP> iter = subscribers.iterator();
			while (iter.hasNext()) {
				PacketPipeline.networkPipe.sendTo(visual, iter.next());
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
			GeneralQuest q = playerQuest.get(player);
			if (q == null) {
				return;
			}
			q.quitPlayer(player);
			RunningSubscriptionHandler.subscribers.remove(player);
		}
	}

	private static int questIDCounter = 0;

	public static void onPlayerInteraction(EntityPlayerMP player, MessageMHFCInteraction message) {
		GeneralQuest quest;
		switch (message.getInteraction()) {
		case NEW_QUEST:
			quest = getQuestForPlayer(player);
			if (quest == null) {
				String registerFor = message.getOptions()[0] + "@" + player.getDisplayName() + "@" + questIDCounter++;
				GeneralQuest newQuest = QuestFactory.constructQuest(
						MHFCQuestBuildRegistry.getQuestDescription(message.getOptions()[0]),
						player,
						registerFor);
				if (newQuest == null) {
					player.addChatMessage(new ChatComponentText("Quest not found"));
					return;
				} else {
					PacketPipeline.networkPipe.sendTo(
							new MessageQuestVisual(
									VisualType.PERSONAL_QUEST,
									registerFor,
									newQuest.getVisualInformation()),
							player);
				}
			} else {
				player.addChatMessage(
						new ChatComponentText("You already are on quest " + getIdentifierForQuest(quest)));
				String id = getIdentifierForQuest(quest);
				PacketPipeline.networkPipe.sendTo(
						new MessageQuestVisual(VisualType.PERSONAL_QUEST, id, quest.getRunningInformation()),
						player);
			}
			break;
		case ACCEPT_QUEST:
			quest = getRunningQuest(message.getOptions()[0]);
			if (quest != null) {
				quest.joinPlayer(player);
				player.addChatMessage(new ChatComponentText("You have accepted a quest"));
			} else {
				player.addChatMessage(new ChatComponentText("The quest you wanted to accept does not exist"));
			}
			break;
		case START_QUEST:
			quest = getQuestForPlayer(player);
			if (quest != null) {
				quest.voteStart(player);
				player.addChatMessage(new ChatComponentText("You have voted for starting the quest"));
			}
			break;
		case END_QUEST:
			quest = getQuestForPlayer(player);
			if (quest != null) {
				quest.voteEnd(player);
				player.addChatMessage(new ChatComponentText("You have voted for ending the quest"));
			} else {
				sendResetPlayerVisual(player);
			}
			break;
		case FORFEIT_QUEST:
			quest = getQuestForPlayer(player);
			if (quest != null) {
				quest.quitPlayer(player);
				player.addChatMessage(new ChatComponentText("You have left the quest"));
			} else {
				sendResetPlayerVisual(player);
			}
			break;
		case MOD_RELOAD:
			for (GeneralQuest genQ : questsRunning) {
				RunningSubscriptionHandler.sendToAll(
						new MessageQuestVisual(
								VisualType.RUNNING_QUEST,
								getIdentifierForQuest(genQ),
								genQ.getRunningInformation()));
			}
			break;
		default:
			break;
		}
	}

	private static void sendResetPlayerVisual(EntityPlayerMP player) {
		PacketPipeline.networkPipe.sendTo(new MessageQuestVisual(VisualType.PERSONAL_QUEST, "", null), player);
	}

	protected static Map<EntityPlayer, GeneralQuest> playerQuest = new HashMap<>();
	protected static List<GeneralQuest> questsRunning = new ArrayList<>();
	protected static KeyToInstanceRegistryData<String, GeneralQuest> runningQuestRegistry = new KeyToInstanceRegistryData<>();

	public static void init() {
		FMLCommonHandler.instance().bus().register(new PlayerConnectionHandler());
	}

	public static GeneralQuest getRunningQuest(String string) {
		return runningQuestRegistry.getData(string);
	}

	/**
	 * Get the quest on which a player is on. If the player is on no quest then null is returned.
	 */
	public static GeneralQuest getQuestForPlayer(EntityPlayer player) {
		return playerQuest.get(player);
	}

	/**
	 * Returns all quests that are running at the moment.
	 */
	public static List<GeneralQuest> getRunningQuests() {
		return questsRunning;
	}

	/**
	 * Returns the identifier for a quest
	 */
	public static String getIdentifierForQuest(GeneralQuest quest) {
		return runningQuestRegistry.getKey(quest);
	}

	/**
	 * Sets the quest for a player, use null to remove the entry
	 *
	 */
	public static void setQuestForPlayer(EntityPlayer player, GeneralQuest generalQuest) {
		if (generalQuest == null) {
			playerQuest.remove(player);
		} else {
			playerQuest.put(player, generalQuest);
		}
	}

	/**
	 * Should be called when a new quest was started
	 */
	public static boolean regRunningQuest(GeneralQuest generalQuest, String identifier) {
		questsRunning.add(generalQuest);
		MHFCMain.logger.debug(questsRunning.size() + " quests are running at the moment.");
		if (!runningQuestRegistry.offerMapping(identifier, generalQuest)) {
			return false;
		}
		MessageQuestVisual message = new MessageQuestVisual(
				VisualType.RUNNING_QUEST,
				identifier,
				generalQuest.getRunningInformation());
		RunningSubscriptionHandler.sendToAll(message);
		return true;
	}

	/**
	 * Should be called when a quest is no longer running and was terminated. Might also be called to ensure client
	 * sync. Returns true if the quest was registered.
	 */
	public static boolean deregRunningQuest(GeneralQuest generalQuest) {
		boolean wasRunning = questsRunning.remove(generalQuest);
		if (wasRunning) {
			String key = runningQuestRegistry.removeKey(generalQuest);
			MessageQuestVisual message = new MessageQuestVisual(VisualType.RUNNING_QUEST, key, null);
			RunningSubscriptionHandler.sendToAll(message);
		}
		generalQuest.close();
		return wasRunning;
	}

	/*
	 * Should be called when major changes to a quest were made that require resending the information to all clients
	 */
	public static void questUpdated(GeneralQuest q) {
		String identifier = runningQuestRegistry.getKey(q);
		if (q == null || identifier == null) {
			return;
		}
		MessageQuestVisual message = new MessageQuestVisual(
				VisualType.RUNNING_QUEST,
				identifier,
				q.getRunningInformation());
		RunningSubscriptionHandler.sendToAll(message);
	}
}
