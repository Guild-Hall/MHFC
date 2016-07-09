package mhfc.net.common.util.services;

import java.util.function.Consumer;

/**
 * Is the public interface of the key for a phase.
 * <p>
 *
 * @author WorldSEnder
 *
 * @param A
 *            the type of startup context
 * @param Z
 *            the type of shutdown context
 */
public interface IPhaseKey<A, Z> {
	/**
	 * @return the {@link IServiceProvider} that this key is from
	 */
	IServiceProvider getServiceProvider();

	/**
	 * Registers an entry callback. Registered consumers will be called when the phase enters in unspecified order.
	 *
	 *
	 * @param onEntry
	 *            the callback
	 */
	void registerEntryCallback(Consumer<A> onEntry);

	void unregisterEntryCallback(Consumer<A> onEntry);

	void registerExitCallback(Consumer<Z> onExit);

	void unregisterExitCallback(Consumer<Z> onExit);
}
