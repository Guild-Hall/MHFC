package mhfc.net.common.quests.goals;

import java.util.EnumSet;
import java.util.Objects;

import mhfc.net.common.quests.QuestStatus;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.api.QuestGoalSocket;

/**
 *
 * This is the super type for quest goals that do depend on multiple others where you have to fulfill them in a specific
 * order.
 */

public class ChainQuestGoal extends QuestGoal implements QuestGoalSocket {

	protected QuestGoal thisGoal;
	protected QuestGoal next;
	private boolean finalFailed;

	/**
	 * Creates a new quest chain with a goal that has to be completed now and a quest chain that is next. The current
	 * goal should not be null otherwise an {@link IllegalArgumentException} is thrown.
	 *
	 * @param socket
	 *            See {@link QuestGoal}
	 * @param thisGoal
	 *            The goal of this step of the chain
	 * @param next
	 *            The next chain element, null if the chain is over
	 */
	public ChainQuestGoal(QuestGoalSocket socket, QuestGoal thisGoal, QuestGoal next) {
		super(socket);
		Objects.requireNonNull(thisGoal, "The goal of this step may not be null");
		Objects.requireNonNull(next, "The following goal may not be null");
		thisGoal.setSocket(this);
		this.thisGoal = thisGoal;
		this.next = next;
		finalFailed = false;
	}

	public ChainQuestGoal(QuestGoal thisGoal, QuestGoal next) {
		this(null, thisGoal, next);
	}

	@Override
	public boolean isFulfilled() {
		if (thisGoal == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isFailed() {
		if (thisGoal == null) {
			return finalFailed;
		}
		return thisGoal.isFailed();
	}

	@Override
	public void questGoalStatusNotification(QuestGoal caller, EnumSet<QuestStatus> newStatus) {
		if (caller == thisGoal) {
			if (newStatus.contains(QuestStatus.Fulfilled)) {
				advanceGoal(newStatus.contains(QuestStatus.Failed));
			}
			if (newStatus.contains(QuestStatus.Failed)) {
				onFailed(newStatus.contains(QuestStatus.Fulfilled));
			}
		} else if (caller == next) {
			onNextNotified(newStatus);
		} else {
			onUnknownStatusNotification(caller, newStatus);
		}
	}

	@Override
	public void questGoalFinalize() {
		if (thisGoal != null) {
			thisGoal.questGoalFinalize();
		}
		if (next != null) {
			next.questGoalFinalize();
		}
	}

	/**
	 * This gets called whenever this QuestGoal has notified us that it is finished.
	 */
	protected void advanceGoal(boolean newFailed) {
		thisGoal = next;
		if (thisGoal == null) {
			notifyOfStatus(EnumSet.<QuestStatus>of(QuestStatus.Fulfilled));
			return;
		}
		thisGoal.setSocket(this);
		thisGoal.reset();
		thisGoal.setActive(true);
		next = null;
		if (thisGoal instanceof ChainQuestGoal) {
			next = ((ChainQuestGoal) thisGoal).getNext();
			thisGoal = ((ChainQuestGoal) thisGoal).thisGoal;
		}
		if (next == null) {
			finalFailed = newFailed;
			notifyOfStatus(true, newFailed);
		} else {
			next.setSocket(this);
		}

	}

	/**
	 * This gets called whenever this QuestGoal has notified us that it is failed.
	 */
	protected void onFailed(boolean newFulfilled) {
		EnumSet<QuestStatus> e = EnumSet.<QuestStatus>of(QuestStatus.Failed);
		if (newFulfilled) {
			e.add(QuestStatus.Fulfilled);
		}
		notifyOfStatus(e);
	}

	protected void onNextNotified(EnumSet<QuestStatus> newStatus) {

	}

	/**
	 * This gets called whenever some {@link QuestGoal} has notified us that is not the next goal.
	 */
	protected void onUnknownStatusNotification(QuestGoal caller, EnumSet<QuestStatus> newStatus) {
		throw new IllegalArgumentException(
				"ChainQuestGoal: A QuestGoal that is not our next goal should not notify us");
	}

	public QuestGoal getNext() {
		return next;
	}

	public void setNext(QuestGoal goal) {
		this.next = goal;
	}

	@Override
	public void reset() {
		thisGoal.reset();
		next.reset();
		EnumSet<QuestStatus> e = EnumSet.<QuestStatus>of(QuestStatus.Failed);
		if (isFulfilled()) {
			e.add(QuestStatus.Fulfilled);
		}
		if (isFailed()) {
			e.add(QuestStatus.Failed);
		}
		notifyOfStatus(e);
	}

	@Override
	public void setActive(boolean newActive) {
		if (thisGoal != null) {
			thisGoal.setActive(newActive);
		}
	}
}
