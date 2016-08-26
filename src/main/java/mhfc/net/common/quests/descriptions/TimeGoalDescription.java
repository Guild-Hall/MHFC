package mhfc.net.common.quests.descriptions;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.goals.TimeQuestGoal;
import mhfc.net.common.quests.properties.GroupProperty;

public class TimeGoalDescription extends GoalDefinition {

	public static final String ID_TIME = "time";

	private int time;

	public TimeGoalDescription(int time) {
		super(MHFCQuestBuildRegistry.GOAL_TIME_TYPE);
		this.time = time;
	}

	public int getTime() {
		return time;
	}

	@Override
	public TimeQuestGoal build(GroupProperty properties) {
		return new TimeQuestGoal(properties, getTime());
	}
}
