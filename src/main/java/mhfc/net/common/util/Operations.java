package mhfc.net.common.util;

import com.google.common.util.concurrent.Runnables;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.function.operation.DelegateOperation;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.RunContext;

import java.util.List;
import java.util.function.Consumer;

public class Operations {
	/**
	 * An {@link Operation} that, when finished instantly calls the runnable.
	 *
	 * @param op
	 * @param runnable
	 * @return
	 */
	public static Operation withCallback(
			final Operation op,
			final Consumer<Operation> onCompletion,
			final Consumer<Operation> onCancel) {
		return new Operation() {
			private Operation current = op;
			private boolean isCancelled = false;

			@Override
			public Operation resume(RunContext run) throws WorldEditException {
				if (isCancelled) {
					return null;
				}
				current = current.resume(run);
				if (current == null) {
					onCompletion.accept(this);
					return null;
				}
				return this;
			}


			@Override
			public void cancel() {
				if (!isCancelled) {
					isCancelled = true;
					current.cancel();
					onCancel.accept(this);
				}
			}


			@Override
			public void addStatusMessages(List<String> messages) {
				current.addStatusMessages(messages);
			}
		};
	}

	/**
	 *
	 * @param operation
	 * @param delayedInvocations
	 * @return an operation that the first delayedInvocations times it gets resumed, does nothing, then falls back to
	 *         operation
	 */
	public static Operation delayed(final Operation operation, int delayedInvocations) {
		return sequentially(limitedLoop(noopForever(), delayedInvocations), operation);
	}

	/**
	 *
	 * @param first
	 * @param andThen
	 * @return an operation that first resumes first until finished, afterwards andThen
	 */
	public static Operation sequentially(final Operation first, final Operation andThen) {
		return new DelegateOperation(andThen, first);
	}

	public static Operation wrapping(final Runnable runnable) {
		return wrapping(runnable, Runnables.doNothing());
	}

	/**
	 * Returns an Operation that resumes the given operation op at most count times, then finishes
	 *
	 * @param op
	 * @param count
	 * @return
	 */
	public static Operation limitedLoop(final Operation op, int count) {
		if (count < 0) {
			throw new IllegalArgumentException("count must be greater or equal than 0");
		}
		if (count == 0) {
			return null;
		}
		return new Operation() {
			private int countLeft = count;
			private Operation current = op;

			@Override
			public Operation resume(RunContext run) throws WorldEditException {
				Operation followup = current.resume(run);
				if (followup == null || --countLeft <= 0) {
					return null;
				}
				current = followup;
				return this;
			}

			@Override
			public void addStatusMessages(List<String> messages) {
				current.addStatusMessages(messages);
			}

			@Override
			public void cancel() {
				this.current.cancel();
			}
		};
	}

	/**
	 *
	 * @return an operation that does nothing, once, then finishes
	 */
	public static Operation noop() {
		return new Operation() {

			@Override
			public Operation resume(RunContext run) throws WorldEditException {
				return null;
			}

			@Override
			public void addStatusMessages(List<String> messages) {
				// no-op has no status messages, probably?
			}

			@Override
			public void cancel() {}
		};
	}

	/**
	 *
	 * @return an operation that does nothing, forever
	 */
	public static Operation noopForever() {
		return new Operation() {

			@Override
			public Operation resume(RunContext run) throws WorldEditException {
				return this;
			}

			@Override
			public void cancel() {}

			@Override
			public void addStatusMessages(List<String> messages) {
			}
		};
	}

	/**
	 *
	 * @param op
	 * @param maxTimeMillis
	 * @return an operation that resumes op as many times as is needed to exceed a runtime of longTimeMillis
	 */
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


			@Override
			public void addStatusMessages(List<String> messages) {
				current.addStatusMessages(messages);
			}
		};
	}

	/**
	 * @param runnable
	 * @param cancel
	 * @return an operation that when resumed, calls runnable, when canceled cancel
	 */
	public static Operation wrapping(Runnable runnable, Runnable cancel) {
		return new Operation() {
			@Override
			public Operation resume(RunContext run) throws WorldEditException {
				runnable.run();
				return null;
			}

			@Override
			public void cancel() {
				cancel.run();
			}

			@Override
			public void addStatusMessages(List<String> messages) {}
		};
	}
}
