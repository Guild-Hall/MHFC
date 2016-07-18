package mhfc.net.common.quests.descriptions;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.goals.HuntingQuestGoal;
import net.minecraft.entity.Entity;

public class HuntingGoalDescription extends GoalDefinition {

	public static final String ID_HUNTED_TYPE = "target";
	public static final String ID_AMOUNT = "amount";

	private int amount;
	private Class<? extends Entity> huntedClass;

	public HuntingGoalDescription(Class<? extends Entity> huntedClass, int amount) {
		super(MHFCQuestBuildRegistry.GOAL_HUNTING_TYPE);
		this.huntedClass = huntedClass;
		this.amount = amount;
	}

	public Class<? extends Entity> getHuntedClass() {
		return huntedClass;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public HuntingQuestGoal build() {
		return new HuntingQuestGoal(null, getHuntedClass(), getAmount());
	}
}
