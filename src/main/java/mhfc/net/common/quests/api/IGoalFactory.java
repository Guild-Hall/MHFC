package mhfc.net.common.quests.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public interface IGoalFactory {
	public GoalDescription buildGoalDescription(JsonElement jsonElement,
		JsonDeserializationContext context);

	public JsonObject serialize(GoalDescription description,
		JsonSerializationContext context);
}
