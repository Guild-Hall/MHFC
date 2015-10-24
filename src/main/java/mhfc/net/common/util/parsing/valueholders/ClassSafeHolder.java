package mhfc.net.common.util.parsing.valueholders;

import java.util.Objects;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

/**
 * A Holder that holds an object of a specific {@link Class}. The object can be
 * changed later, but the class can not.
 *
 * @author WorldSEnder
 *
 * @param <V>
 */
public class ClassSafeHolder<V> implements IValueHolder {
	private final Class<?> clazz;
	private V value;

	/**
	 * Constructs a ClassSafeHolder from a nonnull value. The Class is
	 * automatically determined from the given value.
	 *
	 * @param value
	 */
	public ClassSafeHolder(V value) {
		this.clazz = value.getClass();
		this.set(value);
	}

	/**
	 * Constructs a ClassSafeHolder from a possibly null value. The Class is
	 * given as a parameter. The object has to be an instance of that class.
	 *
	 * @param value
	 */
	public ClassSafeHolder(V value, Class<V> clazz) {
		this.clazz = Objects.requireNonNull(clazz);
		this.set(value);
	}

	public void set(V newVal) {
		if (!this.clazz.isInstance(newVal))
			throw new IllegalArgumentException(
					"new value not an instance of the class represented by this holder: " + clazz.getName());
		this.value = newVal;
	}

	public V get() {
		return this.value;
	}

	@Override
	public Class<?> getContainedClass() {
		return this.clazz;
	}

	@Override
	public Holder snapshot() {
		return Holder.valueOfUnsafe(this.value, this.clazz);
	}

	@Override
	public ClassSafeHolder<V> snapshotClass() {
		return this;
	}

	@Override
	public boolean isClassSnapshot() {
		return true;
	}
}
