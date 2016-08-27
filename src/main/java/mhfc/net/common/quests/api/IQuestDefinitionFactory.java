package mhfc.net.common.quests.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;

public interface IQuestDefinitionFactory {
	public QuestDefinition buildQuestDescription(JsonElement json, JsonDeserializationContext context);

	public JsonElement serialize(QuestDefinition description, JsonSerializationContext context);
}
