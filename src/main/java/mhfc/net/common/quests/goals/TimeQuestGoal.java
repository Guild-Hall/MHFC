/**
 *
 */
package mhfc.net.common.quests.goals;

import java.util.Objects;

import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.RunContext;

import mhfc.net.common.eventhandler.DelayedJob;
import mhfc.net.common.eventhandler.MHFCTickHandler;
import mhfc.net.common.eventhandler.TickPhase;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.properties.IntProperty;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

/**
 *
 */
public class TimeQuestGoal extends QuestGoal implements DelayedJob {
	private class Timer implements Operation {
		private int initialTickTime;
		private IntProperty tickTime;
		private boolean isCancelled;

		public Timer(IntProperty timer, int ticks) {
			if (ticks < 0) {
				throw new IllegalArgumentException("ticks must be greater than 0");
			}
			this.initialTickTime = ticks;
			this.tickTime = Objects.requireNonNull(timer);
			isCancelled = false;
		}

		public void reset() {
			tickTime.set(initialTickTime);
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
			tickTime.decr();
			if (getRemaining() > 0) {
				return this;
			}
			tickTime.set(0); // Possible underflow from cancelling etc...
			TimeQuestGoal.this.isFailed = true;
			TimeQuestGoal.this.notifyOfStatus(isFulfilled(), isFailed());
			return null;
		}

		public int getRemaining() {
			return tickTime.get();
		}
	}

	protected boolean isFailed = false;
	protected boolean active;
	protected Timer timer;

	public TimeQuestGoal(IntProperty timer, int initialTime) {
		super(null);
		active = false;
		this.timer = new Timer(timer, initialTime);
		MHFCTickHandler.instance.registerOperation(TickPhase.SERVER_POST, this.timer);
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
}
