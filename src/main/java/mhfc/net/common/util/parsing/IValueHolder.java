package mhfc.net.common.util.parsing;

import com.google.common.collect.ComputationException;

import mhfc.net.common.util.parsing.Holder.DefaultPolicies;
import mhfc.net.common.util.parsing.valueholders.MemberAccess;

public interface IValueHolder {
	public static interface FailPolicy {
		boolean failedBoolean(Class<?> existing);

		byte failedByte(Class<?> existing);

		char failedChar(Class<?> existing);

		short failedShort(Class<?> existing);

		int failedInt(Class<?> existing);

		long failedLong(Class<?> existing);

		float failedFloat(Class<?> existing);

		double failedDouble(Class<?> existing);

		<F> F failedObject(Class<?> existing, Class<F> request);
	}

	public static final Class<?> EMPTY_CLASS = void.class;

	/**
	 * Snapshot the current state of this {@link IValueHolder} into an immutable
	 * {@link Holder}. If the computation fails at any point or something else
	 * unforseeably happens it is appropriate to throw a
	 * {@link ComputationException}.
	 *
	 * @return the Holder this {@link IValueHolder} represents at the time of
	 *         invokation, never <code>null</code>
	 * @throws ComputationException
	 */
	Holder snapshot();

	default boolean isSnapshot() {
		return false;
	}

	/**
	 * Makes this {@link IValueHolder} independant of other {@link IValueHolder}
	 * s. <br>
	 * <b>Requirements:</b> The returned value shall return the same class for
	 * every future invocation on the returned value of
	 * {@link #getType()}. Different stored values are allowed,
	 * though.<br>
	 * If {@link #isClassSnapshot()} would return <code>true</code>, then
	 * <code>this</code> should be returned.<br>
	 * Clarification: The value returned may still be dependable on the time
	 * when the next {@link #snapshot()} is invoked for the resulting value
	 * computation. For example a {@link MemberAccess} chooses to snapshot the
	 * {@link IValueHolder} the member is being accessed on but does not
	 * retrieve the actual value of the field until {@link #snapshot()} is
	 * invoked.<br>
	 * {@link ComputationException} is appropriate to throw if something fails
	 *
	 * @return
	 * @throws ComputationException
	 */
	IValueHolder snapshotClass();

	default boolean isClassSnapshot() {
		return false;
	}

	default boolean asBool() throws ClassCastException {
		return asBool(getDefaultPolicy());
	}

	default boolean asBool(FailPolicy onFail) throws ClassCastException {
		return snapshot().asBool(onFail);
	}

	default byte asByte() throws ClassCastException {
		return asByte(getDefaultPolicy());
	}

	default byte asByte(FailPolicy onFail) throws ClassCastException {
		return snapshot().asByte(onFail);
	}

	default char asChar() throws ClassCastException {
		return asChar(getDefaultPolicy());
	}

	default char asChar(FailPolicy onFail) throws ClassCastException {
		return snapshot().asChar(onFail);
	}

	default double asDouble() throws ClassCastException {
		return asDouble(getDefaultPolicy());
	}

	default double asDouble(FailPolicy onFail) throws ClassCastException {
		return snapshot().asDouble(onFail);
	}

	default float asFloat() throws ClassCastException {
		return asFloat(getDefaultPolicy());
	}

	default float asFloat(FailPolicy onFail) throws ClassCastException {
		return snapshot().asFloat(onFail);
	}

	default int asInt() throws ClassCastException {
		return asInt(getDefaultPolicy());
	}

	default int asInt(FailPolicy onFail) throws ClassCastException {
		return snapshot().asInt(onFail);
	}

	default long asLong() throws ClassCastException {
		return asLong(getDefaultPolicy());
	}

	default long asLong(FailPolicy onFail) throws ClassCastException {
		return snapshot().asLong(onFail);
	}

	default short asShort() throws ClassCastException {
		return asShort(getDefaultPolicy());
	}

	default short asShort(FailPolicy onFail) throws ClassCastException {
		return snapshot().asShort(onFail);
	}

	default <F> F getAs(Class<F> fClazz) throws ClassCastException {
		return getAs(fClazz, getDefaultPolicy());
	}

	/**
	 * Gets the stored object. If the stored object can not be retrieved as an
	 * fClazz it is appropriate to throw a {@link ClassCastException}. It is
	 * appropriate to throw an {@link ComputationException} if the computation
	 * for the value failed.
	 *
	 * @param fClazz
	 * @param onFail
	 * @return
	 * @throws ClassCastException
	 */
	default <F> F getAs(Class<F> fClazz, FailPolicy onFail) throws ClassCastException {
		return snapshot().getAs(fClazz, onFail);
	}

	/**
	 * Gets the class of the stored object, in other words the the class an
	 * object would have if {@link #snapshot()} was being invoked at this point.
	 * Actually computing the value of the Object shall explicitly not be done.
	 * <br>
	 * {@link #EMPTY_CLASS} is appropriate if the {@link Class} can not be
	 * deduced at the moment.
	 *
	 * @return
	 */
	Class<?> getType();

	default FailPolicy getDefaultPolicy() {
		return DefaultPolicies.STRICT;
	}

}
