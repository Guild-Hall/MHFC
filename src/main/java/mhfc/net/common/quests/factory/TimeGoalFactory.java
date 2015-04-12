package mhfc.net.common.quests.factory;

import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.descriptions.TimeGoalDescription;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import static mhfc.net.common.quests.descriptions.TimeGoalDescription.*;

public class TimeGoalFactory implements IGoalFactory {

	@Override
	public QuestGoal buildQuestGoal(GoalDescription gd) {
		return gd.build();
	}

	@Override
	public GoalDescription buildGoalDescription(JsonObject json,
		JsonDeserializationContext context) {
		if (!json.has(ID_TIME) || !json.get(ID_TIME).isJsonPrimitive())
			throw new JsonParseException(
				"Time goal needs one integer attribute " + ID_TIME);
		int time = json.get(ID_TIME).getAsInt();
		return new TimeGoalDescription(time);
	}
}
