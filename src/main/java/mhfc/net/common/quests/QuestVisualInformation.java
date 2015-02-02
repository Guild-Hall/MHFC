package mhfc.net.common.quests;

import mhfc.net.common.core.registry.MHFCRegQuests;

public class QuestVisualInformation {

	public static final QuestVisualInformation LOADING_REPLACEMENT = new QuestVisualInformation(
			"Loading...", "Waiting for server response", "Guild hunter", "---",
			"----", "Town", "No time limit", "A quest", "None", "---",
			QuestType.EpicHunting);
	public static final QuestVisualInformation IDENTIFIER_ERROR = new QuestVisualInformation(
			"Identifier invalid",
			"Please contact the server operator so he can give information to the mod team",
			"MHFC mod team", "Work out this issue", "Not contacting anyone",
			"Network or server", "Just do it asap", "A better experience",
			"A few seconds of your time", "Hopefully one", QuestType.Gathering);

	public enum QuestType {
		Hunting(MHFCRegQuests.QUEST_TYPE_HUNTING), EpicHunting(
				MHFCRegQuests.QUEST_TYPE_EPIC_HUNTING), Killing(
				MHFCRegQuests.QUEST_TYPE_KILLING), Gathering(
				MHFCRegQuests.QUEST_TYPE_GATHERING);
		QuestType(String s) {
			this.s = s;
		}

		public String getAsString() {
			return s;
		}

		String s;
	}

	protected final String name;
	protected final String description;
	protected final String client;
	protected final String aims;
	protected final String fails;

	protected final String areaNameId;
	protected final String timeLimitInS;
	protected final QuestType type;

	protected final String reward;
	protected final String fee;
	protected final String maxPartySize;

	public QuestVisualInformation(String name, String description,
			String client, String aims, String fails, String areaNameID,
			String timeLimitInS, String reward, String fee,
			String maxPartySize, QuestType type) {
		this.name = name;
		this.type = type;
		this.timeLimitInS = timeLimitInS;
		this.description = description;
		this.client = client;
		this.aims = aims;
		this.fails = fails;
		this.reward = reward;
		this.fee = fee;
		this.maxPartySize = maxPartySize;
		this.areaNameId = areaNameID;

	}

	public String getName() {
		return name;
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

	public String getTimeLimitAsString() {
		return timeLimitInS;
	}

	public QuestType getType() {
		return type;
	}

	public String getRewardString() {
		return reward;
	}

	public String getFeeString() {
		return fee;
	}

	public String getAreaID() {
		return areaNameId;
	}

	public String getMaxPartySize() {
		return maxPartySize;
	}

}
