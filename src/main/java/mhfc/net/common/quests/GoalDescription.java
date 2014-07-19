package mhfc.net.common.quests;

public class GoalDescription {
	protected String goalType;
	protected String[] dependencyIds;
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
