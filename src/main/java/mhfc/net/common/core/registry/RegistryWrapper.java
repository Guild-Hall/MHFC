package mhfc.net.common.core.registry;

import java.util.function.Supplier;

import mhfc.net.common.util.services.IPhaseKey;
import mhfc.net.common.util.services.IServiceAccess;
import mhfc.net.common.util.services.IServiceHandle;
import mhfc.net.common.util.services.IServiceKey;
import mhfc.net.common.util.services.IServicePhaseHandle;
import mhfc.net.common.util.services.Services;

public class RegistryWrapper<T> {
	private T registry;
	private final Supplier<T> newRegistry;

	public RegistryWrapper(Supplier<T> newRegistry) {
		this.newRegistry = newRegistry;
	}

	public void register() {
		assert this.registry == null;
		this.registry = newRegistry.get();
	}

	public void unregister() {
		assert this.registry != null;
		this.registry = null;
	}

	public T getRegistry() {
		if (registry == null) {
			throw new IllegalStateException("Registry not active");
		}
		return registry;
	}

	public static <T, A, Z> IServiceKey<T> registerService(
			String name,
			Supplier<T> newRegistry,
			IPhaseKey<A, Z> phase) {
		IServiceAccess<RegistryWrapper<T>> serviceAccess = Services.instance
				.registerService(name, IServiceHandle.noInit(), () -> new RegistryWrapper<>(newRegistry));

		serviceAccess.addTo(phase, new IServicePhaseHandle<RegistryWrapper<T>, A, Z>() {
			@Override
			public void onPhaseStart(RegistryWrapper<T> service, A startupContext) {
				service.register();
			}

			@Override
			public void onPhaseEnd(RegistryWrapper<T> service, Z shutdownContext) {
				service.unregister();
			}
		});
		return serviceAccess.withIndirection(RegistryWrapper::getRegistry);
	}
}
