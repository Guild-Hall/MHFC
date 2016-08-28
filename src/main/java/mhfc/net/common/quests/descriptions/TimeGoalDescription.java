package mhfc.net.common.quests.descriptions;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.goals.TimeQuestGoal;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.quests.properties.IntProperty;
import mhfc.net.common.util.stringview.Viewable;
import mhfc.net.common.util.stringview.Viewables;

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
			public boolean areAttributesBound() {
				return timer != null && baseProperties != null;
			}

			@Override
			public IGoalFactory bindAttributes(GroupProperty goalProperties) {
				timer = goalProperties.newMember("ticks", IntProperty.construct(getTime()));
				baseProperties = goalProperties;
				return this;
			}

			@Override
			public IGoalFactory bindVisualSupplements() {
				baseProperties.newVisualSupplementMethod("ticksToTime", TimeGoalDescription.class, "ticksToTime");
				return this;
			}

			@Override
			public Viewable buildVisual() {
				checkAttributesBound();
				return Viewables.parse("Time remaining: {{ticks | ticksToTime}}", baseProperties);
			}

			@Override
			public Viewable buildShortStatus() {
				checkAttributesBound();
				return Viewables.parse("Timelimit: {{ticks | ticksToTime}}", baseProperties);
			}

			@Override
			public QuestGoal build() {
				checkAttributesBound();
				return new TimeQuestGoal(timer, getTime());
			}
		};
	}

	private static NumberFormat format = new DecimalFormat("00");

	public static String ticksToTime(int ticksLeft) {
		int seconds = (ticksLeft + 19) / 20;
		int minutes = seconds / 60;
		seconds = seconds % 60;
		int hours = minutes / 60;
		minutes = minutes % 60;

		StringBuilder value = new StringBuilder();
		if (hours != 0) {
			value.append(format.format(hours));
			value.append(':');
		}
		value.append(format.format(minutes));
		value.append(':');
		value.append(format.format(seconds));
		return value.toString();
	}
}
