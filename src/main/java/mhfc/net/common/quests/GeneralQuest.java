package mhfc.net.common.quests;

import java.util.EnumSet;

import mhfc.net.common.core.registry.MHFCRegQuests;
import mhfc.net.common.quests.goals.QuestGoal;
import net.minecraft.entity.player.EntityPlayer;

public class GeneralQuest implements QuestGoalSocket {

	enum QuestState {
		pending, running, finished, resigned;
	}

	private EntityPlayer[] players;
	private int playerCount;

	protected QuestState state;
	protected QuestGoal questGoal;
	// TODO replace with real area
	protected String areaId;

	protected int reward;
	protected int fee;

	public GeneralQuest(QuestGoal goal, int maxPartySize, int reward, int fee,
			String areaId) {
		this.questGoal = goal;
		this.players = new EntityPlayer[maxPartySize];
		this.reward = reward;
		this.fee = fee;
		this.areaId = areaId;
		this.state = QuestState.pending;

		MHFCRegQuests.registerQuest(this);
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
	}

	protected void onFail() {
		// TODO Teleport back when we have not finished yet, don't reward
	}

	protected void onSuccess() {
		// TODO tp back and reward the players for finishing the quest
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
		}
	}

	public boolean leavePlayer(EntityPlayer player) {
		int i;
		boolean found = false;
		for (i = 0; i < players.length; i++) {
			if (players[i] == player) {
				found = true;
				break;
			}
		}
		if (found) {
			// TODO teleport the player back to the overworld if this happened
			// during the quest (cautious of him being online) else do nothing
			MHFCRegQuests.setQuestForPlayer(players[i], null);
		}
		for (; i < players.length; i++) {
			players[i] = (i == players.length - 1 ? null : players[i + 1]);
		}
		return found;
	}

	public EntityPlayer[] getPlayers() {
		return players;
	}
}
