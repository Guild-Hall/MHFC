package mhfc.net.common.quests.factory;

import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.descriptions.HuntingGoalDescription;
import net.minecraft.entity.EntityList;
import net.minecraft.util.JsonUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import static mhfc.net.common.quests.descriptions.HuntingGoalDescription.*;

public class HuntingGoalFactory implements IGoalFactory {

	@Override
	public QuestGoal buildQuestGoal(GoalDescription gd) {
		return gd.build();
	}

	@Override
	public GoalDescription buildGoalDescription(JsonObject json,
		JsonDeserializationContext context) {
		if (!json.has(ID_HUNTED_TYPE) || !json.has(ID_AMOUNT))
			throw new JsonParseException("A hunting goal needs a "
				+ ID_HUNTED_TYPE + " and a " + ID_AMOUNT + "attribute");
		if (!JsonUtils.jsonObjectFieldTypeIsString(json, ID_HUNTED_TYPE))
			throw new JsonParseException("The target must be a mob id");
		String mobID = JsonUtils.getJsonElementStringValue(json
			.get(ID_HUNTED_TYPE), ID_HUNTED_TYPE);
		Class<?> goalClass = (Class<?>) EntityList.stringToClassMapping
			.get(mobID);
		if (goalClass == null) {
			throw new JsonParseException("The mob identifier " + mobID
				+ " could not be resolved");
		}
		if (!json.get(ID_AMOUNT).isJsonPrimitive())
			throw new JsonParseException(
				"The amount given is not of type integer");
		int amount = json.get(ID_AMOUNT).getAsInt();
		return new HuntingGoalDescription(goalClass, amount);
	}
}
