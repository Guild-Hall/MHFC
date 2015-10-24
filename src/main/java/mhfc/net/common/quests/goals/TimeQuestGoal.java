/**
 *
 */
package mhfc.net.common.quests.goals;

import mhfc.net.common.eventhandler.DelayedJob;
import mhfc.net.common.eventhandler.MHFCJobHandler;
import mhfc.net.common.quests.QuestRunningInformation.InformationType;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.api.QuestGoalSocket;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

/**
 *
 */
public class TimeQuestGoal extends QuestGoal implements DelayedJob {
	private long scheduledTime;

	protected boolean isFailed = false;
	protected boolean active;
	protected long ticksToFail;
	protected long timeOfLastUpdate;
	protected int initialTicksToFail;

	public TimeQuestGoal(QuestGoalSocket socket, int initialTime) {
		super(socket);
		this.initialTicksToFail = initialTime;
		ticksToFail = initialTime;
		active = false;
		scheduledTime = 0;
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
		MHFCJobHandler.instance().remove(this);
		isFailed = false;
		ticksToFail = initialTicksToFail;
	}

	@Override
	public void setActive(boolean newActive) {
		if (newActive) {
			if (ticksToFail > 0 && !active) {
				scheduledTime = MHFCJobHandler.instance().insert(this,
						ticksToFail);
			}
			timeOfLastUpdate = System.currentTimeMillis();
			active = true;
			notifyOfStatus(isFulfilled(), isFailed());
		} else {
			long delay = MHFCJobHandler.instance().getDelay(scheduledTime);
			ticksToFail = delay;
			MHFCJobHandler.instance().remove(this);
			active = false;
		}
	}

	@Override
	public void executeJob(Phase phase) {
		if (phase != Phase.END)
			return;
		isFailed = true;
		notifyOfStatus(isFulfilled(), isFailed());
	}

	public int getInitialDelay() {
		return initialTicksToFail;
	}

	@Override
	public void questGoalFinalize() {
		MHFCJobHandler.instance().remove(this);
	}

	@Override
	public String modify(InformationType type, String current) {
		// TODO Externalize and unlocalize these strings
		long ticksToFail = MHFCJobHandler.instance().getDelay(scheduledTime);
		if (ticksToFail < 0)
			ticksToFail = initialTicksToFail;
		switch (type) {
			case TimeLimit :
				current += (current.endsWith("\n") || current.matches("\\s*")
						? ""
						: "\n");
				if (active) {
					current += "{time:" + ticksToFail + "}/ "
							+ parseTimeFromTicks(initialTicksToFail);
				} else {
					current += parseTimeFromTicks(ticksToFail);
				}
				break;
			case LongStatus :
				current += (current.endsWith("\n") || current.matches("\\s*")
						? ""
						: "\n");
				current += "Finish within "
						+ (active ? " {time:" + ticksToFail + "} of " : "")
						+ "a " + parseTimeFromTicks(initialTicksToFail)
						+ "Time Limit";
				break;
			case ShortStatus :
				current += (current.endsWith("\n") || current.matches("\\s*")
						? ""
						: "\n");
				if (active) {
					current += "{time:" + ticksToFail + "} remaining";
				} else {
					current += parseTimeFromTicks(ticksToFail) + "limit";
				}
				break;
			default :
				break;
		}
		return current;
	}

	private static String parseTimeFromTicks(long delta) {
		delta /= MHFCJobHandler.ticksPerSecond;
		return "" + (delta / 3600 > 0 ? delta / 3600 + "h " : "")
				+ ((delta % 3600) / 60 > 0 ? (delta % 3600) / 60 + "min " : "")
				+ (delta % 60 > 0 ? delta % 60 + "s " : "");
	}
}
