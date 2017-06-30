package mhfc.net.common.util;

import java.util.Optional;

/**
 * Implements a resource that may not need to be closed after a certain point. Possible use case:
 * 
 * <pre>
 * <code>try(DetachableResource<InputStream> resource = new DetachableResource(openFile())) {
 *     // First, do some work with the original resource.
 *     // The resource will get closed if the method return abruptly
 *     setupParameters(resource.get());
 *     // Detach the resource, it will not get closed but handed off to the caller
 *     return continuation(resource.detach());
 * }</code>
 * </pre>
 * 
 * The problem with try-with-resource statements is that declared resources are implicitly final and as such can not be
 * reassigned. As a result, you can not opt out from the resource being closed at a later point in the try block.<br>
 * 
 * <b>Note: this class is not designed to be thread safe.</b>
 * 
 * @author WorldSEnder
 *
 * @param <T>
 *            the type of resource managed
 */
public class DetachableResource<T extends AutoCloseable> implements AutoCloseable {

	private Optional<T> resource;

	public DetachableResource(T resource) {
		this.resource = Optional.ofNullable(resource);
	}

	private void checkPresent() {
		if (resource == null) {
			throw new IllegalStateException("Resource was already detached");
		}
	}

	public T get() {
		checkPresent();

		return resource.orElse(null);
	}

	public T detach() {
		T res = get();
		this.resource = null;
		return res;
	}

	@Override
	public void close() throws Exception {
		if (resource != null && resource.isPresent()) {
			resource.get().close();
		}
	}

}
