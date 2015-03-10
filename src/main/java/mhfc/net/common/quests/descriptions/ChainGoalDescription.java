package mhfc.net.common.quests.descriptions;

import mhfc.net.common.core.registry.MHFCQuestsRegistry;
import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.GoalReference;

public class ChainGoalDescription extends GoalDescription {

	GoalReference trueGoal, successorGoal;

	public ChainGoalDescription(GoalReference trueGoal,
			GoalReference successorGoal) {
		super(MHFCQuestsRegistry.GOAL_CHAIN_TYPE);
		this.trueGoal = trueGoal;
		this.successorGoal = successorGoal;
	}

	public GoalDescription getTrueGoal() {
		return trueGoal.getReferredDescription();
	}

	public GoalDescription getSuccessorGoal() {
		return successorGoal.getReferredDescription();
	}

}
