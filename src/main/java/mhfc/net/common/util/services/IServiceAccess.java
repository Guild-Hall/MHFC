package mhfc.net.common.util.services;

/**
 * Offers service providers a way to publish their service. They should be kept private so that not everybody can bind
 * the service to specific phases
 *
 * @author WorldSEnder
 *
 * @param T
 *            the type of service offered
 */
public interface IServiceAccess<T> extends IServiceKey<T> {
	/**
	 * Used to register a service as being active during the phase. Normally a phase offers a way to retrieve its
	 * {@link IPhaseKey} with a public method.
	 * <p>
	 * Note that a specific {@link IServiceID} can be registered for multiple phases.
	 *
	 *
	 * @param phase
	 *            the phase to register for
	 * @return this
	 * @param phaseBootstrapper
	 *            the handler that is used to start and stop the phase
	 */
	<A, Z> IServiceAccess<T> addTo(IPhaseKey<A, Z> phase, IServicePhaseHandle<T, A, Z> phaseBootstrapper);
}
