package mhfc.net.common.util.services;

import java.util.function.Supplier;

public interface IServiceProvider {
	/**
	 * Gets the service for this key
	 *
	 * @param serviceKey
	 *            the key of the service to retrieve.
	 * @return the service
	 * @throws IllegalArgumentException
	 *             if no service for the specified key was registered
	 * @throws IllegalStateException
	 *             if no service for the specified key is currently running
	 */
	<T> T getServiceFor(IServiceKey<T> serviceKey);

	/**
	 * Determines if a given phase is currently active
	 *
	 * @param phase
	 *            the phase to poll
	 * @return true if the phase is currently active
	 */
	public boolean isActive(IPhaseKey<?, ?> phase);

	/**
	 * Registers a service. The service
	 * <p>
	 * Note that the service used here should <b>not be accessible</b> publicly. Instead provide a wrapper method to
	 * retrive the {@link IServiceKey}:
	 *
	 * <pre>
	 * <code>
	 *class ExampleService {
	 *    private static final IServiceAccess<ExampleService> access = Services.registerService(...);
	 *    static {
	 *        //... configure the service with access
	 *    }
	 *
	 *    public IServiceKey<ExampleService> getServiceKey() {
	 *        return (IServiceKey<Void, Void>) access;
	 *    }
	 *}</code>
	 * </pre>
	 *
	 * @param serviceBootstrap
	 * @param service
	 */
	<T> IServiceAccess<T> registerService(IServiceHandle<T> serviceBootstrap, Supplier<T> serviceSupplier);

	/**
	 * Retrieves a {@link IPhaseKey} that can be used to register services for the service phase given.
	 * <p>
	 * Note that the servicePhase used here should <b>not be accessible</b> publicly. Instead provide a wrapper method
	 * to retrive the {@link IPhaseKey} for the phase:
	 *
	 * <pre>
	 * <code>
	 *class ExamplePhase {
	 *    private static final IPhaseAccess<Void, Void> access = Services.registerPhase();
	 *    static {
	 *        //... configure the phase with access
	 *    }
	 *
	 *    public IPhaseKey<Void, Void> getServiceRegistry() {
	 *        return (IPhaseKey<Void, Void>) access;
	 *    }
	 *}</code>
	 * </pre>
	 *
	 * @param servicePhase
	 * @return a registry that can be used to enter the phase and register services for it
	 */
	<A, Z> IPhaseAccess<A, Z> registerPhase();
}
