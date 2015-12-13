package mhfc.net.common.quests.api;

import mhfc.net.common.quests.IVisualInformation;

public abstract class QuestDescription {

	protected final String type;

	public abstract IVisualInformation getVisualInformation();

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
