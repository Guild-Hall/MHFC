package mhfc.net.common.quests.api;

import mhfc.net.common.quests.GeneralQuest;

public abstract class QuestDefinition implements IQuestDefinition {

	protected final String serializerType;

	public QuestDefinition(String serializerType) {
		this.serializerType = serializerType;
	}

	@Override
	public String getSerializerType() {
		return serializerType;
	}

	@Override
	public abstract GeneralQuest build();
}
