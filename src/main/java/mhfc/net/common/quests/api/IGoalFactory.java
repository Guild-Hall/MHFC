package mhfc.net.common.quests.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

public interface IGoalFactory {
	public QuestGoal buildQuestGoal(GoalDescription goalDesc);

	public GoalDescription buildGoalDescription(JsonObject json,
			JsonDeserializationContext context);

}