package mhfc.net.common.quests;

public class GoalDescription {
	protected String goalType;
	protected String[] dependencyIds;
	protected Object[] arguments;

	public GoalDescription(String type, Object[] arguments,
			String[] dependencyIds) {
		this.goalType = type;
		this.dependencyIds = dependencyIds;
		this.arguments = arguments;
	}

	public GoalDescription(String type, String[] dependencyIds,
			Object... arguments) {
		this(type, arguments, dependencyIds);
	}

	public String getGoalType() {
		return goalType;
	}

	public String[] getDependencyIds() {
		return dependencyIds;
	}

	public Object[] getArguments() {
		return arguments;
	}
}
