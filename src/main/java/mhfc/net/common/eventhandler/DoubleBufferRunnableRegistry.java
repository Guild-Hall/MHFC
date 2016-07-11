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

	private List<RunEntry> current = new ArrayList<>();
	private List<RunEntry> other = new ArrayList<>();

	public void register(Runnable r, Runnable cancel) {
		Objects.requireNonNull(r);
		synchronized (runGuard) {
			current.add(new RunEntry(r, cancel));
		}
	}

	public void runAll() {
		synchronized (runGuard) {
			List<RunEntry> toRun = current;
			current = other;
			current.clear();
			other = toRun;
			for (RunEntry r : toRun) {
				r.run();
			}
		}
	}

	public void cancel() {
		synchronized (runGuard) {
			for (RunEntry r : current) {
				r.cancel();
			}
			current.clear();
			for (RunEntry r : other) {
				r.cancel();
			}
			other.clear();
		}
	}
}
