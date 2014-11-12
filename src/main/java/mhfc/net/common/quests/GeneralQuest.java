package mhfc.net.common.quests;

import java.util.EnumSet;

import mhfc.net.common.core.registry.MHFCRegQuests;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.quests.QuestRunningInformation.InformationType;
import mhfc.net.common.quests.factory.QuestDescription;
import mhfc.net.common.quests.goals.QuestGoal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class GeneralQuest implements QuestGoalSocket {

	enum QuestState {
		pending, running, finished, resigned;
	}

	private QuestDescription originalDescription;
	private QuestRunningInformation visualInformation;

	private EntityPlayer[] players;
	private boolean[] votes;
	private QuestSpawnController spawnController;
	private int playerCount;

	protected QuestState state;
	protected QuestGoal questGoal;
	// FIXME replace with real area
	protected String areaId;

	protected int reward;
	protected int fee;

	public GeneralQuest(QuestGoal goal, int maxPartySize, int reward, int fee,
			String areaId, QuestDescription originalDescription) {
		this.questGoal = goal;
		goal.setSocket(this);
		this.players = new EntityPlayer[maxPartySize];
		this.votes = new boolean[maxPartySize];
		for (int i = 0; i < maxPartySize; i++) {
			players[i] = null;
			votes[i] = false;
		}
		this.reward = reward;
		this.fee = fee;
		this.areaId = areaId;
		this.state = QuestState.pending;
		this.originalDescription = originalDescription;
		this.visualInformation = new QuestRunningInformation(this);
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
	public void questGoalStatusNotification(QuestGoal goal,
			EnumSet<QuestStatus> newStatus) {
		if (newStatus.contains(QuestStatus.Fulfilled))
			onSuccess();
		if (newStatus.contains(QuestStatus.Failed))
			onFail();
		updatePlayers();
	}

	protected void onFail() {
		for (int i = 0; i < playerCount; i++) {
			players[i].addChatMessage(new ChatComponentText(
					"You have failed a quest"));
		}
		// TODO do special stuff for fail
		onEnd();
	}

	protected void onSuccess() {
		for (int i = 0; i < playerCount; i++) {
			players[i].addExperienceLevel(10);
			players[i].addChatMessage(new ChatComponentText(
					"You have successfully completed a quest"));
		}
		this.state = QuestState.finished;
		// TODO reward the players for finishing the quest with dynamic rewards
		onEnd();
	}

	protected void onStart() {
		questGoal.setActive(true);
		this.state = QuestState.running;
		updatePlayers();
	}

	/**
	 * This method should be called whenever the quest ends, no matter how.
	 */
	// FIXME somehow the quests end early now!!
	protected void onEnd() {
		System.out.println("Hey, a quest ended");
		for (int i = 0; i < playerCount; i++) {
			removePlayer(i);
		}
		visualInformation.cleanUp();
		// FIXME something goes wrong here
		// questGoal.questGoalFinalize();
		MHFCRegQuests.deregisterQuest(this);
	}

	protected void updatePlayers() {
		visualInformation.updateFromQuest(this);
		for (int i = 0; i < playerCount; i++) {
			EntityPlayer p = players[i];
			String id = MHFCRegQuests.getIdentifierForQuest(this);
			// TODO review id resolving
			MHFCRegQuests.networkWrapper.sendTo(
					new<QuestRunningInformation> MessageQuestVisual(id,
							visualInformation), (EntityPlayerMP) p);

		}
		MHFCRegQuests.questUpdated(this);
	}

	public boolean canJoin(EntityPlayer player) {
		// TODO add more evaluation
		if (state == QuestState.pending && playerCount < players.length
				&& MHFCRegQuests.getQuestForPlayer(player) == null) {
			return true;
		}
		return false;
	}

	@Override
	public void reset() {
		questGoal.reset();
	}

	@Override
	public GeneralQuest getQuest() {
		return this;
	}

	public void addPlayer(EntityPlayer player) {
		if (canJoin(player)) {
			players[playerCount] = player;
			++playerCount;
			MHFCRegQuests.setQuestForPlayer(player, this);
			updatePlayers();
		}
	}

	public boolean removePlayer(EntityPlayer player) {
		int i;
		boolean found = false;
		for (i = 0; i < players.length; i++) {
			if (players[i] != null
					&& player != null
					&& players[i].getGameProfile().getName() == player
							.getGameProfile().getName()) {
				found = true;
				break;
			}
		}
		removePlayer(i);
		if (playerCount == 0)
			onEnd();
		return found;
	}

	protected void removePlayer(int index) {
		if (index < 0 || index >= playerCount)
			return;
		MHFCRegQuests.networkWrapper.sendTo(
				new<QuestRunningInformation> MessageQuestVisual("", null),
				(EntityPlayerMP) players[index]);
		MHFCRegQuests.setQuestForPlayer(players[index], null);
		// FIXME teleport players to the overworld if they aren't there.
		for (int i = index; i < playerCount - 1; i++) {
			players[i] = players[i + 1];
		}
		players[playerCount - 1] = null;
		--playerCount;
	}

	public EntityPlayer[] getPlayers() {
		return players;
	}

	public QuestSpawnController getSpawnController() {
		return spawnController;
	}

	public QuestDescription getOriginalDescription() {
		return originalDescription;
	}

	public String updateVisual(InformationType t, String current) {
		if (t == InformationType.MaxPartySize) {
			return playerCount + "/" + players.length
					+ " {unlocalized:Players}";
		}
		String mod = questGoal.modify(t, "");
		if (mod.equals(""))
			return current;
		return mod;
	}

	public void voteStart(EntityPlayerMP player) {
		for (int i = 0; i < playerCount; i++) {
			if (players[i] == player) {
				votes[i] = true;
				break;
			}
		}
		boolean start = true;
		for (int i = 0; i < playerCount; i++) {
			start &= (player == null) || votes[i];
		}
		if (start) {
			onStart();
		}
	}

	public void voteEnd(EntityPlayerMP player) {
		for (int i = 0; i < playerCount; i++) {
			if (players[i] == player) {
				votes[i] = false;
				break;
			}
		}
		boolean end = false;
		for (int i = 0; i < playerCount; i++) {
			end |= (player == null) && votes[i];
		}
		if (end && state == QuestState.running) {
			onEnd();
		}
	}

	public QuestRunningInformation getRunningInformation() {
		return visualInformation;
	}

}
