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
		// TODO Auto-generated method stub

	}

	public boolean canJoin(EntityPlayer player) {
		// TODO add more evaluation
		if (state == QuestState.pending && playerCount < players.length) {
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
}
