package mhfc.net.common.quests.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

public interface IGoalFactory {
	public QuestGoal buildQuestGoal(GoalDescription goalDesc);

	public GoalDescription buildGoalDescription(JsonElement json,
			JsonDeserializationContext context);

}