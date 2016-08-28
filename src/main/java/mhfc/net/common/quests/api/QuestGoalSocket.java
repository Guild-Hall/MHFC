package mhfc.net.common.quests.api;

import java.util.EnumSet;

import mhfc.net.common.quests.Mission;
import mhfc.net.common.quests.QuestStatus;

/**
 * An interface for classes that do have a quest goal. This is necessary as they should be notified whenever the
 * QuestGoal status changes.
 *
 */
public interface QuestGoalSocket {

	/**
	 * Called whenever a goal (most likely one that is socketed here although this can not be guaranteed and some
	 * sockets may even want to take action in exactly these cases) wants to notify this socket of its status. This does
	 * not imply that the status has changed but this might have happened.
	 *
	 * @param caller
	 *            The {@link QuestGoal} that this status notification concerns
	 * @param newFinished
	 *            The new finished status
	 * @param newFailed
	 *            The new failed status
	 */
	public void questGoalStatusNotification(QuestGoal caller, EnumSet<QuestStatus> newStatus);

	/**
	 * Resets a socket and all {@link QuestGoal} instances in it to its original status. For a quest this can mean
	 * rebuilding the quest line, for a single quest goal it can be a simple reset.
	 */
	public void reset();

	/**
	 * Gets the top node, that is the actual quest that the goal belongs to.
	 *
	 * @return
	 */
	public Mission getMission();
}
