package mhfc.net.common.quests.factory;

import static mhfc.net.common.quests.descriptions.DeathRestrictionDescription.ID_LIVES;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.api.IGoalDefinition;
import mhfc.net.common.quests.api.IGoalDefinitionFactory;
import mhfc.net.common.quests.descriptions.DeathRestrictionDescription;

public class DeathRestrictionGoalFactory implements IGoalDefinitionFactory {

	@Override
	public IGoalDefinition convertTo(
			JsonElement jsonE,
			JsonDeserializationContext context) {
		JsonObject json = jsonE.getAsJsonObject();
		if (!json.has(ID_LIVES) || !json.get(ID_LIVES).isJsonPrimitive()) {
			throw new JsonParseException(
					"A death restriction goal requires a \"lives\" integer");
		}
		int lifes = json.get(ID_LIVES).getAsInt();
		return new DeathRestrictionDescription(lifes);
	}

	@Override
	public JsonObject convertFrom(IGoalDefinition description,
			JsonSerializationContext context) {
		DeathRestrictionDescription deathGoal = (DeathRestrictionDescription) description;
		JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty(ID_LIVES, deathGoal.getAllowedDeaths());
		return jsonObject;
	}

}
