package mhfc.net.common.core.registry;

import com.google.common.base.Throwables;
import mhfc.net.MHFCMain;
import mhfc.net.common.core.data.KeyToInstanceRegistryData;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.handler.ThreadSafeMessageHandler;
import mhfc.net.common.network.message.quest.*;
import mhfc.net.common.quests.Mission;
import mhfc.net.common.quests.QuestFactories;
import mhfc.net.common.quests.api.IQuestDefinition;
import mhfc.net.common.util.DetachableResource;
import mhfc.net.common.util.services.IServiceKey;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.*;
import java.util.Map.Entry;

public class MHFCQuestRegistry {
	public static void staticInit() {}

	private static final IServiceKey<MHFCQuestRegistry> serviceAccess = RegistryWrapper.registerService(
			"quest manager",
			MHFCQuestRegistry::new,
			MHFCQuestRegistry::shutdown,
			MHFCMain.serverRunningPhase);

	public static class RegistryRequestVisualHandler
	implements
	IMessageHandler<MessageRequestMissionUpdate, MessageMissionUpdate> {

		@Override
		public MessageMissionUpdate onMessage(MessageRequestMissionUpdate message, MessageContext ctx) {
			String identifier = message.getIdentifier();
			Mission runningQuest = getRegistry().getMission(identifier);
			if (runningQuest == null) {
				return null;
			}
			return runningQuest.createFullUpdateMessage();
		}
	}

