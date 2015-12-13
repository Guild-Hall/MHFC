package mhfc.net.common.quests.descriptions;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.IVisualInformation;
import mhfc.net.common.quests.QuestVisualInformation;
import mhfc.net.common.quests.QuestVisualInformation.QuestType;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.QuestDescription;

/**
 * Used by the QuestFactory as well as to display quests.
 */
public class DefaultQuestDescription extends QuestDescription {

	public static final String KEY_MAX_PARTY_SIZE = "maxPartySize";
	public static final String KEY_TYPE = "type";
	public static final String KEY_TIME_LIMIT = "timeLimit";
	public static final String KEY_AREA_ID = "areaID";
	public static final String KEY_FEE = "fee";
	public static final String KEY_REWARD = "reward";
	public static final String KEY_GOAL = "goal";

	protected GoalReference goalReference;
	protected IVisualInformation visual;

	protected String areaId;

	protected int reward;
	protected int fee;
	protected int maxPartySize;

	public DefaultQuestDescription(GoalReference goalDescID, String name,
		QuestType type, int reward, int fee, int maxPartySize, String areaId,
		String description, String client, String aims, String fails,
		String rewardAsS, String feeAsS, String timeLimitAsS,
		String maxPartySizeAsS) {
		super(MHFCQuestBuildRegistry.QUEST_DEFAULT);
		this.goalReference = goalDescID;
		this.areaId = areaId;
		this.reward = reward;
		this.fee = fee;
		this.maxPartySize = maxPartySize;
		this.visual = new QuestVisualInformation(name, description, client,
			aims, fails, resolveAreaIDToName(this.areaId), timeLimitAsS,
			rewardAsS, feeAsS, maxPartySizeAsS, type);
	}

	public void setVisualInformation(IVisualInformation visualInformation) {

	}

	private String resolveAreaIDToName(String areaId2) {
		// TODO Really resolve, once we have an area system in place
		return areaId2;
	}

	@Override
	public GoalReference getGoalReference() {
		return goalReference;
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
	public String getAreaID() {
		return areaId;
	}

	@Override
	public int getMaxPartySize() {
		return maxPartySize;
	}

	@Override
	public IVisualInformation getVisualInformation() {
		return visual;
	}

}
