package mhfc.net.common.quests.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.GeneralQuest;

public interface IQuestFactory {
	public GeneralQuest buildQuest(QuestDescription questDesc);

	public QuestDescription buildQuestDescription(JsonElement json,
		JsonDeserializationContext context);

	public JsonElement serialize(QuestDescription description,
		JsonSerializationContext context);
}
