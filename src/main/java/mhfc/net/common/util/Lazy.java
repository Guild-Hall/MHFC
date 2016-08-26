package mhfc.net.common.util;

import java.util.Objects;
import java.util.function.Supplier;

public class Lazy<T> implements Supplier<T> {
	private Object lock = new Object();
	private boolean isInit = false;
	private T value = null;
	private Supplier<T> initialSupplier;

	/**
	 * Constructs a new lazy that will use initialSupplier when no value is present, otherwise return the present value.
	 *
	 * @param initialSupplier
	 */
	public Lazy(Supplier<T> initialSupplier) {
		this.initialSupplier = Objects.requireNonNull(initialSupplier);
	}

	private void compute() {
		synchronized (lock) {
			if (isInit) {
				return;
			}
			this.value = initialSupplier.get();
		}
	}

	@Override
	public T get() {
		if (!isInit) {
			compute();
		}
		return value;
	}
}
