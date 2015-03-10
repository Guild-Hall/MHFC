package mhfc.net.common.quests.factory;

import java.util.List;

import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.QuestFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.descriptions.ForkGoalDescription;
import mhfc.net.common.quests.goals.ForkQuestGoal;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

public class ForkGoalFactory implements IGoalFactory {

	@Override
	public QuestGoal buildQuestGoal(GoalDescription goalDesc) {
		ForkGoalDescription description = (ForkGoalDescription) goalDesc;
		List<GoalDescription> required = description.getRequired();
		List<GoalDescription> optional = description.getOptional();
		ForkQuestGoal fork = new ForkQuestGoal(null);

		for (GoalDescription desc : required) {
			QuestGoal q = QuestFactory.constructGoal(desc);
			if (q == null)
				continue;
			fork.addRequisite(q);
		}

		for (GoalDescription desc : optional) {
			QuestGoal q = QuestFactory.constructGoal(desc);
			if (q == null)
				continue;
			fork.addOptional(q);
		}

		return fork;
	}

	@Override
	public GoalDescription buildGoalDescription(JsonElement json,
			JsonDeserializationContext context) {
		// FIXME Auto-generated method stub
		return null;
	}

}