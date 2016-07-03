package mhfc.net.common.util.services;

/**
 * Marker interface that identifies and authenticates the user to manipulate the properties of the represented phase.
 * Normally you want to generate one with {@link PrivateID#generatePhaseID()}. Should be kept private as it allows the
 * holder to start and stop the phase.
 *
 * @author WorldSEnder
 *
 * @param <A>
 *            the type of context when the phase starts
 * @param <Z>
 *            the type of context when the phase stops
 */
public interface IPhaseID<A, Z> {
	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);
}
