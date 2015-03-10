package mhfc.net.common.quests.descriptions;

import java.util.ArrayList;
import java.util.List;

import mhfc.net.common.core.registry.MHFCQuestsRegistry;
import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.GoalReference;

import com.sun.istack.internal.NotNull;

public class ForkGoalDescription extends GoalDescription {

	private GoalReference[] required;
	private GoalReference[] optional;

	public ForkGoalDescription(GoalReference[] required,
			GoalReference[] optional) {
		super(MHFCQuestsRegistry.GOAL_FORK_TYPE);
		this.required = required;
		this.optional = optional;
	}

	@NotNull
	public List<GoalDescription> getRequired() {
		List<GoalDescription> list = new ArrayList<GoalDescription>();
		if (required == null)
			return list;
		for (int i = 0; i < required.length; i++) {
			list.add(required[i].getReferredDescription());
		}
		return list;
	}

	@NotNull
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
