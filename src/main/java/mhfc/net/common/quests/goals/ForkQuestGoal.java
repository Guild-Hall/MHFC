package mhfc.net.common.quests.goals;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import mhfc.net.common.quests.QuestGoalSocket;
import mhfc.net.common.quests.QuestStatus;

/**
 * This is the super type for quest goals that do depend on multiple others but
 * in no specific order. The order should not make a difference and any order
 * should be possible. If this is not the case use {@link ChainQuestGoal}
 * instead.
 */

public class ForkQuestGoal extends QuestGoal implements QuestGoalSocket {

	protected List<QuestGoal> requisites;
	protected List<QuestGoal> optional;

	public ForkQuestGoal(QuestGoalSocket parent) {
		super(parent);
		requisites = new LinkedList<QuestGoal>();
		optional = new LinkedList<QuestGoal>();
	}

	/**
	 * Adds a {@link QuestGoal} as the requisite for this QuestGoal
	 */
	public void addRequisite(QuestGoal goal) {
		if (goal == null)
			throw new IllegalArgumentException(
					"ForkQuestGoal: A null goal is not a valid requisite");
		requisites.add(goal);
		notifyOfStatus(isFulfilled(), isFailed());
	}

	/**
	 * Adds a {@link QuestGoal} as an optional one for this QuestGoal
	 */
	public void addOptional(QuestGoal goal) {
		if (goal == null)
			throw new IllegalArgumentException(
					"ForkQuestGoal: A null goal is not a valid requisite");
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
	public void questGoalStatusNotification(QuestGoal goal,
			EnumSet<QuestStatus> newStatus) {
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
