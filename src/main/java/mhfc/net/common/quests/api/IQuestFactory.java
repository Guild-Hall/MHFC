package mhfc.net.common.quests.api;

import mhfc.net.common.quests.GeneralQuest;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

public interface IQuestFactory {
	public GeneralQuest buildQuest(QuestDescription questDesc);

	public QuestDescription buildQuestDescription(JsonElement json,
			JsonDeserializationContext context);
}