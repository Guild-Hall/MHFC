package mhfc.net.common.quests.descriptions;

import mhfc.net.common.core.registry.MHFCQuestsRegistry;
import mhfc.net.common.quests.api.GoalDescription;

public class DeathRestrictionDescription extends GoalDescription {

	public static final String ID_LIFES = "lives";

	private int allowedDeaths;

	public DeathRestrictionDescription(int allowedDeaths) {
		super(MHFCQuestsRegistry.GOAL_DEATH_RESTRICTION_TYPE);
		this.allowedDeaths = allowedDeaths;
	}

	public int getAllowedDeaths() {
		return allowedDeaths;
	}
}
