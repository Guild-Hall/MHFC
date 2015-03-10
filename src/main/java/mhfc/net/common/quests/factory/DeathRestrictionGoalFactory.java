package mhfc.net.common.quests.factory;

import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.descriptions.DeathRestrictionDescription;
import mhfc.net.common.quests.goals.DeathRestrictionQuestGoal;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

public class DeathRestrictionGoalFactory implements IGoalFactory {

	@Override
	public QuestGoal buildQuestGoal(GoalDescription gd) {
		DeathRestrictionDescription description = (DeathRestrictionDescription) gd;
		return new DeathRestrictionQuestGoal(description.getAllowedDeaths());
	}

	@Override
	public GoalDescription buildGoalDescription(JsonElement json,
			JsonDeserializationContext context) {
		// FIXME Auto-generated method stub
		return null;
	}

}