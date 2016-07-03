package mhfc.net.common.util.services;

/**
 * Offers phase providers a way to publish their phase. They should be kept private so that not everybody can start or
 * end a specific phase.
 *
 * @author WorldSEnder
 *
 * @param A
 *            the type of startup context
 * @param Z
 *            the type of shutdown context
 */
public interface IPhaseAccess<A, Z> extends IPhaseKey<A, Z> {

	/**
	 * Signals that this phase starts. All services that have been registered will be bootstrapped.
	 * <p>
	 * If the phase has already been started, this call is invalid.
	 *
	 * @param startupContext
	 * @throws IllegalStateException
	 *             if the phase is already active
	 */
	void enterPhase(A startupContext) throws IllegalStateException;

	/**
	 * Signals that this phase stops. All services that have been active will be shutdown.
	 *
	 * @param shutdownContext
	 * @throws IllegalStateException
	 *             if the phase is currently not active
	 */
	void exitPhase(Z shutdownContext) throws IllegalStateException;

	/**
	 * Declares that other is a parent phase of this phase. This is used for sanity checks.
	 * <p>
	 * If a phase A has a parent phase B, this means that A has to start after B has been started and A has to end
	 * before B ends.
	 *
	 * @param other
	 *            the registry of the parent phase. Must come from the same ServiceProvider.
	 * @throws IllegalArgumentException
	 */
	void declareParent(IPhaseKey<?, ?> other) throws IllegalArgumentException;

}
