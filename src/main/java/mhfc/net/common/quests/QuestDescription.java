package mhfc.net.common.quests;

import mhfc.net.common.core.registry.MHFCRegQuests;

/**
 * Used by the QuestFactory as well as to display quests.
 */
public class QuestDescription {
	public enum QuestType {
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

	public static final QuestType QT_HUNTING = QuestType.Hunting;
	public static final QuestType QT_EPIC_HUNTING = QuestType.EpicHunting;
	public static final QuestType QT_KILLING = QuestType.Killing;
	public static final QuestType QT_GATHERING = QuestType.Gathering;

	protected String goalDescID;
	protected GoalDescription goalDesc;
	protected String name;
	protected QuestType type;
	protected int timeLimitInS;
	protected String description;
	protected String client;
	protected String aims;
	protected String fails;

	protected String areaId;

	protected int reward;
	protected int fee;
	protected int maxPartySize;

	public QuestDescription(String goalDescID, String name, QuestType type,
			int reward, int fee, int maxPartySize, int timeLimit,
			String areaId, String description, String client, String aims,
			String fails) {
		this.goalDescID = goalDescID;
		this.name = name;
		this.type = type;
		this.timeLimitInS = timeLimit;
		this.description = description;
		this.client = client;
		this.aims = aims;
		this.fails = fails;
		this.areaId = areaId;
		this.reward = reward;
		this.fee = fee;
		this.maxPartySize = maxPartySize;
	}

	public QuestDescription(GoalDescription goalDesc, String name,
			QuestType type, int reward, int fee, int maxPartySize,
			int timeLimit, String areaId, String description, String client,
			String aims, String fails) {
		this((String) null, name, type, reward, fee, maxPartySize, timeLimit,
				areaId, description, client, aims, fails);
		this.goalDesc = goalDesc;
	}

	public GoalDescription getGoalDescription() {
		if (goalDescID == null)
			return goalDesc;
		return MHFCRegQuests.getGoalDescription(goalDescID);
	}

	public String getName() {
		return name;
	}

	public QuestType getType() {
		return type;
	}

	public int getTimeLimitInS() {
		return timeLimitInS;
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

	public int getReward() {
		return reward;
	}

	public int getFee() {
		return fee;
	}

	public String getAreaID() {
		return areaId;
	}

	public int getMaxPartySize() {
		return maxPartySize;
	}

}
