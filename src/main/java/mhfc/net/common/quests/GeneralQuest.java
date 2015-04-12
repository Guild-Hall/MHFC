package mhfc.net.common.quests;

import java.util.EnumSet;

import mhfc.net.common.core.registry.MHFCQuestsRegistry;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.quests.QuestRunningInformation.InformationType;
import mhfc.net.common.quests.factory.QuestDescription;
import mhfc.net.common.quests.goals.QuestGoal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.server.FMLServerHandler;

public class GeneralQuest implements QuestGoalSocket {

	enum QuestState {
		pending, running, finished, resigned;
	}

	private static class PlayerAttributes {
		public EntityPlayerMP player;
		public boolean vote;
		public boolean restoreInventory;
		public boolean reward;
		public int posX, posY, posZ;
		public int dimensionID;

		public PlayerAttributes(EntityPlayerMP p, boolean vote,
				boolean restoreInventory, boolean reward, int x, int y, int z,
				int dim) {
			this.player = p;
			this.restoreInventory = restoreInventory;
			this.vote = vote;
			this.reward = reward;
			this.posX = x;
			this.posY = y;
			this.posZ = z;
			this.dimensionID = dim;
		}
	}

	private static PlayerAttributes newAttribute(EntityPlayerMP player) {
		return new PlayerAttributes(player, false, true, false,
				player.serverPosX, player.serverPosY, player.serverPosZ,
				player.worldObj.provider.dimensionId);
	}

	private QuestDescription originalDescription;
	private QuestRunningInformation visualInformation;

	private PlayerAttributes[] playerAttributes;
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
		this.playerAttributes = new PlayerAttributes[maxPartySize];
		for (int i = 0; i < maxPartySize; i++) {
			playerAttributes[i] = null;
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
			playerAttributes[i].player.addChatMessage(new ChatComponentText(
					"You have failed a quest"));
		}
		// TODO do special stuff for fail
		onEnd();
	}

	protected void onSuccess() {
		for (int i = 0; i < playerCount; i++) {
			playerAttributes[i].reward = true;
			playerAttributes[i].player.addExperienceLevel(10);
			playerAttributes[i].player.addChatMessage(new ChatComponentText(
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
		resetVotes();
	}

	private void resetVotes() {
		for (int i = 0; i < playerCount; i++) {
			playerAttributes[i].vote = false;
		}
	}

	/**
	 * This method should be called whenever the quest ends, no matter how.
	 */
	protected void onEnd() {
		for (int i = 0; i < playerCount; i++) {
			removePlayer(i);
		}
		visualInformation.cleanUp();
		questGoal.questGoalFinalize();
		MHFCQuestsRegistry.deregisterQuest(this);
	}

	protected void updatePlayers() {
		visualInformation.updateFromQuest(this);
		for (int i = 0; i < playerCount; i++) {
			EntityPlayerMP p = playerAttributes[i].player;
			String id = MHFCQuestsRegistry.getIdentifierForQuest(this);
			PacketPipeline.networkPipe.sendTo(
					new<QuestRunningInformation> MessageQuestVisual(id,
							visualInformation), p);

		}
		MHFCQuestsRegistry.questUpdated(this);
	}

	public boolean canJoin(EntityPlayer player) {
		// TODO add more evaluation and/or move to another class?
		if (state == QuestState.pending
				&& playerCount < playerAttributes.length
				&& MHFCQuestsRegistry.getQuestForPlayer(player) == null) {
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
			playerAttributes[playerCount] = newAttribute((EntityPlayerMP) player);
			++playerCount;
			MHFCQuestsRegistry.setQuestForPlayer(player, this);
			updatePlayers();
		}
	}

	public boolean removePlayer(EntityPlayer player) {
		int i;
		boolean found = false;
		for (i = 0; i < playerAttributes.length; i++) {
			if (playerAttributes[i] != null
					&& player != null
					&& playerAttributes[i].player.getGameProfile().getName() == player
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

		PlayerAttributes att = playerAttributes[index];
		PacketPipeline.networkPipe.sendTo(
				new<QuestRunningInformation> MessageQuestVisual("", null),
				att.player);
		MHFCQuestsRegistry.setQuestForPlayer(att.player, null);
		if (att.player.getEntityWorld().provider.dimensionId != att.dimensionID)
			FMLServerHandler.instance().getServer().getConfigurationManager()
					.transferPlayerToDimension(att.player, att.dimensionID);
		att.player.moveEntity(att.posX, att.posY, att.posZ);

		// Clean up, move rest up
		for (int i = index; i < playerCount - 1; i++) {
			playerAttributes[i] = playerAttributes[i + 1];
		}
		playerAttributes[playerCount - 1] = null;
		--playerCount;
	}

	public EntityPlayerMP[] getPlayers() {
		EntityPlayerMP[] playrs = new EntityPlayerMP[playerCount];
		for (int i = 0; i < playerCount; i++) {
			playrs[i] = playerAttributes[i].player;
		}
		return playrs;
	}

	public QuestSpawnController getSpawnController() {
		return spawnController;
	}

	public QuestDescription getOriginalDescription() {
		return originalDescription;
	}

	public String updateVisual(InformationType t, String current) {
		if (t == InformationType.MaxPartySize) {
			return playerCount + "/" + playerAttributes.length
					+ " {unlocalized:Players}";
		}
		String mod = questGoal.modify(t, "");
		if (mod.equals(""))
			return current;
		return mod;
	}

	public void voteStart(EntityPlayerMP player) {
		for (int i = 0; i < playerCount; i++) {
			if (playerAttributes[i].player == player) {
				playerAttributes[i].vote = true;
				break;
			}
		}

		boolean start = allVotes();
		if (start) {
			onStart();
		}
	}

	private boolean allVotes() {
		boolean votes = true;
		for (int i = 0; i < playerCount; i++) {
			votes &= (playerAttributes[i] == null) || playerAttributes[i].vote;
		}
		return votes;
	}

	public void voteEnd(EntityPlayerMP player) {
		for (int i = 0; i < playerCount; i++) {
			if (playerAttributes[i].player == player) {
				playerAttributes[i].vote = false;
				break;
			}
		}
		boolean end = allVotes();
		if (end && state == QuestState.running) {
			onEnd();
		}
	}

	public QuestRunningInformation getRunningInformation() {
		return visualInformation;
	}

}
