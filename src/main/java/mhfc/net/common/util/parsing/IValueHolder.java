package mhfc.net.common.util.parsing;

import com.google.common.collect.ComputationException;

public interface IValueHolder {

	public static final Class<?> EMPTY_CLASS = void.class;

	/**
	 * Snapshot the current state of this {@link IValueHolder} into an immutable {@link Holder}. If the computation
	 * fails at any point or something else unforseeably happens it is appropriate to throw a
	 * {@link ComputationException}.<br>
	 * Note that, if you want to catch exceptions thrown by the computations, consider using
	 * {@link Holder#snapshotSafely(IValueHolder)}.
	 *
	 * @return the Holder this {@link IValueHolder} represents at the time of invokation, never <code>null</code>
	 */
	Holder snapshot();

	/**
	 * Returns <code>true</code> if successive calls to {@link #getType()} would always return the same class.
	 *
	 * @return
	 */
	default boolean isTypeFinal() {
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
