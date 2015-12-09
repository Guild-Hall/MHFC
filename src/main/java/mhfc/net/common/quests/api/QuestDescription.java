package mhfc.net.common.quests.api;

import mhfc.net.common.quests.QuestVisualInformation;

public abstract class QuestDescription {

	protected final String type;

	public abstract QuestVisualInformation getVisualInformation();

	public abstract int getMaxPartySize();

	public abstract String getAreaID();

	public abstract int getFee();

	public abstract int getReward();

	public abstract GoalReference getGoalReference();

	public QuestDescription(String questDefault) {
		type = questDefault;
	}

	public String getType() {
		return type;
	}

}
