package mhfc.net.common.util.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.apache.commons.lang3.NotImplementedException;

import com.google.common.base.Preconditions;

/**
 * This singleton class offers a way to retrieve Services during different phases of the game. This is kind of a sanity
 * check if all phases are correctly implemented and all services are being correctly started and shut down.
 *
 * @author WorldSEnder
 *
 */
public class Services implements IServiceProvider {
	private static enum LifeCycle {
		NOT_INITIALIZED,
		BOOTSTRAPPED,
		ACTIVE;
	}

	/**
	 * Marker interface that identifies and authenticates the user to manipulate the properties of the represented
	 * phase. Normally you want to generate one with {@link PrivateID#generatePhaseID()}. Should be kept private as it
	 * allows the holder to start and stop the phase.
	 *
	 * @author WorldSEnder
	 *
	 * @param <A>
	 *            the type of context when the phase starts
	 * @param <Z>
	 *            the type of context when the phase stops
	 */
	private static interface IPhaseID<A, Z> {
		@Override
		int hashCode();

		@Override
		boolean equals(Object obj);
	}

	/**
	 * Marker interface that identifies and authenticates the user to manipulate the properties of the represented
	 * service. Normally you want to generate one with {@link PrivateID#generateServiceID()}. Should be kept private as
	 * it allows the holder to enable/disable a service and register it for phases.
	 *
	 * @author WorldSEnder
	 *
	 * @param <T>
	 *            the type of service offered.
	 */
	private static interface IServiceID<T> {
		@Override
		int hashCode();

		@Override
		boolean equals(Object obj);
	}

	@SuppressWarnings("rawtypes")
	private static class ID implements IServiceID, IPhaseID {
		private static final AtomicInteger COUNTER = new AtomicInteger(0);

		private final int id = COUNTER.getAndIncrement();

		@Override
		public boolean equals(Object obj) {
			return obj instanceof ID && ((ID) obj).id == id;
		}

