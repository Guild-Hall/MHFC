package mhfc.net.common.util.parsing;

import com.google.common.collect.ComputationException;

import mhfc.net.common.util.parsing.valueholders.MemberAccess;

public interface IValueHolder {

	public static final Class<?> EMPTY_CLASS = void.class;

	/**
	 * Snapshot the current state of this {@link IValueHolder} into an immutable {@link Holder}. If the computation
	 * fails at any point or something else unforseeably happens it is appropriate to throw a
	 * {@link ComputationException}.
	 *
	 * @return the Holder this {@link IValueHolder} represents at the time of invokation, never <code>null</code>
	 */
	Holder snapshot();

	default boolean isSnapshot() {
		return false;
	}

	/**
	 * Makes this {@link IValueHolder} independant of other {@link IValueHolder} s. <br>
	 * <b>Requirements:</b> The returned value shall return the same class for every future invocation on the returned
	 * value of {@link #getType()}. Different stored values are allowed, though.<br>
	 * If {@link #isClassSnapshot()} would return <code>true</code>, then <code>this</code> should be returned.<br>
	 * Clarification: The value returned may still be dependable on the time when the next {@link #snapshot()} is
	 * invoked for the resulting value computation. For example a {@link MemberAccess} chooses to snapshot the
	 * {@link IValueHolder} the member is being accessed on but does not retrieve the actual value of the field until
	 * {@link #snapshot()} is invoked.<br>
	 *
	 * @return
	 */
	IValueHolder snapshotClass();

	default boolean isClassSnapshot() {
		return false;
	}

	/**
	 * Gets the class of the stored object, in other words the the class an object would have if {@link #snapshot()} was
	 * being invoked at this point. Actually computing the value of the Object shall explicitly not be done. <br>
	 * {@link #EMPTY_CLASS} is appropriate if the {@link Class} can not be deduced at the moment.
	 *
	 * @return
	 */
	Class<?> getType();

}
