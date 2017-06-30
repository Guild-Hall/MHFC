package mhfc.net.common.util.services;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * A service handle is a service's interface to the global outside world.
 *
 * @author WorldSEnder
 *
 * @param T
 *            the type of the service
 */
public interface IServiceHandle<T> {
	public static <T> IServiceHandle<T> noInit(Supplier<T> factory) {
		Objects.requireNonNull(factory);

		return new IServiceHandle<T>() {
			@Override
			public T createInstance() {
				return factory.get();
			}

			@Override
			public void shutdown(T instance) {}

			@Override
			public void startup(T instance) {}
		};
	}

	/**
	 * Called to create a new, fresh instance of the service. Most likely startup is called soon after with the returned
	 * instance.
	 * 
	 * @return
	 */
	T createInstance();

	/**
	 * Called when the service is being started. Only to be called when the service is not currently running.
	 *
	 */
	void startup(T instance);

	/**
	 * Called before the service is being shutdown. No further users should be served afterwards. Only to be called when
	 * the service is currently running. It is guaranteed that all phases this service is being registered for are being
	 * exited (by calling {@link IServicePhaseHandle#onPhaseEnd(Object, Object)}) before this is called.
	 * <p>
	 * <b> Do not depend on this always being called. It could be that the program is forcefully closed.
	 *
	 */
	void shutdown(T instance);
}
