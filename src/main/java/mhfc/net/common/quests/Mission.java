package mhfc.net.common.quests;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;
import com.mojang.authlib.GameProfile;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.core.registry.MHFCQuestRegistry;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.eventhandler.MHFCTickHandler;
import mhfc.net.common.eventhandler.TickPhase;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.quest.MessageMissionStatus;
import mhfc.net.common.network.message.quest.MessageMissionUpdate;
import mhfc.net.common.quests.api.IQuestDefinition;
import mhfc.net.common.quests.api.IQuestReward;
import mhfc.net.common.quests.api.ISpawnInformation;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.api.QuestGoalSocket;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.quests.rewards.NullReward;
import mhfc.net.common.quests.spawns.NoSpawn;
import mhfc.net.common.quests.world.IQuestAreaSpawnController;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.util.PlayerMap;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.exploration.IExplorationManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Mission implements QuestGoalSocket, AutoCloseable {


	public static final String KEY_TYPE_RUNNING = "running";
	private static final int DELAY_BEFORE_TP_IN_SECONDS = 5;

	private static enum QuestState {
		PENDING,
		RUNNING,
		FINISHED_SUCCESS,
		FINISHED_FAIL,
		RESIGNED;
	}

	private static enum PlayerState {
		IN_TOWN,
		ON_QUEST,
		WAITING_FOR_BACK_TP;
	}

	private static class QuestingPlayerState {
		public GameProfile player;
		public boolean vote;
		@SuppressWarnings("unused")
		public boolean restoreInventory;
		@SuppressWarnings("unused")
		public boolean reward;
		public IExplorationManager previousManager;
		public JsonElement previousSaveData;
		public PlayerState playerState;

		public QuestingPlayerState(GameProfile p, boolean vote, boolean restoreInventory, boolean reward) {
			this.player = p;
			this.restoreInventory = restoreInventory;
			this.vote = vote;
			this.reward = reward;
			// this bind should also consider player if it dies once ! ,
			this.previousManager = MHFCExplorationRegistry.getExplorationManagerFor(p);
			this.previousSaveData = this.previousManager.saveState();
			this.playerState = PlayerState.IN_TOWN;
		}

		public EntityPlayerMP getPlayerEntity() {
			return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()
					.getPlayerByUUID(player.getId());
		}
	}

	private static QuestingPlayerState newAttribute(GameProfile player) {
		return new QuestingPlayerState(player, false, true, false);
	}

	private final String missionID;
	private IQuestDefinition originalDescription;

	private PlayerMap<QuestingPlayerState> playerAttributes;
	private int maxPlayerCount;

	protected QuestState state;
	protected QuestGoal questGoal;
	protected GroupProperty rootGoalProperties;

	/**
	 * Not set before the {@link StagedFuture} from that the area is retrieved from is complete.
	 */
	protected IActiveArea questingArea;

	protected IQuestReward reward;
	protected ISpawnInformation spawns;
	protected int fee;

	private boolean closed;

	public Mission(
			String missionID,
			QuestGoal goal,
			GroupProperty goalProperties,
			int maxPartySize,
			IQuestReward reward,
			ISpawnInformation spawns,
			int fee,
			CompletionStage<IActiveArea> activeArea,
			IQuestDefinition originalDescription) {
		this.missionID = Objects.requireNonNull(missionID);

		this.playerAttributes = new PlayerMap<>();

		this.questGoal = Objects.requireNonNull(goal);
		this.rootGoalProperties = Objects.requireNonNull(goalProperties);

		activeArea.thenAccept(this::onAreaFinished);
		goal.setSocket(this);

		this.reward = reward == null ? new NullReward() : reward;
		this.spawns = spawns == null ? NoSpawn.INSTANCE : spawns;
		this.fee = fee;
		this.state = QuestState.PENDING;
		this.originalDescription = originalDescription;
		this.maxPlayerCount = maxPartySize;

		this.closed = false;
	}

	public void updateCheck() {
		updatePlayers();
	}

	public QuestState getState() {
		return state;
	}

	public QuestGoal getQuestGoal() {
		return questGoal;
	}

	public int getFee() {
		return fee;
	}

	@Override
	public void questGoalStatusNotification(QuestGoal goal, EnumSet<QuestStatus> newStatus) {
		if (newStatus.contains(QuestStatus.Fulfilled) && state == QuestState.RUNNING) {
			onSuccess();
			this.state = QuestState.FINISHED_SUCCESS;
		}
		if (newStatus.contains(QuestStatus.Failed) && state == QuestState.RUNNING) {
			onFail();
			this.state = QuestState.FINISHED_FAIL;
		}
		updatePlayers();
	}

	protected void onAreaFinished(IActiveArea area) {
		this.questingArea = Objects.requireNonNull(area);
		tryStart();
	}

	protected boolean canStart() {
		return allVotes() && this.questingArea != null;
	}

	private void tryStart() {
		if (state == QuestState.PENDING && canStart()) {
			this.state = QuestState.RUNNING;
			onStart();
			resetVotes();
		}
	}

	protected void onFail() {
		for (EntityPlayerMP player : getPlayerEntities()) {
			player.world.playSound(
					null,
					player.getPosition(),
					MHFCSoundRegistry.getRegistry().questNotification,
					SoundCategory.MUSIC,
					2F,
					1F);
			player.sendMessage(new TextComponentTranslation("mhfc.quests.status.failed"));
		}
		// TODO do special stuff for fail
		onEnd();
	}

	protected void onSuccess() {
		for (EntityPlayerMP player : getPlayerEntities()) {
			player.world.playSound(
					null,
					player.getPosition(),
					MHFCSoundRegistry.getRegistry().questClear,
					SoundCategory.MUSIC,
					2F,
					1F);
			player.world.playSound(
					null,
					player.getPosition(),
					MHFCSoundRegistry.getRegistry().questNotification,
					SoundCategory.NEUTRAL,
					2F,
					2F);
			player.sendMessage(new TextComponentTranslation("mhfc.quests.status.success"));
		}
		onEnd();
	}

	protected void onStart() {
		questGoal.setActive(true);
		QuestExplorationManager.bindPlayersToMission(getPlayers(), this);
		for (QuestingPlayerState playerAttributes : playerAttributes.values()) {
			GameProfile player = playerAttributes.player;
			playerAttributes.playerState = PlayerState.ON_QUEST;
			MHFCExplorationRegistry.getExplorationManagerFor(player).respawn(null);
		}

		updatePlayers();
		resetVotes();
		spawns.enqueueSpawns(getSpawnController());
	}

	private void resetVotes() {
		for (QuestingPlayerState attribute : playerAttributes.values()) {
			attribute.vote = false;
		}
	}

	/**
	 * This method should be called whenever the quest ends, no matter how.
	 */
	protected void onEnd() {
		List<CompletionStage<Void>> teleports = new ArrayList<>();
		for (QuestingPlayerState playerState : playerAttributes.values()) {
			CompletionStage<Void> afterReward = teleportBack(playerState).thenAccept(v -> {
				if (state == QuestState.FINISHED_SUCCESS) {
					EntityPlayerMP player = playerState.getPlayerEntity();
					player.sendMessage(new TextComponentTranslation("mhfc.quests.status.rewardGranted"));
					reward.grantReward(player);
					playerState.reward = true;
				}
			});
			teleports.add(afterReward);
		}
		CompletionStage<Void> afterTeleports = CompletableFuture.allOf(teleports.toArray(new CompletableFuture[0]));
		afterTeleports.<Void>handle((r, e) -> null).thenCompose(v -> {
			return MHFCTickHandler.schedule(TickPhase.SERVER_POST, 5 * 20, () -> {
				MHFCQuestRegistry.getRegistry().endMission(this);
				MHFCMain.logger().info("Mission {} ended", getMission());
			});
		});

	}

	protected void updatePlayers() {
		MessageMissionUpdate update = MessageMissionUpdate.createUpdate(missionID, rootGoalProperties);
		if (update == null) {
			return;
		}
		for (EntityPlayerMP player : getPlayerEntities()) {
			PacketPipeline.networkPipe.sendTo(update, player);
		}
		// MHFCQuestRegistry.questUpdated(update);
	}

	protected void updatePlayerInitial(EntityPlayerMP player) {
		// TODO: add player to the quest
		PacketPipeline.networkPipe.sendTo(MessageMissionStatus.joining(missionID), player);
		PacketPipeline.networkPipe.sendTo(createFullUpdateMessage(), player);
	}

	public boolean canJoin(EntityPlayer player) {
		// TODO add more evaluation and/or move to another class?
		boolean isPending = state == QuestState.PENDING;
		boolean notFull = playerCount() < maxPlayerCount;
		boolean playerHasNoQuest = MHFCQuestRegistry.getRegistry().getMissionForPlayer(player) == null;
		return isPending && notFull && playerHasNoQuest;
	}

	private int playerCount() {
		return playerAttributes.size();
	}

	@Override
	public void reset() {
		questGoal.reset();
	}

	@Override
	public Mission getMission() {
		return this;
	}

	private void addPlayer(EntityPlayerMP player) {
		playerAttributes.putPlayer(player, Mission.newAttribute(player.getGameProfile()));
		MHFCQuestRegistry.getRegistry().setMissionForPlayer(player, this);
		updatePlayerInitial(player);
		updatePlayers();
	}

	private QuestingPlayerState removePlayer(EntityPlayerMP player) {
		QuestingPlayerState att = playerAttributes.removePlayer(player);
		if (att != null) {
			PacketPipeline.networkPipe.sendTo(MessageMissionStatus.departing(missionID), player);
			MHFCQuestRegistry.getRegistry().setMissionForPlayer(player, null);
			//
		}
		return att;
	}

	private CompletionStage<Void> teleportBack(QuestingPlayerState att) {
		Objects.requireNonNull(att);

		if (att.playerState == PlayerState.WAITING_FOR_BACK_TP) {
			return CompletableFuture.completedFuture(null);
		}
		if (att.playerState == PlayerState.IN_TOWN) {
			// Pretend we're gonna to tp him, but actually just rebind the player
			att.playerState = PlayerState.WAITING_FOR_BACK_TP;
			return MHFCTickHandler.schedule(TickPhase.SERVER_POST, DELAY_BEFORE_TP_IN_SECONDS * 20, () -> {
				GameProfile player = att.player;
				MHFCExplorationRegistry.bindPlayer(att.previousManager, player).respawn(att.previousSaveData);
				att.playerState = PlayerState.IN_TOWN;
			});
		}

		att.getPlayerEntity().sendMessage(
				new TextComponentTranslation("mhfc.quests.status.teleportSoon", DELAY_BEFORE_TP_IN_SECONDS));
		att.playerState = PlayerState.WAITING_FOR_BACK_TP;
		return MHFCTickHandler.schedule(TickPhase.SERVER_POST, DELAY_BEFORE_TP_IN_SECONDS * 20, () -> {
			EntityPlayerMP player = att.getPlayerEntity();
			player.sendMessage(new TextComponentTranslation("mhfc.quests.status.teleport"));

			// Remove the player, otherwise the rebind will trigger another remove
			removePlayer(player);

			MHFCExplorationRegistry.bindPlayer(att.previousManager, att.player).respawn(att.previousSaveData);
			att.playerState = PlayerState.IN_TOWN;
		});
	}


	public boolean joinPlayer(EntityPlayerMP player) {
		if (!canJoin(player)) {
			return false;
		}
		addPlayer(player);
		return true;
	}

	public void quitPlayer(EntityPlayerMP player) {
		QuestingPlayerState attributes = removePlayer(player);
		if (attributes != null) {
			teleportBack(attributes);
		}
		if (playerCount() == 0) {
			onEnd();
		}
	}

	public Set<GameProfile> getPlayers() {
		return playerAttributes.values().stream().map(t -> t.player).collect(Collectors.toSet());
	}

	public Set<EntityPlayerMP> getPlayerEntities() {
		return playerAttributes.values().stream().map(QuestingPlayerState::getPlayerEntity).collect(Collectors.toSet());
	}

	/**
	 * Utility method to provide the spawn controller. Might also introduce an indirection if the publicly available
	 * controller should behave differently.
	 */
	public IQuestAreaSpawnController getSpawnController() {
		return questingArea.getArea().getSpawnController();
	}

	public IQuestDefinition getOriginalDescription() {
		return originalDescription;
	}

	private QuestingPlayerState getPlayerAttributes(EntityPlayerMP player) {
		return playerAttributes.getPlayer(player);
	}

	public void voteStart(EntityPlayerMP player) {
		QuestingPlayerState attributes = getPlayerAttributes(player);
		attributes.vote = true;
		tryStart();
	}

	private boolean allVotes() {
		return !playerAttributes.isEmpty() && playerAttributes.values().stream().allMatch((x) -> x.vote);
	}

	public void voteEnd(EntityPlayerMP player) {
		QuestingPlayerState attributes = getPlayerAttributes(player);
		attributes.vote = true;

		boolean end = allVotes();
		if (end && state == QuestState.RUNNING) {
			onEnd();
			state = QuestState.RESIGNED;
			resetVotes();
		}
	}


	public int getMaxPartySize() {
		return maxPlayerCount;
	}

	public IAreaType getAreaType() {
		return questingArea.getType();
	}

	public IActiveArea getQuestingArea() {
		return questingArea;
	}

	public QuestFlair getQuestFlair() {
		return originalDescription.getQuestFlair();
	}

	@Override
	public void close() {
		if (closed) {
			MHFCMain.logger().debug("Tried to close already closed instance of mission {}", missionID);
			return;
		}
		questGoal.questGoalFinalize();
		if (questingArea != null) {
			// Could be closed before area is finished
			questingArea.close();
		}
		closed = true;
	}

	@Override
	protected void finalize() throws Throwable {
		if (!closed) {
			close();
		}
	}

	/**
	 * Creates a packet suitable to initialize the properties of a mission based on the current state.
	 *
	 * @return
	 */
	public MessageMissionUpdate createFullUpdateMessage() {
		return MessageMissionUpdate.createFullDump(missionID, rootGoalProperties);
	}

}
