package mhfc.net.common.util.services;

/**
 * Marker interface that identifies and authenticates the user to manipulate the properties of the represented service.
 * Normally you want to generate one with {@link PrivateID#generateServiceID()}. Should be kept private as it allows the
 * holder to enable/disable a service and register it for phases.
 *
 * @author WorldSEnder
 *
 * @param <T>
 *            the type of service offered.
 */
public interface IServiceID<T> {
	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);
}
