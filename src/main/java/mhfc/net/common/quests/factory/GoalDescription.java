package mhfc.net.common.quests.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mhfc.net.common.core.registry.MHFCRegQuests;

public class GoalDescription {
	protected String goalType;
	protected String[] dependencyIds;
	protected GoalDescription[] dependencies;
	protected Object[] arguments;

	/**
	 * Construct a goal with the given parameters. These parameters are then
	 * used in the factory to generate quest goals. The parameter type and the
	 * parameter should match the implementations in the factory class.
	 * {@link QuestFactory} for more details on the format.
	 * 
	 * @param type
	 * @param dependencyIds
	 * @param arguments
	 */
	public GoalDescription(String type, String[] dependencyIds,
			Object[] arguments) {
		this.goalType = type;
		this.dependencyIds = dependencyIds;
		this.arguments = arguments;
	}

	public GoalDescription(String type, Object[] dependencies,
			Object[] arguments) {
		this.goalType = type;
		this.arguments = arguments;
		List<String> goalStrings = new ArrayList<String>();
		List<GoalDescription> goals = new ArrayList<GoalDescription>();
		for (Object obj : dependencies) {
			if (obj instanceof String) {
				goalStrings.add((String) obj);
			} else if (obj instanceof GoalDescription) {
				goals.add((GoalDescription) obj);
			} else {
				throw new IllegalArgumentException(
						"[MHFC] GoalDescription: Only others and their Ids allowed.");
			}
		}
		if (dependencies.length == 0)
			dependencies = null;
		if (dependencyIds.length == 0)
			dependencyIds = null;
		this.dependencyIds = null;
	}

	public String getGoalType() {
		return goalType;
	}

	public GoalDescription[] getDependencies() {
		if (dependencyIds == null)
			return dependencies;
		List<GoalDescription> descs = new ArrayList<GoalDescription>();
		for (String dependency : dependencyIds) {
			descs.add(MHFCRegQuests.getGoalDescription(dependency));
		}
		if (dependencies != null)
			descs.addAll(Arrays.asList(dependencies));
		return descs.toArray(new GoalDescription[0]);
	}

	public Object[] getArguments() {
		return arguments;
	}
}
