package mhfc.net.common.quests.descriptions;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.QuestFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.goals.ChainQuestGoal;

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
		super(MHFCQuestBuildRegistry.GOAL_CHAIN_TYPE);
		this.trueGoal = trueGoal;
		this.successorGoal = successorGoal;
	}

	public GoalDescription getTrueGoal() {
		return trueGoal.getReferredDescription();
	}

	public GoalDescription getSuccessorGoal() {
		return successorGoal.getReferredDescription();
	}

	@Override
	public QuestGoal build() {
		GoalDescription truG = getTrueGoal(), sucG = getSuccessorGoal();

		QuestGoal dep1, dep2;
		if (sucG == null) {
			dep2 = null;
		} else
			dep2 = QuestFactory.constructGoal(sucG);
		boolean trueGoalNull = false;
		if (truG == null) {
			trueGoalNull = true;
			dep1 = null;
		} else
			dep1 = QuestFactory.constructGoal(truG);
		trueGoalNull |= dep1 == null;

		if (trueGoalNull) {
			MHFCMain.logger
				.warn("A chain goal used an invalid description as its goal. Using the successor goal instead of the chain goal");
			return dep2;
		}

		ChainQuestGoal goal = new ChainQuestGoal(dep1, dep2);
		dep1.setSocket(goal);
		if (dep2 != null)
			dep2.setSocket(goal);
		return goal;
	}

}
