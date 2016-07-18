package mhfc.net.common.quests;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.core.registry.MHFCQuestRegistry;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.network.packet.MessageQuestVisual.VisualType;
import mhfc.net.common.quests.QuestRunningInformation.InformationType;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.IVisualInformation;
import mhfc.net.common.quests.api.QuestDescription;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.api.QuestGoalSocket;
import mhfc.net.common.quests.world.IQuestAreaSpawnController;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.util.StagedFuture;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.exploration.IExplorationManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class GeneralQuest extends QuestDescription implements QuestGoalSocket, AutoCloseable {

	public static final String KEY_TYPE_RUNNING = "running";

	enum QuestState {
		pending,
		running,
		finished,
		resigned;
	}

	private static class PlayerAttributes {
		public EntityPlayerMP player;
		public boolean vote;
		@SuppressWarnings("unused")
		public boolean restoreInventory;
		@SuppressWarnings("unused")
		public boolean reward;
		public IExplorationManager previousManager;

		public PlayerAttributes(EntityPlayerMP p, boolean vote, boolean restoreInventory, boolean reward) {
			this.player = p;
			this.restoreInventory = restoreInventory;
			this.vote = vote;
			this.reward = reward;
			this.previousManager = MHFCExplorationRegistry.getExplorationManagerFor(p);
		}
	}

	private static PlayerAttributes newAttribute(EntityPlayerMP player) {
		return new PlayerAttributes(player, false, true, false);
	}

	private QuestDescription originalDescription;
	private QuestRunningInformation visualInformation;

	private Map<Integer, PlayerAttributes> playerAttributes;
	private int maxPlayerCount;

	protected QuestState state;
	protected QuestGoal questGoal;

	/**
	 * Not set before the {@link StagedFuture} from that the area is retrieved from is complete.
	 */
	protected IActiveArea questingArea;
	protected QuestExplorationManager explorationManager;

	protected int reward;
	protected int fee;

	private boolean closed;

	public GeneralQuest(
			QuestGoal goal,
			int maxPartySize,
			int reward,
			int fee,
			CompletionStage<IActiveArea> activeArea,
			QuestDescription originalDescription) {
		super(MHFCQuestBuildRegistry.QUEST_RUNNING);
		this.playerAttributes = new HashMap<>();
		this.questGoal = Objects.requireNonNull(goal);
		activeArea.thenAccept(this::onAreaFinished);
		goal.setSocket(this);

		this.reward = reward;
		this.fee = fee;
		this.state = QuestState.pending;
		this.originalDescription = originalDescription;
		this.maxPlayerCount = maxPartySize;

		this.visualInformation = new QuestRunningInformation(this);

		this.closed = false;
	}

	public QuestState getState() {
		return state;
	}

	public QuestGoal getQuestGoal() {
		return questGoal;
	}

	@Override
	public int getReward() {
		return reward;
	}

	@Override
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
		boolean start = allVotes();
		return start && this.questingArea != null;
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
		for (PlayerAttributes attribute : playerAttributes.values()) {
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
		for (PlayerAttributes attribute : playerAttributes.values()) {
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
		MHFCQuestRegistry.deregRunningQuest(this);
		MHFCMain.logger().info("Quest {} ended", this.visualInformation.getName());
	}

	protected void updatePlayers() {
		visualInformation.updateFromQuest(this);
		for (PlayerAttributes attribute : playerAttributes.values()) {
			EntityPlayerMP p = attribute.player;
			String id = MHFCQuestRegistry.getIdentifierForQuest(this);
			PacketPipeline.networkPipe
					.sendTo(new MessageQuestVisual(VisualType.PERSONAL_QUEST, id, visualInformation), p);

		}
		MHFCQuestRegistry.questUpdated(this);
	}

	public boolean canJoin(EntityPlayer player) {
		// TODO add more evaluation and/or move to another class?
		if (state == QuestState.pending && playerCount() < maxPlayerCount
				&& MHFCQuestRegistry.getQuestForPlayer(player) == null) {
			return true;
		}
		return false;
	}

	private int playerCount() {
		return playerAttributes.size();
	}

	@Override
	public void reset() {
		questGoal.reset();
	}

	@Override
	public GeneralQuest getQuest() {
		return this;
	}

	private void addPlayer(EntityPlayerMP player) {
		playerAttributes.put(player.getEntityId(), GeneralQuest.newAttribute(player));
		MHFCQuestRegistry.setQuestForPlayer(player, this);
		updatePlayers();
	}

	private boolean removePlayer(EntityPlayerMP player) {
		PlayerAttributes att = playerAttributes.remove(player.getEntityId());
		if (att != null) {
			PacketPipeline.networkPipe.sendTo(new MessageQuestVisual(VisualType.PERSONAL_QUEST, "", null), att.player);
			MHFCQuestRegistry.setQuestForPlayer(att.player, null);
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

	public QuestDescription getOriginalDescription() {
		return originalDescription;
	}

	public String updateVisual(InformationType t, String current) {
		if (t == InformationType.MaxPartySize) {
			return playerCount() + "/" + maxPlayerCount + " {unlocalized:Players}";
		}
		String mod = questGoal.modify(t, "");
		if (mod.equals("")) {
			return current;
		}
		return mod;
	}

	private PlayerAttributes getPlayerAttributes(EntityPlayerMP player) {
		return playerAttributes.get(player.getEntityId());
	}

	public void voteStart(EntityPlayerMP player) {
		PlayerAttributes attributes = getPlayerAttributes(player);
		attributes.vote = true;
		tryStart();
	}

	private boolean allVotes() {
		return playerAttributes.values().stream().map((x) -> x.vote).reduce(Boolean::logicalAnd).orElse(true);
	}

	public void voteEnd(EntityPlayerMP player) {
		PlayerAttributes attributes = getPlayerAttributes(player);
		attributes.vote = true;

		boolean end = allVotes();
		if (end && state == QuestState.running) {
			onEnd();
			resetVotes();
		}
	}

	public QuestRunningInformation getRunningInformation() {
		return visualInformation;
	}

	@Override
	public IVisualInformation getVisualInformation() {
		return getRunningInformation();
	}

	@Override
	public int getMaxPartySize() {
		return maxPlayerCount;
	}

	@Override
	public IAreaType getAreaType() {
		return questingArea.getType();
	}

	public IActiveArea getQuestingArea() {
		return questingArea;
	}

	@Override
	public GoalReference getGoalReference() {
		return originalDescription.getGoalReference();
	}

	@Override
	public QuestType getQuestType() {
		return originalDescription.getQuestType();
	}

	@Override
	public QuestFlair getQuestFlair() {
		return originalDescription.getQuestFlair();
	}

	@Override
	public void close() {
		if (closed) {
			MHFCMain.logger().debug("Tried to close already closed instance of quest {}", visualInformation.getName());
			return;
		}
		visualInformation.cleanUp();
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
		return playerAttributes.computeIfPresent(player.getEntityId(), (k, a) -> {
			a.player = player;
			return a;
		}) != null;
	}

}
