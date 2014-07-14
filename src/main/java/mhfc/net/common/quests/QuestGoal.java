package mhfc.net.common.quests;

public abstract class QuestGoal {
	protected QuestGoalSocket socket;

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
	 * Notifies the overlaying {@link QuestGoalSocket} that our status has
	 * changed.
	 * 
	 * @param newFinished
	 *            Is the quest goal finished now
	 * @param newFailed
	 *            Whether this quest now has the failed status
	 */
	protected void notifyOfStatus(boolean newFulfilled, boolean newFailed) {
		socket.questGoalStatusNotification(this, newFulfilled, newFailed);
	}
}
