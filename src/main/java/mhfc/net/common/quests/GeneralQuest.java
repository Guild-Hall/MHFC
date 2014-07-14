package mhfc.net.common.quests;

import java.util.EnumSet;

import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.quests.goals.QuestGoal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GeneralQuest implements QuestGoalSocket {
	enum QuestType {
		Hunting("quests.type.hunting"), EpicHunting("quest.type.epichunting"), Killing(
				"quests.type.killing"), Gathering("quests.type.gathering");
		QuestType(String s) {
			this.s = s;
		}
		public String getAsString() {
			return s;
		}
		String s;
	}
	enum QuestState {
		pending, running, finished, resigned;
	}
	public static final QuestType HUNTING = QuestType.Hunting;
	public static final QuestType EPIC_HUNTING = QuestType.EpicHunting;
	public static final QuestType KILLING = QuestType.Killing;
	public static final QuestType GATHERING = QuestType.Gathering;

	protected QuestType type;
	protected QuestState state;
	protected EntityMHFCBase entities[];
	protected WorldGenerator worldGen;
	protected QuestGoal questGoal;

	protected int reward;
	protected int fee;
	protected int timeLimitInS;

	protected String name;
	protected String area;
	protected String description;
	protected String client;
	protected String aims;
	protected String fails;

	public GeneralQuest(QuestType type, QuestGoal goal,
			EntityMHFCBase entities[], int reward, int fee, int timeLimit,
			String name, String area, String description, String client,
			String aims, String fails) {
		this.type = type;
		this.questGoal = goal;
		this.entities = entities;
		this.reward = reward;
		this.fee = fee;
		this.timeLimitInS = timeLimit;
		this.name = name;
		this.area = area;
		this.description = description;
		this.client = client;
		this.aims = aims;
		this.fails = fails;

		this.state = QuestState.pending;
	}

	public QuestType getType() {
		return type;
	}

	public QuestState getState() {
		return state;
	}

	public EntityMHFCBase[] getEntities() {
		return entities;
	}

	public WorldGenerator getWorldGen() {
		return worldGen;
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

	public int getTimeLimitInS() {
		return timeLimitInS;
	}

	public String getName() {
		return name;
	}

	public String getArea() {
		return area;
	}

	public String getDescription() {
		return description;
	}

	public String getClient() {
		return client;
	}

	public String getAims() {
		return aims;
	}

	public String getFails() {
		return fails;
	}

	@Override
	public void questGoalStatusNotification(QuestGoal goal,
			EnumSet<QuestStatus> newStatus) {
		// TODO Auto-generated method stub

	}

	public boolean canJoin(EntityPlayer player) {
		// TODO add more evaluation
		if (state == QuestState.pending) {
			return true;
		}
		return false;
	}
	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}
}
