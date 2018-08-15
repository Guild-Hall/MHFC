package mhfc.net.common.quests.factory;

import static mhfc.net.common.quests.descriptions.ForkGoalDescription.ID_OPTIONAL;
import static mhfc.net.common.quests.descriptions.ForkGoalDescription.ID_REQUIRED;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.IGoalDefinition;
import mhfc.net.common.quests.api.IGoalDefinitionFactory;
import mhfc.net.common.quests.descriptions.ForkGoalDescription;

public class ForkGoalFactory implements IGoalDefinitionFactory {
	@Override
	public IGoalDefinition convertTo(
			JsonElement jsonE,
			JsonDeserializationContext context) {
		JsonObject json = jsonE.getAsJsonObject();
		if (!json.has(ID_REQUIRED)) {
			throw new JsonParseException(
					"A fork does at least need a list of required goals. This could be empty, but is needed");
		}
		GoalReference[] rqs = new GoalReference[0], opt = new GoalReference[0];
		rqs = context.<GoalReference[]> deserialize(json.get(ID_REQUIRED),
				GoalReference[].class);
		if (json.has(ID_OPTIONAL)) {
			opt = context.<GoalReference[]> deserialize(json.get(ID_OPTIONAL),
					GoalReference[].class);
		}
		return new ForkGoalDescription(rqs, opt);
	}

	@Override
	public JsonObject convertFrom(IGoalDefinition description,
			JsonSerializationContext context) {
		ForkGoalDescription forkGoal = (ForkGoalDescription) description;
		JsonObject holder = new JsonObject();
		JsonElement jsonRequired = context.serialize(forkGoal.getRequired()
				.toArray(), GoalReference[].class);
		JsonElement jsonOptional = context.serialize(forkGoal.getOptional()
				.toArray(), GoalReference[].class);
		holder.add(ID_REQUIRED, jsonRequired);
		holder.add(ID_OPTIONAL, jsonOptional);
		return holder;
	}

}
