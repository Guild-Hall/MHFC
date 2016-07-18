package mhfc.net.common.quests.api;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.GeneralQuest;

public abstract class QuestDefinition implements IQuestInformation {

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

	public QuestDefinition(String serializerType) {
		this.serializerType = serializerType;
	}

	public String getSerializerType() {
		return serializerType;
	}

	public abstract GeneralQuest build();
}
