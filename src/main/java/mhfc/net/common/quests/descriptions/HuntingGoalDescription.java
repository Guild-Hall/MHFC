package mhfc.net.common.quests.descriptions;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDescription;

public class HuntingGoalDescription extends GoalDescription {

	public static final String ID_HUNTED_TYPE = "target";
	public static final String ID_AMOUNT = "amount";

	private int amount;
	private Class<?> huntedClass;

	public HuntingGoalDescription(Class<?> huntedClass, int amount) {
		super(MHFCQuestBuildRegistry.GOAL_HUNTING_TYPE);
		this.huntedClass = huntedClass;
		this.amount = amount;
	}

	public Class<?> getHuntedClass() {
		return huntedClass;
	}

	public int getAmount() {
		return amount;
	}
}
