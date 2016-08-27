package mhfc.net.common.quests.factory;

import static mhfc.net.common.quests.descriptions.HuntingGoalDescription.ID_AMOUNT;
import static mhfc.net.common.quests.descriptions.HuntingGoalDescription.ID_HUNTED_TYPE;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.api.IGoalDefinitionFactory;
import mhfc.net.common.quests.descriptions.HuntingGoalDescription;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.JsonUtils;

public class HuntingGoalFactory implements IGoalDefinitionFactory {
	@Override
	public GoalDefinition buildGoalDescription(JsonElement jsonE, JsonDeserializationContext context) {
		JsonObject json = jsonE.getAsJsonObject();
		if (!json.has(ID_HUNTED_TYPE) || !json.has(ID_AMOUNT)) {
			throw new JsonParseException(
					"A hunting goal needs a " + ID_HUNTED_TYPE + " and a " + ID_AMOUNT + " attribute");
		}
		String mobID = JsonUtils.getJsonElementStringValue(json.get(ID_HUNTED_TYPE), ID_HUNTED_TYPE);
		@SuppressWarnings("unchecked")
		Class<? extends Entity> goalClass = (Class<? extends Entity>) EntityList.stringToClassMapping.get(mobID);
		if (goalClass == null) {
			throw new JsonParseException("The mob identifier " + mobID + " could not be resolved");
		}
		if (!json.get(ID_AMOUNT).isJsonPrimitive()) {
			throw new JsonParseException("The amount given is not of type integer");
		}
		int amount = json.get(ID_AMOUNT).getAsInt();
		return new HuntingGoalDescription(goalClass, amount);
	}

	@Override
	public JsonObject serialize(GoalDefinition description, JsonSerializationContext context) {
		HuntingGoalDescription huntingGoal = (HuntingGoalDescription) description;
		JsonObject holder = new JsonObject();
		String huntedName = (String) EntityList.classToStringMapping.get(huntingGoal.getHuntedClass());
		holder.addProperty(ID_HUNTED_TYPE, huntedName);
		holder.addProperty(ID_AMOUNT, huntingGoal.getAmount());
		return holder;
	}
}
