/**
 * 
 */
package mhfc.net.common.quests.goals;

import mhfc.net.common.eventhandler.MHFCDelayedJob;
import mhfc.net.common.eventhandler.MHFCJobHandler;
import mhfc.net.common.quests.QuestGoalSocket;
import mhfc.net.common.quests.QuestRunningInformation.InformationType;

/**
 * 
 */
public class TimeQuestGoal extends QuestGoal implements MHFCDelayedJob {

	protected boolean isFailed = false;
	protected boolean active;
	protected int ticksToFail;
	protected long timeOfLastUpdate;
	protected int initialTicksToFail;

	public TimeQuestGoal(QuestGoalSocket socket, int initialTime) {
		super(socket);
		this.initialTicksToFail = initialTime;
		ticksToFail = initialTime;
		active = false;
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
			timeOfLastUpdate = System.currentTimeMillis();
			active = true;
			notifyOfStatus(isFulfilled(), isFailed());
		} else {
			int delay = MHFCJobHandler.getJobHandler().getDelay(this);
			if (delay > 0)
				ticksToFail = delay;
			MHFCJobHandler.getJobHandler().remove(this);
			active = false;
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

	@Override
	public String modify(InformationType type, String current) {
		// TODO Externalize these strings
		if (type == InformationType.TimeLimit) {
			current += (current.equals("") ? "" : "\n");
			if (active) {
				current += "{time:" + (timeOfLastUpdate + ticksToFail * 33)
						+ "}";
			} else {
				long delta = ticksToFail * 33;
				current += parseTime(delta);
			}
		} else if (type == InformationType.LongStatus) {
			current += (current.equals("") ? "" : "\n");
			if (active) {
				current += "Finish within " + "{time:"
						+ (timeOfLastUpdate + ticksToFail * 33) + "}";
			} else {
				long delta = ticksToFail * 33;
				current += "Finish within " + parseTime(delta) + " Time Limit";
			}
		} else if (type == InformationType.ShortStatus) {
			current += (current.equals("") ? "" : "\n");
			if (active) {
				current += "Finish within " + "{time:"
						+ (timeOfLastUpdate + ticksToFail * 33) + "}";
			}
		}
		if (type == InformationType.TimeLimit)
			System.out.println("Time goal updated the string to " + current);
		return current;
	}

	private String parseTime(long delta) {
		delta /= 1000;
		return "" + (delta >= 3600 ? delta / 3600 + "h " : "")
				+ (delta >= 60 ? (delta % 3600) / 60 + "min " : "")
				+ (delta >= 0 ? delta % 60 : delta) + "s";
	}
}
