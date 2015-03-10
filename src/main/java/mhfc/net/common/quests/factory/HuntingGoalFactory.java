package mhfc.net.common.quests.factory;

import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.descriptions.HuntingGoalDescription;
import mhfc.net.common.quests.goals.HuntingQuestGoal;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

public class HuntingGoalFactory implements IGoalFactory {

	@Override
	public QuestGoal buildQuestGoal(GoalDescription gd) {
		HuntingGoalDescription description = (HuntingGoalDescription) gd;
		return new HuntingQuestGoal(null, description.getHuntedClass(),
				description.getAmount());
	}

	@Override
	public GoalDescription buildGoalDescription(JsonElement json,
			JsonDeserializationContext context) {
		// if (gd.getArguments().length != 2
		// || !String.class.isAssignableFrom(gd.getArguments()[0]
		// .getClass())
		// || !String.class.isAssignableFrom(gd.getArguments()[1]
		// .getClass()))
		// throw new IllegalArgumentException(
		// "[MHFC] A hunter goal needs a String and a string representing an integer argument");
		//
		// Class<?> goalClass = (Class<?>) EntityList.stringToClassMapping
		// .get(gd.arguments[0]);
		// if (goalClass == null) {
		// System.out.println(gd.getArguments()[0]);
		// throw new IllegalArgumentException(
		// "[MHFC] The mob identifier could not be resolved");
		// }
		// int number = Integer.parseInt((String) gd.getArguments()[1]);
		// FIXME Auto-generated method stub
		return null;
	}

}