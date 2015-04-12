package mhfc.net.common.quests.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

public interface IGoalFactory {
	@Deprecated
	public QuestGoal buildQuestGoal(GoalDescription goalDesc);

	public GoalDescription buildGoalDescription(JsonObject json,
		JsonDeserializationContext context);

}
