package mhfc.net.common.quests.descriptions;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.goals.TimeQuestGoal;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.quests.properties.IntProperty;
import mhfc.net.common.util.stringview.DynamicString;
import mhfc.net.common.util.stringview.Viewable;

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
	public IGoalFactory newFactory() {
		return new IGoalFactory() {
			private IntProperty timer;
			private GroupProperty baseProperties;

			@Override
			public IGoalFactory bindAttributes(GroupProperty goalProperties) {
				timer = goalProperties.newMember("ticks", IntProperty.construct(getTime()));
				return this;
			}

			@Override
			public Viewable buildVisual() {
				return new DynamicString().append("{{ticks}} remaining", baseProperties);
			}

			@Override
			public QuestGoal build() {
				return new TimeQuestGoal(timer, getTime());
			}
		};
	}
}
