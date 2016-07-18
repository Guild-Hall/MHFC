package mhfc.net.common.quests.descriptions;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.goals.DeathRestrictionQuestGoal;

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
	public DeathRestrictionQuestGoal build() {
		return new DeathRestrictionQuestGoal(getAllowedDeaths());
	}
}
