package mhfc.net.common.quests;

import java.util.EnumSet;

import mhfc.net.common.core.registry.MHFCRegQuests;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.quests.QuestRunningInformation.InformationType;
import mhfc.net.common.quests.factory.QuestDescription;
import mhfc.net.common.quests.goals.QuestGoal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

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

		visualInformation.updateFromQuest(this);
	}

	protected void onFail() {
		// FIXME Teleport back when we have not finished yet, don't reward
	}

	protected void onSuccess() {
		// FIXME tp back and reward the players for finishing the quest
	}

	protected void onStart() {
		questGoal.setActive(true);
		updatePlayers();
	}

	protected void updatePlayers() {
		visualInformation.updateFromQuest(this);
		for (int i = 0; i < playerCount; i++) {
			EntityPlayer p = players[i];
			String id = MHFCRegQuests.getIdentifierForQuest(this);
			MHFCRegQuests.networkWrapper.sendTo(
					new<QuestRunningInformation> MessageQuestVisual(id,
							visualInformation), (EntityPlayerMP) p);

		}
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
			MHFCRegQuests.networkWrapper.sendTo(
					new MessageQuestVisual("", null), (EntityPlayerMP) player);
		}
		for (; i < players.length; i++) {
			players[i] = (i == players.length - 1 ? null : players[i + 1]);
		}
		return found;
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

	public String update(InformationType t, String current) {
		String mod = questGoal.modify(t, "");
		System.out.println(mod);
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

}
