package mhfc.net.common.quests.goals;

import java.util.EnumSet;

import mhfc.net.common.quests.QuestGoalSocket;
import mhfc.net.common.quests.QuestStatus;

public abstract class QuestGoal {
	protected QuestGoalSocket socket;

	public QuestGoal() {
		this(null);
	}
	public QuestGoal(QuestGoalSocket parent) {
		this.socket = parent;
	}

	public QuestGoalSocket getSocket() {
		return socket;
	}
	public void setSocket(QuestGoalSocket socket) {
		this.socket = socket;
	}

	/**
	 * Tests the quest goal if it is fulfilled right now.
	 * 
	 */
	public abstract boolean isFulfilled();

	/**
	 * Tests the quest goal if it is in failed status right now
	 * 
	 */
	public abstract boolean isFailed();

	/**
	 * Resets the quest goal to its beginning
	 */
	public abstract void reset();

	/**
	 * Gets called by the socket when the event is activated. When inactive, a
	 * QuestEvent should not call notifyOfStatus()
	 */
	public abstract void setActive(boolean newActive);

	/**
	 * This method should be called by the socket when it wants to remove it
	 * from its socketed quests. It does not have to be called though, for
	 * example when a reset is done.
	 */
	public void questGoalFinalize() {

	}
	/**
	 * Notifies the overlaying {@link QuestGoalSocket} that our status has
	 * changed.
	 * 
	 * @param newFinished
	 *            Is the quest goal finished now
	 * @param newFailed
	 *            Whether this quest now has the failed status
	 */
	protected void notifyOfStatus(EnumSet<QuestStatus> newStatus) {
		socket.questGoalStatusNotification(this, newStatus);
	}
	protected void notifyOfStatus(boolean newFulfilled, boolean newFailed) {
		EnumSet<QuestStatus> e = EnumSet.<QuestStatus> of(QuestStatus.Failed);
		if (isFulfilled())
			e.add(QuestStatus.Fulfilled);
		if (isFailed())
			e.add(QuestStatus.Failed);
		notifyOfStatus(e);
	}
}
