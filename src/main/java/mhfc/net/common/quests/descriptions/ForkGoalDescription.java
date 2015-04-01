package mhfc.net.common.quests.descriptions;

import java.util.ArrayList;
import java.util.List;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.QuestFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.goals.ForkQuestGoal;

public class ForkGoalDescription extends GoalDescription {

	public static final String ID_REQUIRED = "requisites";
	public static final String ID_OPTIONAL = "optional";

	private GoalReference[] required;
	private GoalReference[] optional;

	public ForkGoalDescription(GoalReference[] required,
		GoalReference[] optional) {
		super(MHFCQuestBuildRegistry.GOAL_FORK_TYPE);
		this.required = required;
		this.optional = optional;
	}

	public List<GoalDescription> getRequired() {
		List<GoalDescription> list = new ArrayList<GoalDescription>();
		if (required == null)
			return list;
		for (int i = 0; i < required.length; i++) {
			list.add(required[i].getReferredDescription());
		}
		return list;
	}

	public List<GoalDescription> getOptional() {
		List<GoalDescription> list = new ArrayList<GoalDescription>();
		if (optional == null)
			return list;
		for (int i = 0; i < optional.length; i++) {
			list.add(optional[i].getReferredDescription());
		}
		return list;
	}

	@Override
	public ForkQuestGoal build() {
		List<GoalDescription> required = getRequired();
		List<GoalDescription> optional = getOptional();
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

}
