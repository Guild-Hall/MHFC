package mhfc.net.common.quests.descriptions;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDescription;

public class TimeGoalDescription extends GoalDescription {

	public static final String ID_TIME = "time";

	private int time;

	public TimeGoalDescription(int time) {
		super(MHFCQuestBuildRegistry.GOAL_TIME_TYPE);
		this.time = time;
	}

	public int getTime() {
		return time;
	}
}
