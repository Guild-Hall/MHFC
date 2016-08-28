package mhfc.net.common.quests.descriptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.goals.ForkQuestGoal;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.util.stringview.JoinedView;
import mhfc.net.common.util.stringview.Viewable;

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
		if (required == null) {
			return Collections.emptyList();
		}
		return Arrays.asList(required);
	}

	public List<GoalReference> getOptional() {
		if (optional == null) {
			return Collections.emptyList();
		}
		return Arrays.asList(optional);
	}

	@Override
	public IGoalFactory newFactory() {
		return new IGoalFactory() {
			private List<IGoalFactory> requiredFactories = new ArrayList<>(getRequired().size());
			private List<IGoalFactory> optionalFactories = new ArrayList<>(getOptional().size());

			@Override
			public boolean areAttributesBound() {
				return true;
			}

			@Override
			public IGoalFactory bindAttributes(GroupProperty goalProperties) {
				int i = 0;
				for (GoalReference req : getRequired()) {
					GroupProperty reqProps = goalProperties.newMember("R" + i++, GroupProperty.construct());
					requiredFactories.add(req.getReferredDescription().newFactory().bindAttributes(reqProps));
				}
				i = 0;
				for (GoalReference opt : getOptional()) {
					GroupProperty reqProps = goalProperties.newMember("O" + i++, GroupProperty.construct());
					optionalFactories.add(opt.getReferredDescription().newFactory().bindAttributes(reqProps));
				}
				return this;
			}

			@Override
			public IGoalFactory bindVisualSupplements() {
				for (IGoalFactory reqFactory : requiredFactories) {
					reqFactory.bindVisualSupplements();
				}
				for (IGoalFactory optFactory : optionalFactories) {
					optFactory.bindVisualSupplements();
				}
				return this;
			}

			@Override
			public Viewable buildVisual() {
				JoinedView requisitesSummary = JoinedView.on("\n");
				JoinedView optionalSummary = JoinedView.on("\n");
				for (IGoalFactory reqFactory : requiredFactories) {
					requisitesSummary = requisitesSummary.concat(reqFactory.buildVisual());
				}
				for (IGoalFactory optFactory : optionalFactories) {
					optionalSummary = optionalSummary.concat(optFactory.buildVisual());
				}
				JoinedView goalSummary = JoinedView.on("\n").concat(requisitesSummary).concat(optionalSummary);
				return goalSummary;
			}

			@Override
			public Viewable buildShortStatus() {
				JoinedView requisitesSummary = JoinedView.on("\n");
				int maxGoals = 3;
				for (IGoalFactory reqFactory : requiredFactories) {
					requisitesSummary = requisitesSummary.concat(reqFactory.buildShortStatus());
					if (--maxGoals == 0) {
						break;
					}
				}
				return requisitesSummary;
			}

			@Override
			public QuestGoal build() {
				ForkQuestGoal fork = new ForkQuestGoal(null);
				for (IGoalFactory reqFactory : requiredFactories) {
					fork.addRequisite(reqFactory.build());
				}
				for (IGoalFactory optFactory : optionalFactories) {
					fork.addRequisite(optFactory.build());
				}
				return fork;
			}
		};
	}
}
