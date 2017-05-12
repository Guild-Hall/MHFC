package mhfc.net.common.quests.descriptions;

import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import mhfc.net.client.quests.DefaultQuestVisualDefinition;
import mhfc.net.common.core.registry.MHFCDimensionRegistry;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.Mission;
import mhfc.net.common.quests.QuestFactories;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.IQuestDefinition;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.types.ArenaType;

/**
 * Used by the QuestFactories as well as to display quests.
 */
public class DefaultQuestDescription implements IQuestDefinition {
	public static final DefaultQuestDescription UNKNOWN_DESCRIPTION = new DefaultQuestDescription(
			null,
			QuestType.Gathering,
			ArenaType.INSTANCE,
			QuestFlair.DAYTIME,
			0,
			0,
			q -> DefaultQuestVisualDefinition.UNKNOWN);

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
	public static final String KEY_GOAL = "goal";
	public static final String KEY_VISUAL = "visual";

	protected GoalReference goalReference;
	protected DefaultQuestVisualDefinition visual;
	protected QuestType questType;

	protected IAreaType areaType;
	protected QuestFlair questFlair;

	protected int fee;
	protected int maxPartySize;

	public DefaultQuestDescription(
			GoalReference goalDescID,
			QuestType type,
			IAreaType areaId,
			QuestFlair flair,
			int fee,
			int maxPartySize,
			Function<DefaultQuestDescription, DefaultQuestVisualDefinition> visual) {
		this.goalReference = goalDescID;
		this.questType = type;
		this.areaType = areaId;
		this.questFlair = flair;
		this.fee = fee;
		this.maxPartySize = maxPartySize;
		this.visual = Objects.requireNonNull(visual.apply(this));
	}

	public GoalReference getGoalReference() {
		return goalReference;
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

	public QuestGoal buildGoal(GroupProperty propertyRoot) {
		return QuestFactories.constructGoal(getGoalReference().getReferredDescription(), propertyRoot);
	}

	public IGoalFactory bindGoalVisuals(GroupProperty propertyRoot) {
		return QuestFactories.constructGoalVisualsFactory(getGoalReference().getReferredDescription(), propertyRoot);
	}

	@Override
	public Mission build(String missionID) {
		GroupProperty rootProperties = GroupProperty.makeRootProperty();
		QuestGoal goal = buildGoal(rootProperties);
		if (goal == null) {
			return null;
		}
		IAreaType areaType = getAreaType();

		CompletionStage<IActiveArea> activeArea = MHFCDimensionRegistry
				.getUnusedInstance(areaType, getQuestFlair());
		if (activeArea == null) {
			return null;
		}

		return new Mission(missionID, goal, rootProperties, getMaxPartySize(), getFee(), activeArea, this);
	}

}
