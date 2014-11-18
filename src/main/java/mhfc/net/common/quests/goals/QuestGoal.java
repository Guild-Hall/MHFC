package mhfc.net.common.quests.goals;

import java.util.EnumSet;

import mhfc.net.common.quests.GeneralQuest;
import mhfc.net.common.quests.QuestGoalSocket;
import mhfc.net.common.quests.QuestRunningInformation.InformationType;
import mhfc.net.common.quests.QuestStatus;

/**
 * QuestGoal helps making the programming for quests easier since it offers
 * several methods that make it possible to deduce if a goal has been met or is
 * failed. This allows one goal to depend on others as it is implemented for
 * example in {@link ChainQuestGoal} or {@link ForkQuestGoal}.
 * 
 * Every goal has a socket that the goal notifies about its status whenever it
 * feels necessary but it should only do so when it has changed.
 * 
 * A QuestGoal can be activated and deactivated. On creation, a QuestGoal should
 * be inactive, and when deactivated it should not post any notifications to its
 * socket. If it does nevertheless, the socket is free to throw an exception.
 */
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

	public GeneralQuest getQuest() {
		if (socket == null)
			return null;
		return socket.getQuest();
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
		if (socket == null)
			return;
		socket.questGoalStatusNotification(this, newStatus);
	}

	protected void notifyOfStatus(boolean newFulfilled, boolean newFailed) {
		EnumSet<QuestStatus> e = EnumSet.noneOf(QuestStatus.class);
		if (newFulfilled)
			e.add(QuestStatus.Fulfilled);
		if (newFailed)
			e.add(QuestStatus.Failed);
		notifyOfStatus(e);
	}

	/**
	 * Modifies the settings of the quest information
	 */
	public abstract String modify(InformationType type, String current);
}
