package mhfc.net.common.util.services;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

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
	/**
	 * @return the {@link IServiceProvider} that this key is from
	 */
	IServiceProvider getServiceProvider();

	/**
	 * Represents a service key that, instead of retrieving this key's service, first applies it to the function given.
	 *
	 * @param remap
	 *            the remapper from T to O. Not null
	 * @return A new service key to retrieve the remapped service.
	 */
	default <O> IServiceKey<O> withIndirection(Function<T, O> remap) {
		Objects.requireNonNull(remap);
		final IServiceKey<T> original = this;

		return new IServiceKey<O>() {
			@Override
			public IServiceProvider getServiceProvider() {
				return original.getServiceProvider();
			}

			@Override
			public O getService() {
				return remap.apply(original.getService());
			}
		};
	}

	default Optional<T> get() {
		return getServiceProvider().getServiceFor(this);
	}

	default T getService() {
		return getServiceProvider().getServiceFor(this)
				.orElseThrow(() -> new IllegalStateException("Service not active"));
	}
}
