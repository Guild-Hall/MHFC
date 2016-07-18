/**
 *
 */
package mhfc.net.common.quests.goals;

import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.RunContext;

import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import mhfc.net.client.quests.QuestRunningInformation.InformationType;
import mhfc.net.common.eventhandler.DelayedJob;
import mhfc.net.common.eventhandler.MHFCTickHandler;
import mhfc.net.common.eventhandler.TickPhase;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.api.QuestGoalSocket;

/**
 *
 */
public class TimeQuestGoal extends QuestGoal implements DelayedJob {
	private class Timer implements Operation {
		private int tickTime, initialTickTime;
		private boolean isCancelled;

		public Timer(int ticks) {
			if (ticks < 0) {
				throw new IllegalArgumentException("ticks must be greater than 0");
			}
			this.initialTickTime = this.tickTime = ticks;
			isCancelled = false;
		}

		public void reset() {
			tickTime = initialTickTime;
		}

		@Override
		public void cancel() {
			isCancelled = true;
		}

		@Override
		public Operation resume(RunContext run) throws WorldEditException {
			if (isCancelled) {
				return null;
			}
			if (!TimeQuestGoal.this.active) {
				return this;
			}
			tickTime--;
			if (tickTime > 0) {
				return this;
			}
			tickTime = 0; // Possible underflow from cancelling etc...
			TimeQuestGoal.this.isFailed = true;
			TimeQuestGoal.this.notifyOfStatus(isFulfilled(), isFailed());
			return null;
		}

		public int getRemaining() {
			return tickTime;
		}

		public int getDuration() {
			return initialTickTime;
		}
	}

	protected boolean isFailed = false;
	protected boolean active;
	protected Timer timer;

	public TimeQuestGoal(QuestGoalSocket socket, int initialTime) {
		super(socket);
		active = false;
		this.timer = new Timer(initialTime);
		MHFCTickHandler.instance.registerOperation(TickPhase.SERVER_POST, this.timer);
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
		isFailed = false;
		this.timer.reset();
	}

	@Override
	public void setActive(boolean newActive) {
		if (newActive) {
			active = true;
			notifyOfStatus(isFulfilled(), isFailed());
		} else {
			active = false;
		}
	}

	@Override
	public void executeJob(Phase phase) {
		if (phase != Phase.END) {
			return;
		}
		isFailed = true;
		notifyOfStatus(isFulfilled(), isFailed());
	}

	@Override
	public void questGoalFinalize() {
		setActive(false);
		timer.cancel();
	}

	@Override
	public String modify(InformationType type, String current) {
		// TODO Externalize and unlocalize these strings
		int ticksToFail = Math.max(0, timer.getRemaining());
		int totalTime = timer.getDuration();
		switch (type) {
		case TimeLimit:
			current += (current.endsWith("\n") || current.matches("\\s*") ? "" : "\n");
			if (active) {
				current += "{time:" + ticksToFail + "}/ " + "{time:" + totalTime + "}";
			} else {
				current += "{time:" + totalTime + "}";
			}
			break;
		case LongStatus:
			current += (current.endsWith("\n") || current.matches("\\s*") ? "" : "\n");
			current += "Finish within " + (active ? "{time:" + ticksToFail + "} of " : "") + "a " + "{time:" + totalTime
					+ "}" + " Time Limit";
			break;
		case ShortStatus:
			current += (current.endsWith("\n") || current.matches("\\s*") ? "" : "\n");
			if (active) {
				current += "{time:" + ticksToFail + "} remaining";
			} else {
				current += "{time:" + totalTime + "}" + "limit";
			}
			break;
		default:
			break;
		}
		return current;
	}
}
