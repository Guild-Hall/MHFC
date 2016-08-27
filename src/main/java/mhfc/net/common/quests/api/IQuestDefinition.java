package mhfc.net.common.quests.api;

import mhfc.net.client.quests.api.IVisualDefinition;
import mhfc.net.common.quests.GeneralQuest;
import mhfc.net.common.quests.world.QuestFlair;

public interface IQuestDefinition {
	String getSerializerType();

	IVisualDefinition getVisualInformation();

	QuestFlair getQuestFlair();

	GeneralQuest build();
}
