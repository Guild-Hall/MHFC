package mhfc.net.common.eventhandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class MHFCJobHandler {
	private static MHFCJobHandler instance;
	static {
		instance = new MHFCJobHandler();
		FMLCommonHandler.instance().bus().register(instance);
	}

	private abstract class JobListElement {
		public abstract JobListElement insert(MHFCDelayedJob job, int delay);

		public abstract JobListElement tick();

		public abstract int getDelay(MHFCDelayedJob job);

		public abstract JobListElement remove(MHFCDelayedJob job);

		public abstract boolean containsJob(MHFCDelayedJob job);
	}

	private class DelayedJob extends JobListElement {

		private List<MHFCDelayedJob> jobs;
		private JobListElement following;
		private int ticksToExecution;

		public DelayedJob(MHFCDelayedJob initialJob, int delay) {
			if (initialJob == null)
				throw new IllegalArgumentException(
						"Initial job may not be null");
			jobs = new ArrayList<MHFCDelayedJob>();
			ticksToExecution = delay;
			jobs.add(initialJob);
		}

		@Override
		public JobListElement insert(MHFCDelayedJob job, int delay) {
			if (delay > ticksToExecution) {
				following = following.insert(job, delay - ticksToExecution);
				return this;
			} else if (delay == ticksToExecution) {
				jobs.add(job);
				return this;
			} else {
				ticksToExecution -= delay;
				return new DelayedJob(job, delay);
			}
		}

		@Override
		public JobListElement tick() {
			--ticksToExecution;
			if (ticksToExecution <= 0) {
				Iterator<MHFCDelayedJob> iter = jobs.iterator();
				while (iter.hasNext()) {
					iter.next().executeJob();
				}
				return following.tick();
			}
			return this;
		}

		@Override
		public int getDelay(MHFCDelayedJob job) {
			if (jobs.contains(job))
				return ticksToExecution;
			else
				return following.getDelay(job) < 0 ? -1 : ticksToExecution
						+ following.getDelay(job);
		}

		@Override
		public JobListElement remove(MHFCDelayedJob job) {
			jobs.remove(job);
			if (jobs.isEmpty())
				return following;
			else
				return this;
		}

		@Override
		public boolean containsJob(MHFCDelayedJob job) {
			return jobs.contains(job) || following.containsJob(job);
		}
	}

	private class JobListEnd extends JobListElement {

		@Override
		public JobListElement insert(MHFCDelayedJob job, int delay) {
			return new DelayedJob(job, delay);
		}

		@Override
		public JobListElement tick() {
			return this;
		}

		@Override
		public int getDelay(MHFCDelayedJob job) {
			return -1;
		}

		@Override
		public JobListElement remove(MHFCDelayedJob job) {
			return this;
		}

		@Override
		public boolean containsJob(MHFCDelayedJob job) {
			return false;
		}
	}

	public static MHFCJobHandler getJobHandler() {
		return instance;
	}

	private JobListElement startOfList;

	private MHFCJobHandler() {
		startOfList = new JobListEnd();
	}

	@SubscribeEvent
	public void receiveTick(ClientTickEvent tick) {
		startOfList = startOfList.tick();
	}

	@SubscribeEvent
	public void receiveTick(ServerTickEvent tick) {
		startOfList = startOfList.tick();
	}

	public void insert(MHFCDelayedJob job, int delay) {
		startOfList = startOfList.insert(job, delay);
	}

	public void remove(MHFCDelayedJob job) {
		startOfList = startOfList.remove(job);
	}

	public boolean containsJob(MHFCDelayedJob job) {
		return startOfList.containsJob(job);
	}

	public int getDelay(MHFCDelayedJob job) {
		return startOfList.getDelay(job);
	}

}
