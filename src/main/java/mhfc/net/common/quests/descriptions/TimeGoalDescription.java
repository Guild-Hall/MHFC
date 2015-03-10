package mhfc.net.common.quests.descriptions;

import mhfc.net.common.core.registry.MHFCQuestsRegistry;
import mhfc.net.common.quests.api.GoalDescription;

public class TimeGoalDescription extends GoalDescription {

	private int time;

	public TimeGoalDescription(int time) {
		super(MHFCQuestsRegistry.GOAL_TIME_TYPE);
		this.time = time;
	}

	public int getTime() {
		return time;
	}
}
