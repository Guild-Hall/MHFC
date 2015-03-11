package mhfc.net.common.quests.descriptions;

import java.util.ArrayList;
import java.util.List;

import mhfc.net.common.core.registry.MHFCQuestsRegistry;
import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.GoalReference;

public class ForkGoalDescription extends GoalDescription {

	public static final String ID_REQUIRED = "requisites";
	public static final String ID_OPTIONAL = "optional";

	private GoalReference[] required;
	private GoalReference[] optional;

	public ForkGoalDescription(GoalReference[] required,
			GoalReference[] optional) {
		super(MHFCQuestsRegistry.GOAL_FORK_TYPE);
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

}
