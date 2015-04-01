package mhfc.net.common.quests.factory;

import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.descriptions.ChainGoalDescription;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import static mhfc.net.common.quests.descriptions.ChainGoalDescription.*;

public class ChainGoalFactory implements IGoalFactory {

	@Override
	public QuestGoal buildQuestGoal(GoalDescription gd) {
		return gd.build();
	}

	@Override
	public GoalDescription buildGoalDescription(JsonObject json,
		JsonDeserializationContext context) {
		if (!json.has(ID_GOAL))
			throw new JsonParseException("A chain goal requires field "
				+ ID_GOAL);
		GoalReference goal, successor;
		goal = GoalReference.constructFromJson(json.get(ID_GOAL), context);
		successor = GoalReference.constructFromJson(json.get(ID_SUCCESSOR),
			context);
		return new ChainGoalDescription(goal, successor);
	}
}
