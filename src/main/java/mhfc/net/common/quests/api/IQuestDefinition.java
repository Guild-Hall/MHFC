package mhfc.net.common.quests.api;

import mhfc.net.client.quests.api.IVisualDefinition;
import mhfc.net.common.quests.Mission;
import mhfc.net.common.quests.world.QuestFlair;

public interface IQuestDefinition {
	String getSerializerType();

	IVisualDefinition getVisualInformation();

	QuestFlair getQuestFlair();

	Mission build(String missionID);
}
