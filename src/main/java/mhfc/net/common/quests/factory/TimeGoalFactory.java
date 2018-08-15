package mhfc.net.common.quests.factory;

import static mhfc.net.common.quests.descriptions.TimeGoalDescription.ID_TIME;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.api.IGoalDefinition;
import mhfc.net.common.quests.api.IGoalDefinitionFactory;
import mhfc.net.common.quests.descriptions.TimeGoalDescription;

public class TimeGoalFactory implements IGoalDefinitionFactory {
	@Override
	public IGoalDefinition convertTo(
			JsonElement jsonE,
			JsonDeserializationContext context) {
		JsonObject json = jsonE.getAsJsonObject();
		if (!json.has(ID_TIME) || !json.get(ID_TIME).isJsonPrimitive()) {
			throw new JsonParseException(
					"Time goal needs one integer attribute " + ID_TIME);
		}
		int time = json.get(ID_TIME).getAsInt();
		return new TimeGoalDescription(time);
	}

	@Override
	public JsonObject convertFrom(IGoalDefinition description,
			JsonSerializationContext context) {
		TimeGoalDescription timeGoalDesc = (TimeGoalDescription) description;
		JsonObject holder = new JsonObject();
		holder.addProperty(ID_TIME, timeGoalDesc.getTime());
		return holder;
	}
}
