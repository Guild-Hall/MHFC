package mhfc.net.common.quests;
/**
 *
 * This is the super type for quest goals that do depend on multiple others
 * where you have to fulfill them in a specific order.
 */

public class ChainQuestGoal extends QuestGoal implements QuestGoalSocket {

	protected QuestGoal thisGoal;
	protected QuestGoal next;

	/**
	 * Creates a new quest chain with a goal that has to be completed now and a
	 * quest chain that is next. The current goal should not be null otherwise
	 * an {@link IllegalArgumentException} is thrown.
	 * 
	 * @param socket
	 *            See {@link QuestGoal}
	 * @param thisGoal
	 *            The goal of this step of the chain
	 * @param next
	 *            The next chain element, null if the chain is over
	 */
	public ChainQuestGoal(QuestGoalSocket socket, QuestGoal thisGoal,
			QuestGoal next) {
		super(socket);
		if (thisGoal == null)
			throw new IllegalArgumentException(
					"ChainQuestGoal: The goal of this step may not be null");
		this.thisGoal = thisGoal;
		this.next = next;
	}

	@Override
	public boolean isFulfilled() {
		if (thisGoal == null)
			throw new IllegalStateException(
					"ChainQuestGoal: Not fulfilled because of a null goal");
		if (next == null && thisGoal.isFulfilled())
			return true;
		return false;
	}

	@Override
	public boolean isFailed() {
		if (thisGoal == null)
			throw new IllegalStateException(
					"ChainQuestGoal: Failed because of a null goal");
		return thisGoal.isFailed();
	}
	@Override
	public void questGoalStatusNotification(QuestGoal caller,
			boolean newFinished, boolean newFailed) {
		if (caller == thisGoal) {
			if (newFinished) {
				onFinished(newFinished);
			}
			if (newFailed) {
				notifyOfStatus(newFinished, newFailed);
			}
		} else {
			onUnknownStatusNotification(caller, newFinished, newFailed);
		}
	}
	/**
	 * This gets called whenever this QuestGoal has notified us that it is
	 * finished.
	 */
	protected void onFinished(boolean newFailed) {
		thisGoal = next;
		next = null;
		if (thisGoal instanceof ChainQuestGoal) {
			next = ((ChainQuestGoal) thisGoal).getNext();
		}
		if (next == null)
			notifyOfStatus(true, newFailed);
	}
	/**
	 * This gets called whenever this QuestGoal has notified us that it is
	 * failed.
	 */
	protected void onFailed(boolean newFinished) {
		notifyOfStatus(newFinished, true);
	}
	/**
	 * This gets called whenever some {@link QuestGoal} has notified us that is
	 * not the next goal.
	 */
	protected void onUnknownStatusNotification(QuestGoal caller,
			boolean newFinished, boolean newFailed) {
		throw new IllegalArgumentException(
				"ChainQuestGoal: A QuestGoal that is not our next goal should not notify us");
	}
	public QuestGoal getNext() {
		return next;
	}

	@Override
	public void reset() {
		thisGoal.reset();
		next.reset();
		notifyOfStatus(isFulfilled(), isFailed());
	}

}
