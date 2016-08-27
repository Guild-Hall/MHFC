package mhfc.net.common.quests;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.core.registry.MHFCQuestRegistry;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.quest.MessageMissionStatus;
import mhfc.net.common.network.message.quest.MessageMissionUpdate;
import mhfc.net.common.quests.api.QuestDefinition;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.api.QuestGoalSocket;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.quests.world.IQuestAreaSpawnController;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.util.PlayerMap;
import mhfc.net.common.util.StagedFuture;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.exploration.IExplorationManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class Mission implements QuestGoalSocket, AutoCloseable {

	public static final String KEY_TYPE_RUNNING = "running";

	enum QuestState {
		pending,
		running,
		finished,
		resigned;
	}

	private static class QuestingPlayerState {
		public EntityPlayerMP player;
		public boolean vote;
		@SuppressWarnings("unused")
		public boolean restoreInventory;
		@SuppressWarnings("unused")
		public boolean reward;
		public IExplorationManager previousManager;

		public QuestingPlayerState(EntityPlayerMP p, boolean vote, boolean restoreInventory, boolean reward) {
			this.player = p;
			this.restoreInventory = restoreInventory;
			this.vote = vote;
			this.reward = reward;
			this.previousManager = MHFCExplorationRegistry.getExplorationManagerFor(p);
		}
	}

	private static QuestingPlayerState newAttribute(EntityPlayerMP player) {
		return new QuestingPlayerState(player, false, true, false);
	}

	private final String missionID;
	private QuestDefinition originalDescription;

	private PlayerMap<QuestingPlayerState> playerAttributes;
	private int maxPlayerCount;

	protected QuestState state;
	protected QuestGoal questGoal;
	protected GroupProperty rootGoalProperties;

	/**
	 * Not set before the {@link StagedFuture} from that the area is retrieved from is complete.
	 */
	protected IActiveArea questingArea;
	protected QuestExplorationManager explorationManager;

	protected int reward;
	protected int fee;

	private boolean closed;

	public Mission(
			String missionID,
			QuestGoal goal,
			GroupProperty goalProperties,
			int maxPartySize,
			int reward,
			int fee,
			CompletionStage<IActiveArea> activeArea,
			QuestDefinition originalDescription) {
		this.missionID = Objects.requireNonNull(missionID);

		this.playerAttributes = new PlayerMap<>();

		this.questGoal = Objects.requireNonNull(goal);
		this.rootGoalProperties = Objects.requireNonNull(goalProperties);

		activeArea.thenAccept(this::onAreaFinished);
		goal.setSocket(this);

		this.reward = reward;
		this.fee = fee;
		this.state = QuestState.pending;
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

	public int getReward() {
		return reward;
	}

	public int getFee() {
		return fee;
	}

	@Override
	public void questGoalStatusNotification(QuestGoal goal, EnumSet<QuestStatus> newStatus) {
		if (newStatus.contains(QuestStatus.Fulfilled)) {
			onSuccess();
		}
		if (newStatus.contains(QuestStatus.Failed)) {
			onFail();
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
		if (canStart()) {
			onStart();
			resetVotes();
		}
	}

	protected void onFail() {
		for (EntityPlayerMP player : getPlayers()) {
			player.addChatMessage(new ChatComponentText("You have failed a quest"));
		}
		// TODO do special stuff for fail
		onEnd();
	}

	protected void onSuccess() {
		for (QuestingPlayerState attribute : playerAttributes.values()) {
			attribute.reward = true;
			attribute.player.addExperienceLevel(10);
			attribute.player.addChatMessage(new ChatComponentText("You have successfully completed a quest"));
		}
		this.state = QuestState.finished;
		// TODO reward the players for finishing the quest with dynamic rewards
		onEnd();
	}

	protected void onStart() {
		explorationManager = new QuestExplorationManager(getQuestFlair(), getQuestingArea(), this);
		questGoal.setActive(true);
		this.state = QuestState.running;
		for (EntityPlayerMP player : getPlayers()) {
			MHFCExplorationRegistry.bindPlayer(explorationManager, player);
			explorationManager.respawn(player);
			// AreaTeleportation.movePlayerToArea(player, questingArea.getArea());
		}
		updatePlayers();
		resetVotes();
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
		for (EntityPlayerMP player : getPlayers()) {
			removePlayer(player);
		}
		MHFCQuestRegistry.getRegistry().endMission(this);
		MHFCMain.logger().info("Mission {} ended", getMission());
	}

	protected void updatePlayers() {
		MessageMissionUpdate update = MessageMissionUpdate.createUpdate(missionID, rootGoalProperties);
		if (update == null) {
			return;
		}
		for (QuestingPlayerState attribute : playerAttributes.values()) {
			EntityPlayerMP player = attribute.player;
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
		boolean isPending = state == QuestState.pending;
		boolean notFull = playerCount() < maxPlayerCount;
		boolean playerHasNoQuest = MHFCQuestRegistry.getRegistry().getQuestForPlayer(player) == null;
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
		playerAttributes.putPlayer(player, Mission.newAttribute(player));
		MHFCQuestRegistry.getRegistry().setQuestForPlayer(player, this);
		updatePlayerInitial(player);
		updatePlayers();
	}

	private boolean removePlayer(EntityPlayerMP player) {
		QuestingPlayerState att = playerAttributes.removePlayer(player);
		if (att != null) {
			PacketPipeline.networkPipe.sendTo(MessageMissionStatus.departing(missionID), att.player);
			MHFCQuestRegistry.getRegistry().setQuestForPlayer(att.player, null);
			MHFCExplorationRegistry.bindPlayer(att.previousManager, player);
			MHFCExplorationRegistry.respawnPlayer(player);
			return true;
		}
		return false;
	}

	public boolean joinPlayer(EntityPlayerMP player) {
		if (!canJoin(player)) {
			return false;
		}
		addPlayer(player);
		return true;
	}

	public boolean quitPlayer(EntityPlayerMP player) {
		boolean found = removePlayer(player);
		if (playerCount() == 0) {
			onEnd();
		}
		return found;
	}

	public Set<EntityPlayerMP> getPlayers() {
		return playerAttributes.values().stream().map(t -> t.player).collect(Collectors.toSet());
	}

	/**
	 * Utility method to provide the spawn controller. Might also introduce an indirection if the publicly available
	 * controller should behave differently.
	 */
	public IQuestAreaSpawnController getSpawnController() {
		return questingArea.getArea().getSpawnController();
	}

	public QuestDefinition getOriginalDescription() {
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
		return playerAttributes.values().stream().map((x) -> x.vote).reduce(Boolean::logicalAnd).orElse(true);
	}

	public void voteEnd(EntityPlayerMP player) {
		QuestingPlayerState attributes = getPlayerAttributes(player);
		attributes.vote = true;

		boolean end = allVotes();
		if (end && state == QuestState.running) {
			onEnd();
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
		questingArea.close();
		closed = true;
	}

	@Override
	protected void finalize() throws Throwable {
		if (!closed) {
			close();
		}
	}

	/**
	 * Searches for the player with a matching entity id and updates all references
	 */
	public boolean updatePlayerEntity(EntityPlayerMP player) {
		return playerAttributes.computeIfPresent(PlayerMap.playerToKey(player), (k, a) -> {
			a.player = player;
			return a;
		}) != null;
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
