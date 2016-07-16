package mhfc.net.common.util.parsing;

import com.google.common.collect.ComputationException;

@FunctionalInterface
public interface IValueHolder {

	public static final Class<?> EMPTY_CLASS = void.class;

	/**
	 * Snapshot the current state of this {@link IValueHolder} into an immutable {@link Holder}. If the computation
	 * fails at any point or something else unforseeably happens it is appropriate to throw a
	 * {@link ComputationException}.<br>
	 * Note that, if you want to catch exceptions thrown by the computations, consider using
	 * {@link Holder#snapshotSafely(IValueHolder)}.<br>
	 *
	 * @return the Holder this {@link IValueHolder} represents at the time of invokation, never <code>null</code>
	 */
	Holder snapshot() throws Throwable;
}
