package mhfc.net.common.quests.descriptions;

import java.util.concurrent.CompletionStage;

import mhfc.net.client.quests.DefaultQuestVisualDefinition;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.Mission;
import mhfc.net.common.quests.QuestFactories;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.QuestDefinition;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.quests.world.GlobalAreaManager;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;

/**
 * Used by the QuestFactories as well as to display quests.
 */
public class DefaultQuestDescription extends QuestDefinition {

	public enum QuestType {
		Hunting(MHFCQuestBuildRegistry.QUEST_TYPE_HUNTING),
		EpicHunting(MHFCQuestBuildRegistry.QUEST_TYPE_EPIC_HUNTING),
		Killing(MHFCQuestBuildRegistry.QUEST_TYPE_KILLING),
		Gathering(MHFCQuestBuildRegistry.QUEST_TYPE_GATHERING);

		QuestType(String s) {
			this.s = s;
		}

		public String getAsString() {
			return s;
		}

		String s;
	}

	public static final String KEY_MAX_PARTY_SIZE = "maxPartySize";
	public static final String KEY_QUEST_TYPE = "questType";
	// public static final String KEY_TIME_LIMIT = "timeLimit";
	public static final String KEY_AREA_ID = "areaID";
	public static final String KEY_FLAIR = "flair";
	public static final String KEY_FEE = "fee";
	public static final String KEY_REWARD = "reward";
	public static final String KEY_GOAL = "goal";
	public static final String KEY_VISUAL = "visual";

	protected GoalReference goalReference;
	protected DefaultQuestVisualDefinition visual;
	protected QuestType questType;

	protected IAreaType areaType;
	protected QuestFlair questFlair;

	protected int reward;
	protected int fee;
	protected int maxPartySize;

	public DefaultQuestDescription(
			GoalReference goalDescID,
			QuestType type,
			IAreaType areaId,
			QuestFlair flair,
			int reward,
			int fee,
			int maxPartySize) {
		super(MHFCQuestBuildRegistry.QUEST_DEFAULT);
		this.goalReference = goalDescID;
		this.questType = type;
		this.areaType = areaId;
		this.questFlair = flair;
		this.reward = reward;
		this.fee = fee;
		this.maxPartySize = maxPartySize;
		this.visual = DefaultQuestVisualDefinition.UNKNOWN;
	}

	public void setVisualInformation(DefaultQuestVisualDefinition visualInformation) {
		this.visual = visualInformation;
	}

	public GoalReference getGoalReference() {
		return goalReference;
	}

	public int getReward() {
		return reward;
	}

	public int getFee() {
		return fee;
	}

	public IAreaType getAreaType() {
		return areaType;
	}

	public int getMaxPartySize() {
		return maxPartySize;
	}

	@Override
	public DefaultQuestVisualDefinition getVisualInformation() {
		return visual;
	}

	public QuestType getQuestType() {
		return questType;
	}

	@Override
	public QuestFlair getQuestFlair() {
		return questFlair;
	}

	@Override
	public Mission build() {
		GroupProperty goalProperties = GroupProperty.makeRootProperty();
		QuestGoal goal = QuestFactories.constructGoal(getGoalReference().getReferredDescription(), goalProperties);
		if (goal == null) {
			return null;
		}
		IAreaType areaType = getAreaType();

		CompletionStage<IActiveArea> activeArea = GlobalAreaManager.getInstance()
				.getUnusedInstance(areaType, getQuestFlair());
		if (activeArea == null) {
			return null;
		}

		return new Mission(goal, goalProperties, getMaxPartySize(), getReward(), getFee(), activeArea, this);
	}

}
