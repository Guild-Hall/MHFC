package mhfc.net.common.eventhandler;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

@Deprecated
public class MHFCJobHandler {
	private static final class JobEntry implements Comparable<JobEntry> {
		public final long procTime;
		public final DelayedJob job;

		public JobEntry(long procTime, DelayedJob job) {
			this.procTime = procTime;
			this.job = Objects.requireNonNull(job);
		}

		@Override
		public int compareTo(JobEntry o) {
			return Long.compare(this.procTime, o.procTime);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof DelayedJob) {
				return job.equals(obj);
			}
			if (obj instanceof JobEntry) {
				JobEntry o = (JobEntry) obj;
				return this.job.equals(o.job) && this.procTime == o.procTime;
			}
			return false;
		}
	}

	public static final int ticksPerSecond = 20;
	public static final MHFCJobHandler instance = new MHFCJobHandler();

	public static MHFCJobHandler instance() {
		return instance;
	}

	private BlockingQueue<JobEntry> jobQueue = new PriorityBlockingQueue<>(20);
	private Collection<DelayedJob> thisTick = new Vector<DelayedJob>();
	private Collection<DelayedJob> listOfRemoves = new HashSet<DelayedJob>();
	private volatile AtomicLong now = new AtomicLong(0);
	// See http://stackoverflow.com/q/11167566/
	private Object lock = new Object();

	private MHFCJobHandler() {}

	private void doRemoves() {
		synchronized (listOfRemoves) {
			this.thisTick.removeAll(listOfRemoves);
			this.jobQueue.removeAll(listOfRemoves);
			listOfRemoves.clear();
		}
	}

	private void tick(TickEvent tick) {
		synchronized (this.lock) {
			doRemoves();
			if (tick.phase == Phase.START && !jobQueue.isEmpty()) {
				thisTick.clear();
				long timeNow = now.incrementAndGet();
				assert (!(jobQueue.peek().procTime < timeNow));
				while (jobQueue.peek().procTime == timeNow) {
					DelayedJob job = jobQueue.poll().job;
					thisTick.add(job);
					job.executeJob(Phase.START);
				}
			} else {
				for (DelayedJob job : thisTick) {
					job.executeJob(Phase.END);
				}
			}

		}
	}

	@SubscribeEvent
	public void receiveTick(ClientTickEvent tick) {
		tick(tick);
	}

	@SubscribeEvent
	public void receiveTick(ServerTickEvent tick) {
		// Only if we really are on a server
		if (!FMLCommonHandler.instance().getSide().isServer()) {
			return;
		}
		tick(tick);
	}

	/**
	 * Insert a new job into the Handler. If the delay is less than or equal to zero, an
	 * {@link IllegalArgumentException} is thrown. If the job is <code>null</code> an {@link NullPointerException} is
	 * thrown.
	 *
	 * @param job
	 *            the job to insert
	 * @param delay
	 *            the delay after which to execute the job
	 * @return a time-point at which the job is scheduled to be executed, the difference between the received timepoint
	 *         and the current time can be polled with {@link #getDelay(DelayedJob)}
	 */
	public long insert(DelayedJob job, long delay) {
		if (delay <= 0) {
			throw new IllegalArgumentException("Delay must be positive: " + delay);
		}
		Objects.requireNonNull(job);
		synchronized (this.lock) {
			long jobTime = now.get() + delay;
			jobQueue.add(new JobEntry(jobTime, job));
			return jobTime;
		}

	}

	public void remove(DelayedJob job) {
		Objects.requireNonNull(job);
		synchronized (listOfRemoves) {
			listOfRemoves.add(job);
		}
	}

	public boolean containsJob(DelayedJob job) {
		Objects.requireNonNull(job);
		return jobQueue.contains(job);
	}

	/**
	 * Can be used to poll how much time is left until a job that was previously registered with
	 * {@link #insert(DelayedJob, long)} is due. Given the return value of the {@link #insert(DelayedJob, long)} method,
	 * this tells how long (in ticks) until the job is due.
	 *
	 * @param timepoint
	 *            the return value of {@link #insert(DelayedJob, long)}
	 * @return the amount of ticks until the job is due.
	 */
	public long getDelay(long timepoint) {
		return timepoint - now.get();
	}
}
