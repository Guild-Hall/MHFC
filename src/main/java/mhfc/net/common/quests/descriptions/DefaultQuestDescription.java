package mhfc.net.common.quests.descriptions;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.IVisualInformation;
import mhfc.net.common.quests.QuestVisualInformation;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.QuestDescription;
import mhfc.net.common.world.area.AreaRegistry;
import mhfc.net.common.world.area.IAreaType;

/**
 * Used by the QuestFactory as well as to display quests.
 */
public class DefaultQuestDescription extends QuestDescription {

	public static final String KEY_MAX_PARTY_SIZE = "maxPartySize";
	public static final String KEY_QUEST_TYPE = "questType";
	// public static final String KEY_TIME_LIMIT = "timeLimit";
	public static final String KEY_AREA_ID = "areaID";
	public static final String KEY_FEE = "fee";
	public static final String KEY_REWARD = "reward";
	public static final String KEY_GOAL = "goal";
	public static final String KEY_VISUAL = "visual";

	protected GoalReference goalReference;
	protected IVisualInformation visual;
	protected QuestType questType;

	protected IAreaType areaType;

	protected int reward;
	protected int fee;
	protected int maxPartySize;

	public DefaultQuestDescription(
			GoalReference goalDescID,
			QuestDescription.QuestType type,
			String areaId,
			int reward,
			int fee,
			int maxPartySize) {
		super(MHFCQuestBuildRegistry.QUEST_DEFAULT);
		this.goalReference = goalDescID;
		this.questType = type;
		this.areaType = AreaRegistry.instance.getType(areaId);
		this.reward = reward;
		this.fee = fee;
		this.maxPartySize = maxPartySize;
		this.visual = QuestVisualInformation.UNKNOWN;
	}

	public void setVisualInformation(IVisualInformation visualInformation) {
		this.visual = visualInformation;
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
	public IAreaType getAreaType() {
		return areaType;
	}

	@Override
	public int getMaxPartySize() {
		return maxPartySize;
	}

	@Override
	public IVisualInformation getVisualInformation() {
		return visual;
	}

	@Override
	public QuestType getQuestType() {
		return questType;
	}

}
