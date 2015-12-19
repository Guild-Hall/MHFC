package mhfc.net.common.quests.descriptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.QuestFactory;
import mhfc.net.common.quests.goals.ForkQuestGoal;
import scala.actors.threadpool.Arrays;

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

	@SuppressWarnings("unchecked")
	public List<GoalReference> getRequired() {
		List<GoalReference> list = new ArrayList<GoalReference>();
		if (required == null)
			return list;
		list.addAll(Arrays.asList(required));
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<GoalReference> getOptional() {
		List<GoalReference> list = new ArrayList<GoalReference>();
		if (optional == null)
			return list;
		list.addAll(Arrays.asList(optional));
		return list;
	}

	@Override
	public ForkQuestGoal build() {
		Stream<GoalDescription> required = getRequired().stream().map(
			GoalReference::getReferredDescription);
		Stream<GoalDescription> optional = getOptional().stream().map(
			GoalReference::getReferredDescription);
		ForkQuestGoal fork = new ForkQuestGoal(null);

		required.map(QuestFactory::constructGoal).filter(Objects::nonNull)
			.forEach(fork::addRequisite);

		optional.map(QuestFactory::constructGoal).filter(Objects::nonNull)
			.forEach(fork::addOptional);

		return fork;
	}

}