		@Override
		public int hashCode() {
			return Integer.hashCode(id);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> IServiceID<T> nextServiceID() {
		return new ID();
	}

	@SuppressWarnings("unchecked")
	private static <A, Z> IPhaseID<A, Z> nextPhaseID() {
		return new ID();
	}

	public static Services instance = new Services();

	/**
	 * Gets the instance
	 *
	 * @return
	 */
	public static Services getInstance() {
		return instance;
	}

	/**
	 * simply <code>getInstance().getServiceFor(serviceKey)</code>
	 *
	 * @param serviceKey
	 * @return
	 * @see #getServiceFor(IServiceKey)
	 */
	public static <T> T getService(IServiceKey<T> serviceKey) {
		return getInstance().getServiceFor(serviceKey);
	}

	private class ServiceEntry<T> implements IServiceAccess<T> {
		private final IServiceID<T> serviceID;
		private T service = null;
		private final Supplier<T> bootstrap;
		private LifeCycle state = LifeCycle.NOT_INITIALIZED;
		private IServiceHandle<T> handle;
		private Set<IPhaseID<?, ?>> activePhases = new HashSet<>();

		protected ServiceEntry(IServiceID<T> serviceID, Supplier<T> bootstrap, IServiceHandle<T> handle) {
			this.serviceID = Objects.requireNonNull(serviceID);
			this.bootstrap = Objects.requireNonNull(bootstrap);
			this.handle = Objects.requireNonNull(handle);
		}

		private void ensureBootstrapped() {
			if (state == LifeCycle.NOT_INITIALIZED) {
				service = Objects.requireNonNull(bootstrap.get());
				state = LifeCycle.BOOTSTRAPPED;
			}
			assert service != null;
		}

		private void ensureActive() {
			if (state == LifeCycle.ACTIVE) {
				return;
			}
			startup();
		}

		protected LifeCycle getState() {
			return state;
		}

		protected IServiceID<T> getID() {
			return serviceID;
		}

		private void maybeDispose() {
			assert state == LifeCycle.BOOTSTRAPPED;
			// service = null;
			// state = LifeCycle.NOT_INITIALIZED;
		}

		private void startup() {
			ensureBootstrapped();
			assert state == LifeCycle.BOOTSTRAPPED;
			handle.startup(service);
			state = LifeCycle.ACTIVE;
		}

		private void shutdown() {
			assert state == LifeCycle.ACTIVE && activePhases.isEmpty();
			handle.shutdown(service);
			state = LifeCycle.BOOTSTRAPPED;
			maybeDispose();
		}

		@Override
		public <A, Z> void addTo(IPhaseKey<A, Z> phase, IServicePhaseHandle<T, A, Z> phaseBootstrapper) {
			Services.this.registerServiceForPhase(getID(), tryUpcastPhase(phase), phaseBootstrapper);
		}

		protected void beforePhase(IPhaseID<?, ?> phase) {
			ensureActive();
			assert !activePhases.contains(phase);
			activePhases.add(phase);
			assert activePhases.contains(phase);
		}

		protected void afterPhase(IPhaseID<?, ?> phase) {
			assert state == LifeCycle.ACTIVE;
			assert activePhases.contains(phase);
			activePhases.remove(phase);
			assert !activePhases.contains(phase);
			if (activePhases.isEmpty()) {
				shutdown();
			}
		}

		protected T getService() {
			assert state == LifeCycle.ACTIVE;
			return service;
		}

		@Override
		public Services getServiceProvider() {
			return Services.this;
		}
	}

	private class PhaseEntry<A, Z> implements IPhaseAccess<A, Z> {
		private class ServicePhaseEntry<T> {
			private ServiceEntry<T> service;
			private IServicePhaseHandle<T, A, Z> phaseHandler;

			protected ServicePhaseEntry(ServiceEntry<T> service, IServicePhaseHandle<T, A, Z> phaseHandler) {
				this.service = Objects.requireNonNull(service);
				this.phaseHandler = Objects.requireNonNull(phaseHandler);
			}

			protected void enter(A context) {
				service.beforePhase(PhaseEntry.this.getID());
				phaseHandler.onPhaseStart(service.getService(), context);
			}

			protected void exit(Z context) {
				phaseHandler.onPhaseEnd(service.getService(), context);
				service.afterPhase(PhaseEntry.this.getID());
			}

			@Override
			public boolean equals(Object obj) {
				if (!(obj instanceof ServicePhaseEntry)) {
					return false;
				}
				return Objects.equals(service, ServicePhaseEntry.class.cast(obj).service);
			}

			@Override
			public int hashCode() {
				return service.hashCode();
			}
		}

		private IPhaseID<A, Z> phaseID;
		private Set<ServicePhaseEntry<?>> dependantServices;

		protected PhaseEntry(IPhaseID<A, Z> phase) {
			this.phaseID = Objects.requireNonNull(phase);
			this.dependantServices = new HashSet<>();
		}

		protected IPhaseID<A, Z> getID() {
			return phaseID;
		}

		@Override
		public Services getServiceProvider() {
			return Services.this;
		}

		protected void enterServices(A context) {
			for (ServicePhaseEntry<?> service : dependantServices) {
				service.enter(context);
			}
		}

		protected void exitServices(Z context) {
			for (ServicePhaseEntry<?> service : dependantServices) {
				service.exit(context);
			}
		}

		protected <T> void addService(ServiceEntry<T> service, IServicePhaseHandle<T, A, Z> phaseHandler) {
			ServicePhaseEntry<T> phaseEntry = new ServicePhaseEntry<>(service, phaseHandler);
			Preconditions.checkArgument(
					!dependantServices.contains(phaseEntry),
					"service " + service.getID() + " already registered for the phase" + phaseID);
			this.dependantServices.add(phaseEntry);
		}

		@Override
		public void enterPhase(A startupContext) throws IllegalStateException {
			Services.this.enterPhase(phaseID, startupContext);
		}

		@Override
		public void exitPhase(Z shutdownContext) throws IllegalStateException {
			Services.this.exitPhase(phaseID, shutdownContext);
		}

		@Override
		public void declareParent(IPhaseKey<?, ?> other) throws IllegalArgumentException {
			Services.this.declareParent(getID(), tryUpcastPhase(other));
		}

	}

	private Map<IServiceID<?>, ServiceEntry<?>> services = new HashMap<>();
	private Map<IPhaseID<?, ?>, PhaseEntry<?, ?>> phases = new HashMap<>();
	private Set<IPhaseID<?, ?>> activePhases = new HashSet<>();

	@Override
	public <T> T getServiceFor(IServiceKey<T> serviceKey) {
		IServiceID<T> serviceID = tryUpcastService(serviceKey);
		ServiceEntry<T> service = getService(serviceID);

		Preconditions.checkState(service.getState() == LifeCycle.ACTIVE, "Service not active");

		return service.getService();
	}

	public boolean isActive(IPhaseKey<?, ?> phase) {
		return isActive(tryUpcastPhase(phase));
	}

	@Override
	public <T> IServiceAccess<T> registerService(IServiceHandle<T> serviceBootstrap, Supplier<T> serviceSupplier) {
		IServiceID<T> serviceID = nextServiceID();
		assert !hasService(serviceID);

		ServiceEntry<T> entry = new ServiceEntry<>(serviceID, serviceSupplier, serviceBootstrap);
		addService(serviceID, entry);
		return entry;
	}

	@Override
	public <A, Z> IPhaseAccess<A, Z> registerPhase() {
		IPhaseID<A, Z> phaseID = nextPhaseID();
		assert !hasPhase(phaseID);

		PhaseEntry<A, Z> entry = new PhaseEntry<>(phaseID);
		addPhase(phaseID, entry);
		return entry;
	}

	// --- Helper methods

	private <T> IServiceID<T> tryUpcastService(IServiceKey<T> key) {
		Preconditions.checkArgument(key instanceof ServiceEntry, "not a service key from this service provider");
		ServiceEntry<T> entry = ServiceEntry.class.cast(key);
		Preconditions.checkArgument(entry.getServiceProvider() == this, "not a service key from this service provider");
		IServiceID<T> id = entry.getID();
		assert hasService(id);
		return id;
	}

	private <A, Z> IPhaseID<A, Z> tryUpcastPhase(IPhaseKey<A, Z> key) {
		Preconditions.checkArgument(key instanceof PhaseEntry, "not a phase key from this service provider");
		PhaseEntry<A, Z> entry = PhaseEntry.class.cast(key);
		Preconditions.checkArgument(entry.getServiceProvider() == this, "not a phase key from this service provider");
		IPhaseID<A, Z> id = entry.getID();
		assert hasPhase(id);
		return id;
	}

	@SuppressWarnings("unchecked")
	private <T> ServiceEntry<T> getService(IServiceID<T> serviceID) {
		assert hasService(serviceID);
		return (ServiceEntry<T>) services.get(serviceID);
	}

	@SuppressWarnings("unchecked")
	private <A, Z> PhaseEntry<A, Z> getPhase(IPhaseID<A, Z> phaseID) {
		assert hasPhase(phaseID);
		return (PhaseEntry<A, Z>) phases.get(phaseID);
	}

	private boolean hasService(IServiceID<?> serviceID) {
		return services.containsKey(serviceID);
	}

	private boolean hasPhase(IPhaseID<?, ?> phaseID) {
		return phases.containsKey(phaseID);
	}

	private <T> void addService(IServiceID<T> serviceID, ServiceEntry<T> entry) {
		assert !hasService(serviceID);
		services.put(serviceID, entry);
	}

	private <A, Z> void addPhase(IPhaseID<A, Z> phaseID, PhaseEntry<A, Z> entry) {
		assert !hasPhase(phaseID);
		phases.put(phaseID, entry);
	}

	private boolean isActive(IPhaseID<?, ?> phase) {
		return activePhases.contains(phase);
	}

	// --- Service actions

	private <T, A, Z> void registerServiceForPhase(
			IServiceID<T> service,
			IPhaseID<A, Z> phase,
			IServicePhaseHandle<T, A, Z> phaseBootstrapper) {
		assert hasService(service);
		assert hasPhase(phase);
		Preconditions.checkArgument(!isActive(phase), "Can't register for an active phase");

		getPhase(phase).addService(getService(service), phaseBootstrapper);
	}

	// --- Phase actions

	private <A> void enterPhase(IPhaseID<A, ?> phase, A context) {
		Preconditions.checkState(!isActive(phase), "Already in phase " + phase);

		activePhases.add(phase);
		assert isActive(phase);

		getPhase(phase).enterServices(context);
	}

	private <Z> void exitPhase(IPhaseID<?, Z> phase, Z context) {
		Preconditions.checkState(isActive(phase), "Currently not in phase " + phase);

		activePhases.remove(phase);
		assert !isActive(phase);

		getPhase(phase).exitServices(context);
	}

	private void declareParent(IPhaseID<?, ?> phase, IPhaseID<?, ?> parent) {
		// TODO: implement an ordering of phases
		throw new NotImplementedException("Currently unavailable");
	}
}
