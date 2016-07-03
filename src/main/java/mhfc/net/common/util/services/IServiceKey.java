package mhfc.net.common.util.services;

/**
 * Is the public interface of the key for a service.
 * <p>
 *
 * @author WorldSEnder
 *
 * @param <T>
 *            the type of the service this key is used for.
 */
public interface IServiceKey<T> {
	@Override
	boolean equals(Object obj);

	@Override
	int hashCode();
}
