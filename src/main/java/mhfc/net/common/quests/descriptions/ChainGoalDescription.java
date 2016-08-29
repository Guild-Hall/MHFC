package mhfc.net.common.quests.descriptions;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.goals.ChainQuestGoal;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.util.stringview.Viewable;

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
	public IGoalFactory newFactory() {
		return new IGoalFactory() {
			private IGoalFactory firstFactory = getTrueGoal().getReferredDescription().newFactory();
			private IGoalFactory secondFactory = getSuccessorGoal().getReferredDescription().newFactory();

			@Override
			public boolean areAttributesBound() {
				return true;
			}

			@Override
			public IGoalFactory bindAttributes(GroupProperty goalProperties) {
				firstFactory.bindAttributes(goalProperties.newMember("fg", GroupProperty.construct()));
				secondFactory.bindAttributes(goalProperties.newMember("sg", GroupProperty.construct()));

				return this;
			}

			@Override
			public IGoalFactory bindVisualSupplements() {
				firstFactory = firstFactory.bindVisualSupplements();
				secondFactory = secondFactory.bindVisualSupplements();
				return this;
			}

			@Override
			public Viewable buildVisual() {
				Viewable thisGoal = firstFactory.buildVisual();
				Viewable next = secondFactory.buildVisual();
				return thisGoal.appendStatic("\n").concat(next);
			}

			@Override
			public Viewable buildShortStatus() {
				return firstFactory.buildShortStatus();
			}

			@Override
			public QuestGoal build() {
				QuestGoal firstDependency = firstFactory.build();
				QuestGoal secondDependency = secondFactory.build();

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
		};
	}
}
