package mhfc.net.common.quests.descriptions;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.QuestFactories;
import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.goals.ChainQuestGoal;
import mhfc.net.common.quests.properties.GroupProperty;

/**
 * Format for a goal description:<br>
 * {@value ChainGoalDescription#ID_GOAL} : {@linkplain String}| {@linkplain GoalDefinition}<br>
 * [{@value ChainGoalDescription#ID_SUCCESSOR} : {@linkplain String}| {@linkplain GoalDefinition}]
 */

public class ChainGoalDescription extends GoalDefinition {

	public static final String ID_GOAL = "goal";
	public static final String ID_SUCCESSOR = "successor";

	private GoalReference trueGoal, successorGoal;

	public ChainGoalDescription(GoalReference trueGoal, GoalReference successorGoal) {
		super(MHFCQuestBuildRegistry.GOAL_CHAIN_TYPE);
		this.trueGoal = trueGoal;
		this.successorGoal = successorGoal;
	}

	public GoalReference getTrueGoal() {
		return trueGoal;
	}

	public GoalReference getSuccessorGoal() {
		return successorGoal;
	}

	@Override
	public QuestGoal build(GroupProperty properties) {
		GoalDefinition truG = getTrueGoal().getReferredDescription(),
				sucG = getSuccessorGoal().getReferredDescription();

		QuestGoal firstDependency, secondDependency;
		if (truG == null) {
			firstDependency = null;
		} else {
			firstDependency = QuestFactories.constructGoal(sucG, properties.newMember("fg", GroupProperty.construct()));
		}
		if (sucG == null) {
			secondDependency = null;
		} else {
			secondDependency = QuestFactories
					.constructGoal(sucG, properties.newMember("sg", GroupProperty.construct()));
		}

		if (firstDependency == null) {
			MHFCMain.logger().warn(
					"A chain goal used an invalid description as its goal. Using the successor goal instead of the chain goal");
			return secondDependency;
		}
		if (secondDependency == null) {
			return firstDependency;
		}

		ChainQuestGoal goal = new ChainQuestGoal(firstDependency, secondDependency);
		firstDependency.setSocket(goal);
		secondDependency.setSocket(goal);
		return goal;
	}

}
