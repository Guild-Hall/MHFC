package mhfc.net.common.quests.factory;

import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.descriptions.TimeGoalDescription;
import mhfc.net.common.quests.goals.TimeQuestGoal;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

public class TimeGoalFactory implements IGoalFactory {

	@Override
	public QuestGoal buildQuestGoal(GoalDescription gd) {
		TimeGoalDescription description = (TimeGoalDescription) gd;
		return new TimeQuestGoal(description.getTime());
	}

	@Override
	public GoalDescription buildGoalDescription(JsonElement json,
			JsonDeserializationContext context) {
		// FIXME Auto-generated method stub
		return null;
	}

}