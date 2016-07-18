package mhfc.net.common.quests.api;

import mhfc.net.common.quests.api.QuestDefinition.QuestType;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.area.IAreaType;

public interface IQuestInformation {
	public IVisualInformation getVisualInformation();

	public int getMaxPartySize();

	public IAreaType getAreaType();

	public int getFee();

	public int getReward();

	public GoalReference getGoalReference();

	public QuestType getQuestType();

	public QuestFlair getQuestFlair();
}
