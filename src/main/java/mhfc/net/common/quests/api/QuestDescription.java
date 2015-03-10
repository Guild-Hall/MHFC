package mhfc.net.common.quests.api;

import mhfc.net.common.core.registry.MHFCQuestsRegistry;
import mhfc.net.common.quests.QuestVisualInformation;
import mhfc.net.common.quests.QuestVisualInformation.QuestType;

/**
 * Used by the QuestFactory as well as to display quests.
 */
public class QuestDescription {

	protected String goalDescID;
	protected GoalDescription goalDesc;
	protected QuestVisualInformation visual;

	protected String areaId;

	protected int reward;
	protected int fee;
	protected int maxPartySize;

	public QuestDescription(String goalDescID, String name, QuestType type,
			int reward, int fee, int maxPartySize, String areaId,
			String description, String client, String aims, String fails,
			String rewardAsS, String feeAsS, String timeLimitAsS,
			String maxPartySizeAsS) {
		this.goalDescID = goalDescID;
		this.areaId = areaId;
		this.reward = reward;
		this.fee = fee;
		this.maxPartySize = maxPartySize;
		this.visual = new QuestVisualInformation(name, description, client,
				aims, fails, resolveAreaIDToName(this.areaId), timeLimitAsS,
				rewardAsS, feeAsS, maxPartySizeAsS, type);
	}

	private String resolveAreaIDToName(String areaId2) {
		// TODO Really resolve, once we have an area system in place
		return areaId2;
	}

	public QuestDescription(GoalDescription goalDesc, String name,
			QuestType type, int reward, int fee, int maxPartySize,
			String areaId, String description, String client, String aims,
			String fails, String rewardAsS, String feeAsS, String timeLimitAsS,
			String maxPartySizeAsS) {
		this((String) null, name, type, reward, fee, maxPartySize, areaId,
				description, client, aims, fails, rewardAsS, feeAsS,
				timeLimitAsS, maxPartySizeAsS);
		this.goalDesc = goalDesc;
	}

	public GoalDescription getGoalDescription() {
		if (goalDescID == null)
			return goalDesc;
		return MHFCQuestsRegistry.getGoalDescription(goalDescID);
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

	public QuestVisualInformation getVisualInformation() {
		return visual;
	}

	public String getFactory() {
		return "";
	}

}
