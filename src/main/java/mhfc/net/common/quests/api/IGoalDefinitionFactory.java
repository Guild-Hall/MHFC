package mhfc.net.common.quests.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;

public interface IGoalDefinitionFactory {
	public GoalDefinition buildGoalDescription(JsonElement jsonElement,
		JsonDeserializationContext context);

	public JsonElement serialize(GoalDefinition description,
		JsonSerializationContext context);
}
