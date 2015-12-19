package mhfc.net.common.quests.api;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.IVisualInformation;

public abstract class QuestDescription {

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

	protected final String serializerType;

	public abstract IVisualInformation getVisualInformation();

	public abstract int getMaxPartySize();

	public abstract String getAreaID();

	public abstract int getFee();

	public abstract int getReward();

	public abstract GoalReference getGoalReference();

	public abstract QuestType getQuestType();

	public QuestDescription(String questDefault) {
		serializerType = questDefault;
	}

	public String getType() {
		return serializerType;
	}

}
