package mhfc.net.common.quests.api;

public abstract class GoalDefinition {
	protected String goalType;

	/**
	 * Construct a goal of the given type identificator. In the json file, every goal description has to have at least
	 * the following content:<br>
	 * {@value GoalDefinition#goalType} : {@linkplain String} <br>
	 * More specific types may have additional requirements. See the child classes for more details on their format.
	 */
	public GoalDefinition(String type) {
		this.goalType = type;
	}

	public String getGoalType() {
		return goalType;
	}

	public abstract IGoalFactory newFactory();
}
