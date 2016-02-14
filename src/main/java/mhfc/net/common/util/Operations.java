package mhfc.net.common.util;

import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.RunContext;

public class Operations {
	/**
	 * An {@link Operation} that, when finished instantly calls the runnable.
	 *
	 * @param op
	 * @param runnable
	 * @return
	 */
	public static Operation withCallback(final Operation op, final Runnable runnable) {
		return new Operation() {
			private Operation current = op;

			@Override
			public Operation resume(RunContext run) throws WorldEditException {
				current = current.resume(run);
				if (current == null) {
					runnable.run();
				}
				return current == null ? null : this;
			}

			@Override
			public void cancel() {
				current.cancel();
			}
		};
	}

	public static Operation wrapping(final Runnable runnable) {
		return new Operation() {
			@Override
			public Operation resume(RunContext run) {
				runnable.run();
				return null;
			}

			@Override
			public void cancel() {}
		};
	}

	public static Operation limitedLoop(final Operation op, int count) {
		if (count <= 0) {
			throw new IllegalArgumentException("count must be greater than 0");
		}
		return new Operation() {
			private Operation current = op;

			@Override
			public Operation resume(RunContext run) throws WorldEditException {
				for (int i = 0; i < count; i++) {
					if ((current = current.resume(run)) == null) {
						break;
					}
				}
				return current == null ? null : this;
			}

			@Override
			public void cancel() {
				this.current.cancel();
			}
		};
	}

	public static Operation noop() {
		return new Operation() {

			@Override
			public Operation resume(RunContext run) throws WorldEditException {
				return null;
			}

			@Override
			public void cancel() {}
		};
	}

	public static Operation timingOperation(final Operation op, long maxTimeMillis) {
		if (maxTimeMillis < 0) {
			throw new IllegalArgumentException("maxTime must be greater than 0");
		}
		return new Operation() {
			private Operation current = op;

			@Override
			public Operation resume(RunContext run) throws WorldEditException {
				long then = System.currentTimeMillis();
				while ((current = current.resume(run)) != null) {
					long now = System.currentTimeMillis();
					if (now - then > maxTimeMillis) {
						break;
					}
				}
				return current == null ? null : this;
			}

			@Override
			public void cancel() {}
		};
	}
}
