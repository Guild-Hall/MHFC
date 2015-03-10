package mhfc.net.common.quests.api;


public class GoalDescription {
	protected String goalType;

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
	public GoalDescription(String type) {
		this.goalType = type;
	}

	public String getGoalType() {
		return goalType;
	}
}
