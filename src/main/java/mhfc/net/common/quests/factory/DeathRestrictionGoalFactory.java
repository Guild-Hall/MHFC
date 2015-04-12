package mhfc.net.common.quests.factory;

import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.descriptions.DeathRestrictionDescription;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import static mhfc.net.common.quests.descriptions.DeathRestrictionDescription.*;

public class DeathRestrictionGoalFactory implements IGoalFactory {

	@Override
	public QuestGoal buildQuestGoal(GoalDescription gd) {
		return gd.build();
	}

	@Override
	public GoalDescription buildGoalDescription(JsonObject json,
		JsonDeserializationContext context) {
		if (!json.has(ID_LIFES) || !json.get(ID_LIFES).isJsonPrimitive())
			throw new JsonParseException(
				"A death restriction goal requires a \"lives\" integer");
		int lifes = json.get(ID_LIFES).getAsInt();
		return new DeathRestrictionDescription(lifes);
	}

}
