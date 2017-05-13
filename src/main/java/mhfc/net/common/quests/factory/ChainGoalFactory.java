package mhfc.net.common.quests.factory;

import static mhfc.net.common.quests.descriptions.ChainGoalDescription.ID_GOAL;
import static mhfc.net.common.quests.descriptions.ChainGoalDescription.ID_SUCCESSOR;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.IGoalDefinition;
import mhfc.net.common.quests.api.IGoalDefinitionFactory;
import mhfc.net.common.quests.descriptions.ChainGoalDescription;

public class ChainGoalFactory implements IGoalDefinitionFactory {
	@Override
	public IGoalDefinition convertTo(
			JsonElement jsonE,
			JsonDeserializationContext context) {
		JsonObject json = jsonE.getAsJsonObject();
		if (!json.has(ID_GOAL)) {
			throw new JsonParseException("A chain goal requires field "
					+ ID_GOAL);
		}
		GoalReference goal = context.deserialize(json.get(ID_GOAL), GoalReference.class);
		GoalReference successor = context.deserialize(json.get(ID_SUCCESSOR), GoalReference.class);
		return new ChainGoalDescription(goal, successor);
	}

	@Override
	public JsonObject convertFrom(IGoalDefinition description,
			JsonSerializationContext context) {
		ChainGoalDescription chainGoal = (ChainGoalDescription) description;
		JsonObject jsonObject = new JsonObject();
		JsonElement jsonGoal = context.serialize(chainGoal.getTrueGoal(),
				GoalReference.class);
		JsonElement jsonSuccessor = context.serialize(chainGoal
				.getSuccessorGoal(), GoalReference.class);
		jsonObject.add(ID_GOAL, jsonGoal);
		jsonObject.add(ID_SUCCESSOR, jsonSuccessor);
		return jsonObject;
	}

}
