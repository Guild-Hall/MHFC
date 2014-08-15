/**
 * 
 */
package mhfc.heltrato.common.quests.goals;

import mhfc.heltrato.common.eventhandler.MHFCDelayedJob;
import mhfc.heltrato.common.eventhandler.MHFCJobHandler;
import mhfc.heltrato.common.quests.QuestGoalSocket;

/**
 * 
 */
public class TimeQuestGoal extends QuestGoal implements MHFCDelayedJob {

	protected boolean isFailed = false;
	protected int ticksToFail;
	protected int initialTicksToFail;

	public TimeQuestGoal(QuestGoalSocket socket, int initialTime) {
		super(socket);
		this.initialTicksToFail = initialTime;
		ticksToFail = initialTime;
	}

	public TimeQuestGoal(int initialTime) {
		this(null, initialTime);
	}

	@Override
	public boolean isFulfilled() {
		return !isFailed;
	}

	@Override
	public boolean isFailed() {
		return isFailed;
	}

	@Override
	public void reset() {
		MHFCJobHandler.getJobHandler().remove(this);
		isFailed = false;
		ticksToFail = initialTicksToFail;
	}

	@Override
	public void setActive(boolean newActive) {
		if (newActive) {
			if (ticksToFail > 0)
				MHFCJobHandler.getJobHandler().insert(this, ticksToFail);
			notifyOfStatus(isFulfilled(), isFailed());
		} else {
			int delay = MHFCJobHandler.getJobHandler().getDelay(this);
			if (delay > 0)
				ticksToFail = delay;
			MHFCJobHandler.getJobHandler().remove(this);
		}
	}

	@Override
	public void executeJob() {
		isFailed = true;
		notifyOfStatus(isFulfilled(), isFailed());
	}

	@Override
	public int getInitialDelay() {
		return initialTicksToFail;
	}

}
