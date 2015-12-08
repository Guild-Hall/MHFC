package mhfc.net.common.quests.factory;

import static mhfc.net.common.quests.descriptions.ForkGoalDescription.*;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.descriptions.ForkGoalDescription;

public class ForkGoalFactory implements IGoalFactory {
	@Override
	public GoalDescription buildGoalDescription(JsonObject json,
		JsonDeserializationContext context) {
		if (!json.has(ID_REQUIRED))
			throw new JsonParseException(
				"A fork does at least need a list of required goals. This could be empty, but is needed");
		GoalReference[] rqs = new GoalReference[0], opt = new GoalReference[0];
		rqs = context.<GoalReference[]> deserialize(json.get(ID_REQUIRED),
			GoalReference[].class);
		if (json.has(ID_OPTIONAL))
			opt = context.<GoalReference[]> deserialize(json.get(ID_OPTIONAL),
				GoalReference[].class);
		return new ForkGoalDescription(rqs, opt);
	}

	@Override
	public JsonObject serialize(GoalDescription description,
		JsonSerializationContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}
