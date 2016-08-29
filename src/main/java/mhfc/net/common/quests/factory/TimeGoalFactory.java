package mhfc.net.common.quests.factory;

import static mhfc.net.common.quests.descriptions.TimeGoalDescription.*;

import com.google.gson.*;

import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.api.IGoalDefinitionFactory;
import mhfc.net.common.quests.descriptions.TimeGoalDescription;

public class TimeGoalFactory implements IGoalDefinitionFactory {
	@Override
	public GoalDefinition buildGoalDescription(JsonElement jsonE,
		JsonDeserializationContext context) {
		JsonObject json = jsonE.getAsJsonObject();
		if (!json.has(ID_TIME) || !json.get(ID_TIME).isJsonPrimitive())
			throw new JsonParseException(
				"Time goal needs one integer attribute " + ID_TIME);
		int time = json.get(ID_TIME).getAsInt();
		return new TimeGoalDescription(time);
	}

	@Override
	public JsonObject serialize(GoalDefinition description,
		JsonSerializationContext context) {
		TimeGoalDescription timeGoalDesc = (TimeGoalDescription) description;
		JsonObject holder = new JsonObject();
		holder.addProperty(ID_TIME, timeGoalDesc.getTime());
		return holder;
	}
}
