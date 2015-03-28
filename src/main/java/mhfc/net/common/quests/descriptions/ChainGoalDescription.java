package mhfc.net.common.quests.descriptions;

import mhfc.net.common.core.registry.MHFCQuestsRegistry;
import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.GoalReference;

/**
 * Format for a goal description:<br>
 * {@value ChainGoalDescription#ID_GOAL} : {@linkplain String}|
 * {@linkplain GoalDescription}<br>
 * [{@value ChainGoalDescription#ID_SUCCESSOR} : {@linkplain String}|
 * {@linkplain GoalDescription}]
 */

public class ChainGoalDescription extends GoalDescription {

	public static final String ID_GOAL = "goal";
	public static final String ID_SUCCESSOR = "successor";

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