	public static class RunningSubscriptionHandler
			extends
			ThreadSafeMessageHandler<MessageQuestRunningSubscription, IMessage> {
		private static Set<EntityPlayerMP> subscribers = new HashSet<>();

		public RunningSubscriptionHandler() {}

		@Override
		public void handleLater(MessageQuestRunningSubscription message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			if (message.isSubscribed()) {
				boolean newSubscription = subscribers.add(player);
				if (newSubscription) {
					sendAllTo(player);
				}
			} else {
				subscribers.remove(player);
			}
		}

		public static void sendAllTo(EntityPlayerMP player) {
			for (Mission q : getRegistry().questsRunning) {
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

	public static class QuestEventHandler {

		@SubscribeEvent
		public void onPlayerJoin(PlayerLoggedInEvent logIn) {
			EntityPlayerMP player = EntityPlayerMP.class.cast(logIn.player);
			RunningSubscriptionHandler.subscribers.add(player);
			for (Entry<String, Mission> mission : getRegistry().runningQuestRegistry.getFrozenDataMap().entrySet()) {
				String missionID = mission.getKey();
				ResourceLocation questID = getRegistry().missionIDtoQuestID.get(missionID);
				PacketPipeline.networkPipe.sendTo(MessageMissionStatus.creation(questID, missionID), player);
			}
		}

		@SubscribeEvent
		public void onPlayerLeave(PlayerLoggedOutEvent logOut) {
			EntityPlayerMP player = EntityPlayerMP.class.cast(logOut.player);
			Mission quest = getRegistry().getMissionForPlayer(player);
			if (quest == null) {
				return;
			}
			quest.quitPlayer(player);
			RunningSubscriptionHandler.subscribers.remove(player);
		}

		@SubscribeEvent
		public void onServerTick(TickEvent.ServerTickEvent ticking) {
			if (ticking.phase != Phase.START) {
				return;
			}
			for (Mission mission : getRegistry().questsRunning) {
				mission.updateCheck();
			}
		}
	}

	private static int questIDCounter = 0;

	public void onPlayerInteraction(EntityPlayerMP player, MessageMHFCInteraction message) {
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
		case INVALID:
		default:
			break;
		}
	}

	private void forfeitQuest(EntityPlayerMP player) {
		Mission quest = getMissionForPlayer(player);
		if (quest != null) {
			quest.quitPlayer(player);
			player.sendMessage(new TextComponentTranslation("mhfc.quests.status.forfeit"));
		} else {
			sendResetPlayerVisual(player);
		}
	}

	private void voteEndQuest(EntityPlayerMP player) {
		Mission quest = getMissionForPlayer(player);
		if (quest != null) {
			quest.voteEnd(player);
			player.sendMessage(new TextComponentTranslation("mhfc.quests.status.voteEnd"));
		} else {
			sendResetPlayerVisual(player);
		}
	}

	private void voteStartQuest(EntityPlayerMP player) {
		Mission quest = getMissionForPlayer(player);
		if (quest != null) {
			quest.voteStart(player);
			player.sendMessage(new TextComponentTranslation("mhfc.quests.status.voteStart"));
		}
	}

	private void acceptQuest(EntityPlayerMP player, MessageMHFCInteraction message) {
		Mission quest = getMission(message.getOptions()[0]);
		if (quest != null) {
			quest.joinPlayer(player);
			player.sendMessage(new TextComponentTranslation("mhfc.quests.status.accepted"));
		} else {
			player.sendMessage(new TextComponentTranslation("mhfc.quests.status.acceptFailed"));
		}
	}

	private void createNewQuest(EntityPlayerMP player, MessageMHFCInteraction message) {
		Mission quest = getMissionForPlayer(player);
		if (quest != null) {
			player.sendMessage(
					new TextComponentTranslation("mhfc.quests.status.createFailed.hasQuest", getMissionID(quest)));
			PacketPipeline.networkPipe.sendTo(quest.createFullUpdateMessage(), player);
			return;
		}
		ResourceLocation questID = new ResourceLocation(message.getOptions()[0]);
		IQuestDefinition questDescription = MHFCQuestBuildRegistry.getQuestDescription(questID);
		if (questDescription == null) {
			player.sendMessage(new TextComponentTranslation("mhfc.quests.status.createFailed.noSuchQuest", questID));
			return;
		}
		String missionID = questID + "@" + player.getDisplayNameString() + "@" + questIDCounter++;
		try (
				DetachableResource<Mission> mission = new DetachableResource<>(
						QuestFactories.constructQuest(questDescription, missionID))) {
			if (mission.get() == null) {
				player.sendMessage(new TextComponentTranslation("mhfc.quests.status.createFailed.internal", questID));
				return;
			}
			if (!mission.get().canJoin(player)) {
				return;
			}
			Mission newQuest = startMission(questID, mission, missionID);
			if (newQuest != null) {
				newQuest.joinPlayer(player);
			}
		} catch (Exception e) {
			Throwables.propagateIfPossible(e);
			throw new RuntimeException(e);
		}
	}

	private void sendResetPlayerVisual(EntityPlayerMP player) {
		String missionID = getMissionIDForPlayer(player);
		if (missionID == null) {
			return;
		}
		PacketPipeline.networkPipe.sendTo(MessageMissionStatus.departing(missionID), player);
	}

	protected Map<EntityPlayer, String> playerQuest = new HashMap<>();
	protected List<Mission> questsRunning = new ArrayList<>();
	protected KeyToInstanceRegistryData<String, Mission> runningQuestRegistry = new KeyToInstanceRegistryData<>();
	protected Map<String, ResourceLocation> missionIDtoQuestID = new HashMap<>();

	private QuestEventHandler connectionHandler;

	private MHFCQuestRegistry() {
		MinecraftForge.EVENT_BUS.register(connectionHandler = new QuestEventHandler());
	}

	private void shutdown() {
		MinecraftForge.EVENT_BUS.unregister(connectionHandler);
	}

	public Mission getMission(String string) {
		return runningQuestRegistry.getData(string);
	}

	/**
	 * Get the quest on which a player is on. If the player is on no quest then null is returned.
	 */
	public Mission getMissionForPlayer(EntityPlayer player) {
		String playerMissionID = getMissionIDForPlayer(player);
		return getMission(playerMissionID);
	}

	/**
	 * Get the id of the mission the player is on. If the player is not currently on a mission, null is returned.
	 *
	 * @param player
	 * @return
	 */
	public String getMissionIDForPlayer(EntityPlayer player) {
		return playerQuest.get(player);
	}

	/**
	 * Returns all quests that are running at the moment.
	 */
	public List<Mission> getAllMissions() {
		return Collections.unmodifiableList(questsRunning);
	}

	/**
	 * Returns the identifier for a quest
	 */
	public String getMissionID(Mission quest) {
		return runningQuestRegistry.getKey(quest);
	}

	/**
	 * Sets the quest for a player, use null to remove the entry
	 *
	 */
	public void setMissionForPlayer(EntityPlayer player, Mission generalQuest) {
		if (generalQuest == null) {
			playerQuest.remove(player);
		} else {
			playerQuest.put(player, runningQuestRegistry.getKey(generalQuest));
		}
	}

	/**
	 * Should be called when a new quest was started
	 */
	private Mission startMission(ResourceLocation questID, DetachableResource<Mission> mission, String missionID) {
		if (!runningQuestRegistry.offerMapping(missionID, mission.get())) {
			return null;
		}
		questsRunning.add(mission.get());
		Mission newQuest = mission.detach();
		missionIDtoQuestID.put(missionID, questID);
		MessageMissionStatus message = MessageMissionStatus.creation(questID, missionID);
		RunningSubscriptionHandler.sendToAll(message);
		return newQuest;
	}

	/**
	 * Should be called when a quest is no longer running and was terminated. Might also be called to ensure client
	 * sync. Returns true if the quest was registered.
	 */
	public boolean endMission(Mission mission) {
		boolean wasRunning = questsRunning.remove(mission);
		if (wasRunning) {
			String missionID = runningQuestRegistry.removeKey(mission);
			missionIDtoQuestID.remove(missionID);
			MessageMissionStatus message = MessageMissionStatus.destruction(missionID);
			RunningSubscriptionHandler.sendToAll(message);
			mission.close();
		}
		return wasRunning;
	}

	/**
	 * Should be called when major changes to a quest were made that require resending the information to all clients
	 */
	public void questUpdated(Mission quest) {
		String missionID = runningQuestRegistry.getKey(quest);
		if (quest == null || missionID == null) {
			return;
		}
		MessageMissionUpdate message = quest.createFullUpdateMessage();
		RunningSubscriptionHandler.sendToAll(message);
	}

	public static MHFCQuestRegistry getRegistry() {
		return serviceAccess.getService();
	}
}
