package mhfc.net.common.util.parsing.valueholders;

import java.util.Objects;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

/**
 * A Holder that holds an object of a specific {@link Class}. The object can be changed later, but the class can not.
 *
 * @author WorldSEnder
 *
 * @param <V>
 */
public class ClassSafeHolder<V> implements IValueHolder {
	private final Class<? super V> clazz;
	private V value;

	@SuppressWarnings("unchecked")
	private static <V> Class<? super V> extractClass(V value) {
		// Apparently unsafe...
		return (Class<? super V>) value.getClass();
	}

	/**
	 * Constructs a ClassSafeHolder from a nonnull value. The Class is automatically determined from the given value.
	 *
	 * @param value
	 */
	public ClassSafeHolder(V value) {
		this.clazz = extractClass(value);
		this.set(value);
	}

	/**
	 * Constructs a ClassSafeHolder from a possibly null value. The Class is given as a parameter. The object has to be
	 * an instance of that class.
	 *
	 * @param value
	 */
	public ClassSafeHolder(V value, Class<? super V> clazz) {
		this.clazz = Objects.requireNonNull(clazz);
		this.set(value);
	}

	public void set(V newVal) {
		if (!this.clazz.isInstance(newVal)) {
			throw new IllegalArgumentException(
					"new value not an instance of the class represented by this holder: " + clazz.getName());
		}
		this.value = newVal;
	}

	public V get() {
		return this.value;
	}

	@Override
	public Holder snapshot() {
		return Holder.valueOf(this.value, this.clazz);
	}

	@Override
	public String toString() {
		return Objects.toString(value);
	}
}
