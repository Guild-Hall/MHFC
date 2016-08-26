package mhfc.net.common.quests.descriptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.QuestFactories;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.goals.ForkQuestGoal;
import mhfc.net.common.quests.properties.GroupProperty;

public class ForkGoalDescription extends GoalDefinition {

	public static final String ID_REQUIRED = "requisites";
	public static final String ID_OPTIONAL = "optional";

	private GoalReference[] required;
	private GoalReference[] optional;

	public ForkGoalDescription(GoalReference[] required, GoalReference[] optional) {
		super(MHFCQuestBuildRegistry.GOAL_FORK_TYPE);
		this.required = required;
		this.optional = optional;
	}

	public List<GoalReference> getRequired() {
		List<GoalReference> list = new ArrayList<>();
		if (required == null) {
			return list;
		}
		list.addAll(Arrays.asList(required));
		return list;
	}

	public List<GoalReference> getOptional() {
		List<GoalReference> list = new ArrayList<>();
		if (optional == null) {
			return list;
		}
		list.addAll(Arrays.asList(optional));
		return list;
	}

	@Override
	public ForkQuestGoal build(GroupProperty properties) {
		ForkQuestGoal fork = new ForkQuestGoal(null);

		int r = 0;
		for (GoalReference req : getRequired()) {
			QuestGoal constructGoal = QuestFactories.constructGoal(
					req.getReferredDescription(),
					properties.newMember("R" + r++, GroupProperty.construct()));
			if (constructGoal == null) {
				continue;
			}
			fork.addRequisite(constructGoal);
		}

		r = 0;
		for (GoalReference opt : getOptional()) {
			QuestGoal constructGoal = QuestFactories.constructGoal(
					opt.getReferredDescription(),
					properties.newMember("O" + r++, GroupProperty.construct()));
			if (constructGoal == null) {
				continue;
			}
			fork.addOptional(constructGoal);
		}
		return fork;
	}

}
