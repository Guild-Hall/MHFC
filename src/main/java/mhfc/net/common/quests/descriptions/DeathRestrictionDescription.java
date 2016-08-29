package mhfc.net.common.quests.descriptions;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.goals.DeathRestrictionQuestGoal;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.quests.properties.IntProperty;
import mhfc.net.common.util.stringview.Viewable;
import mhfc.net.common.util.stringview.Viewables;

public class DeathRestrictionDescription extends GoalDefinition {

	public static final String ID_LIVES = "lives";

	private int allowedDeaths;

	public DeathRestrictionDescription(int allowedDeaths) {
		super(MHFCQuestBuildRegistry.GOAL_DEATH_RESTRICTION_TYPE);
		this.allowedDeaths = allowedDeaths;
	}

	public int getAllowedDeaths() {
		return allowedDeaths;
	}

	@Override
	public IGoalFactory newFactory() {
		return new IGoalFactory() {
			protected IntProperty maxDeaths;
			protected IntProperty currentDeaths;
			protected GroupProperty baseGroup;

			@Override
			public boolean areAttributesBound() {
				return maxDeaths != null && currentDeaths != null && baseGroup != null;
			}

			@Override
			public IGoalFactory bindAttributes(GroupProperty goalProperties) {
				maxDeaths = goalProperties.newMember("maxDeaths", IntProperty.construct(allowedDeaths));
				currentDeaths = goalProperties.newMember("currDeaths", IntProperty.construct(0));
				baseGroup = goalProperties;
				return this;
			}

			@Override
			public Viewable buildVisual() {
				checkAttributesBound();
				return Viewables.parse("+{{maxDeaths - currDeaths}} lives left", baseGroup);
			}

			@Override
			public Viewable buildShortStatus() {
				checkAttributesBound();
				return Viewables.parse("+{{maxDeaths - currDeaths}} lives", baseGroup);
			}

			@Override
			public QuestGoal build() {
				checkAttributesBound();
				return new DeathRestrictionQuestGoal(maxDeaths, currentDeaths);
			}
		};
	}
}
