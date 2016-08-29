package mhfc.net.common.quests.goals;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.quests.QuestStatus;
import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.api.QuestGoalSocket;

/**
 * This is the super type for quest goals that do depend on multiple others but in no specific order. The order should
 * not make a difference and any order should be possible. If this is not the case use {@link ChainQuestGoal} instead.
 * The additions to the goal format are as following:<br>
 * {@value ForkQuestDescription#ID_REQUIRED} : ({@linkplain String}| {@linkplain GoalDefinition})[]<br>
 * [{@value ForkQuestDesciption#ID_OPTIONAL} : ({@linkplain String}| {@linkplain GoalDefinition})[]]
 */

public class ForkQuestGoal extends QuestGoal implements QuestGoalSocket {

	protected List<QuestGoal> requisites;
	protected List<QuestGoal> optional;

	public ForkQuestGoal(QuestGoalSocket parent) {
		super(parent);
		requisites = new LinkedList<>();
		optional = new LinkedList<>();
	}

	/**
	 * Adds a {@link QuestGoal} as the requisite for this QuestGoal
	 */
	public void addRequisite(QuestGoal goal) {
		if (goal == null) {
			MHFCMain.logger().warn("ForkQuestGoal: Ignored requisite, a null goal is not valid");
			return;
		}
		requisites.add(goal);
		goal.setSocket(this);
		notifyOfStatus(isFulfilled(), isFailed());
	}

	/**
	 * Adds a {@link QuestGoal} as an optional one for this QuestGoal
	 */
	public void addOptional(QuestGoal goal) {
		if (goal == null) {
			MHFCMain.logger().warn("ForkQuestGoal: Ignored optional, a null goal is not valid");
			return;
		}
		goal.setSocket(this);
		optional.add(goal);
	}

	@Override
	public boolean isFulfilled() {
		boolean fulfilled = true;
		for (QuestGoal g : requisites) {
			fulfilled &= g.isFulfilled();
		}
		return fulfilled;
	}

	@Override
	public boolean isFailed() {
		boolean failed = false;
		for (QuestGoal g : requisites) {
			failed |= g.isFailed();
		}
		return failed;
	}

	@Override
	public void questGoalStatusNotification(QuestGoal goal, EnumSet<QuestStatus> newStatus) {
		notifyOfStatus(isFulfilled(), isFailed());
	}

	@Override
	public void questGoalFinalize() {
		for (QuestGoal g : requisites) {
			g.questGoalFinalize();
		}
		for (QuestGoal g : optional) {
			g.questGoalFinalize();
		}
	}

	@Override
	public void reset() {
		for (QuestGoal g : requisites) {
			g.reset();
		}
		for (QuestGoal g : optional) {
			g.reset();
		}
		notifyOfStatus(isFulfilled(), isFailed());
	}

	@Override
	public void setActive(boolean newActive) {
		for (QuestGoal g : requisites) {
			g.setActive(newActive);
		}
		for (QuestGoal g : optional) {
			g.setActive(newActive);
		}
	}
}
