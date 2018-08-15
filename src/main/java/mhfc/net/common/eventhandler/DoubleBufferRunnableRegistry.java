package mhfc.net.common.eventhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DoubleBufferRunnableRegistry {
	private Object runGuard = new Object();

	private static class RunEntry implements Runnable {
		private Runnable run;
		private Runnable cancel;

		public RunEntry(Runnable run, Runnable cancel) {
			this.run = run;
			this.cancel = cancel;
		}

		@Override
		public void run() {
			this.run.run();
		}

		public void cancel() {
			if (this.cancel == null) {
				return;
			}
			this.cancel.run();
		}
	}

	private List<RunEntry> frontBuffer = new ArrayList<>();
	private List<RunEntry> backbuffer = new ArrayList<>();

	public void register(Runnable r, Runnable cancel) {
		Objects.requireNonNull(r);
		// Re-entry to register during running
		synchronized (runGuard) {
			frontBuffer.add(new RunEntry(r, cancel));
		}
	}

	public void runAll() {
		synchronized (runGuard) {
			backbuffer.clear();
			// Swap front and back, then run old front
			List<RunEntry> toRun = frontBuffer;
			frontBuffer = backbuffer;
			backbuffer = toRun;
			for (RunEntry r : toRun) {
				r.run();
			}
		}
	}

	public void cancel() {
		synchronized (runGuard) {
			for (RunEntry r : frontBuffer) {
				r.cancel();
			}
			frontBuffer.clear();
			for (RunEntry r : backbuffer) {
				r.cancel();
			}
			backbuffer.clear();
		}
	}
}
