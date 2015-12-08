package mhfc.net.common.quests.factory;

import static mhfc.net.common.quests.descriptions.TimeGoalDescription.*;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.descriptions.TimeGoalDescription;

public class TimeGoalFactory implements IGoalFactory {
	@Override
	public GoalDescription buildGoalDescription(JsonObject json,
		JsonDeserializationContext context) {
		if (!json.has(ID_TIME) || !json.get(ID_TIME).isJsonPrimitive())
			throw new JsonParseException(
				"Time goal needs one integer attribute " + ID_TIME);
		int time = json.get(ID_TIME).getAsInt();
		return new TimeGoalDescription(time);
	}

	@Override
	public JsonObject serialize(GoalDescription description,
		JsonSerializationContext context) {
		// TODO Auto-generated method stub
		return null;
	}
}
