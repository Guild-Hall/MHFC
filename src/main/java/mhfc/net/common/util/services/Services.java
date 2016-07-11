package mhfc.net.common.util.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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
		private final String name;

		public ID(String name) {
			this.name = name;
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof ID && ((ID) obj).id == id;
		}

		@Override
		public int hashCode() {
			return Integer.hashCode(id);
		}

		@Override
		public String toString() {
			return Objects.toString(name) + "[" + id + "]";
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> IServiceID<T> nextServiceID(String name) {
		return new ID(name);
	}

	@SuppressWarnings("unchecked")
	private static <A, Z> IPhaseID<A, Z> nextPhaseID(String name) {
		return new ID(name);
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
	public static <T> Optional<T> getService(IServiceKey<T> serviceKey) {
		return getInstance().getServiceFor(serviceKey);
	}

	private interface IServiceRetrieval<T> extends IServiceKey<T> {
		Optional<T> retrieveService();
	}

	private class ServiceEntry<T> implements IServiceAccess<T>, IServiceRetrieval<T> {
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
		public <A, Z> IServiceAccess<T> addTo(IPhaseKey<A, Z> phase, IServicePhaseHandle<T, A, Z> phaseBootstrapper) {
			Services.this.registerServiceForPhase(getID(), tryUpcastPhase(phase), phaseBootstrapper);
			return this;
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

		protected T getServiceDirect() {
			assert getState() == LifeCycle.ACTIVE;

			return service;
		}

		@Override
		public Optional<T> retrieveService() {
			if (this.getState() != LifeCycle.ACTIVE) {
				return Optional.empty();
			}

			return Optional.of(getServiceDirect());
		}

		@Override
		public Services getServiceProvider() {
			return Services.this;
		}

		@Override
		public <U> ServiceIndirection<U, T> withIndirection(Function<T, U> remap) {
			return new ServiceIndirection<>(this, remap);
		}
	}

	private class ServiceIndirection<T, O> implements IServiceRetrieval<T> {
		private IServiceRetrieval<O> parent;
		private Function<O, T> remap;

		public ServiceIndirection(IServiceRetrieval<O> parent, Function<O, T> remapper) {
			this.parent = Objects.requireNonNull(parent);
			this.remap = Objects.requireNonNull(remapper);
		}

		@Override
		public Optional<T> retrieveService() {
			return parent.retrieveService().map(remap);
		}

		@Override
		public IServiceProvider getServiceProvider() {
			return Services.this;
		}

		@Override
		public <U> ServiceIndirection<U, T> withIndirection(Function<T, U> remap) {
			return new ServiceIndirection<>(this, remap);
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
				phaseHandler.onPhaseStart(service.getServiceDirect(), context);
			}

			protected void exit(Z context) {
				phaseHandler.onPhaseEnd(service.getServiceDirect(), context);
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

		private final IPhaseID<A, Z> phaseID;
		private final Set<ServicePhaseEntry<?>> dependantServices;
		private final Set<PhaseEntry<?, ?>> required;
		private final Set<PhaseEntry<?, ?>> requiredFor;
		private final Set<Consumer<A>> entryCallbacks;
		private final Set<Consumer<Z>> exitCallbacks;

		private A defaultStartupContext;
		private Z defaultShutdownContext;
		private boolean hasDefaultStartupContext, hasDefaultShutdownContext;

		protected PhaseEntry(IPhaseID<A, Z> phase) {
			this.phaseID = Objects.requireNonNull(phase);
			this.dependantServices = new HashSet<>();
			this.required = new HashSet<>();
			this.requiredFor = new HashSet<>();
			this.entryCallbacks = new HashSet<>();
			this.exitCallbacks = new HashSet<>();
		}

		private boolean isActive() {
			return Services.this.isActive(phaseID);
		}

		protected IPhaseID<A, Z> getID() {
			return phaseID;
		}

		@Override
		public Services getServiceProvider() {
			return Services.this;
		}

		protected void addRequirement(PhaseEntry<?, ?> phase) {
			assert phase != null;
			assert !isActive();

			required.add(phase);
		}

		protected void addRequiredFor(PhaseEntry<?, ?> phase) {
			assert phase != null;
			assert !isActive();

			requiredFor.add(phase);

		}

		private void tryStartPhase() {
			if (isActive()) {
				return; // Checks for double-dependancies in requirements
			}
			Preconditions.checkState(
					hasDefaultStartupContext,
					"Can't start phase " + phaseID + " without default startup context");
			Services.this.enterPhase(phaseID, defaultStartupContext);
		}

		protected void enterServices(A context) {
			for (PhaseEntry<?, ?> phase : required) {
				phase.tryStartPhase();
			}
			// Check after to catch cyclic requirements. Which we don't want to deal with
			Preconditions.checkState(!isActive(), "Already in phase " + phaseID);
			for (ServicePhaseEntry<?> service : dependantServices) {
				service.enter(context);
			}
			for (Consumer<A> callback : entryCallbacks) {
				callback.accept(context);
			}
		}

		private void tryExitPhase() {
			if (!Services.this.isActive(phaseID)) {
				return; // Checks for double-dependancies in requirements
			}
			Preconditions.checkState(
					hasDefaultShutdownContext,
					"Can't exit phase " + phaseID + " without default shutdown context");
			Services.this.exitPhase(phaseID, defaultShutdownContext);
		}

		protected void exitServices(Z context) {
			for (PhaseEntry<?, ?> phase : requiredFor) {
				phase.tryExitPhase();
			}
			// Check after to catch cyclic requirements. Which we don't want to deal with
			Preconditions.checkState(isActive(), "Currently not in phase " + phaseID);
			for (ServicePhaseEntry<?> service : dependantServices) {
				service.exit(context);
			}
			for (Consumer<Z> callback : exitCallbacks) {
				callback.accept(context);
			}
		}

		protected <T> void addService(ServiceEntry<T> service, IServicePhaseHandle<T, A, Z> phaseHandler) {
			Preconditions.checkArgument(!isActive(), "Can't register for an active phase");

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
		public IPhaseAccess<A, Z> declareParent(IPhaseKey<?, ?> other) throws IllegalArgumentException {
			Services.this.declareParent(getID(), tryUpcastPhase(other));
			return this;
		}

		@Override
		public IPhaseAccess<A, Z> setDefaultStartupContext(A context) {
			this.defaultStartupContext = context;
			this.hasDefaultStartupContext = true;
			return this;
		}

		@Override
		public IPhaseAccess<A, Z> clearDefaultStartupContext() {
			this.defaultStartupContext = null; // Clear to free mem
			this.hasDefaultStartupContext = false;
			return this;
		}

		@Override
		public IPhaseAccess<A, Z> setDefaultShutdownContext(Z context) {
			this.defaultShutdownContext = context;
			this.hasDefaultShutdownContext = true;
			return this;
		}

		@Override
		public IPhaseAccess<A, Z> clearDefaultShutdownContext() {
			this.defaultShutdownContext = null;
			this.hasDefaultShutdownContext = false;
			return this;
		}

		@Override
		public void registerEntryCallback(Consumer<A> onEntry) {
			entryCallbacks.add(Objects.requireNonNull(onEntry));
		}

		@Override
		public void unregisterEntryCallback(Consumer<A> onEntry) {
			entryCallbacks.remove(onEntry);
		}

		@Override
		public void registerExitCallback(Consumer<Z> onExit) {
			exitCallbacks.add(Objects.requireNonNull(onExit));
		}

		@Override
		public void unregisterExitCallback(Consumer<Z> onExit) {
			exitCallbacks.remove(onExit);
		}
	}

	private Map<IServiceID<?>, ServiceEntry<?>> services = new HashMap<>();
	private Map<IPhaseID<?, ?>, PhaseEntry<?, ?>> phases = new HashMap<>();
	private Set<IPhaseID<?, ?>> activePhases = new HashSet<>();

	@Override
	public <T> Optional<T> getServiceFor(IServiceKey<T> serviceKey) {
		IServiceRetrieval<T> serviceID = tryUpcastService(serviceKey);

		return serviceID.retrieveService();
	}

	@Override
	public boolean isActive(IPhaseKey<?, ?> phase) {
		return isActive(tryUpcastPhase(phase));
	}

	@Override
	public <T> IServiceAccess<T> registerService(
			String name,
			IServiceHandle<T> serviceBootstrap,
			Supplier<T> serviceSupplier) {
		IServiceID<T> serviceID = nextServiceID(name);
		assert !hasService(serviceID);

		ServiceEntry<T> entry = new ServiceEntry<>(serviceID, serviceSupplier, serviceBootstrap);
		addService(serviceID, entry);
		return entry;
	}

	@Override
	public <A, Z> IPhaseAccess<A, Z> registerPhase(String name) {
		IPhaseID<A, Z> phaseID = nextPhaseID(name);
		assert !hasPhase(phaseID);

		PhaseEntry<A, Z> entry = new PhaseEntry<>(phaseID);
		addPhase(phaseID, entry);
		return entry;
	}

	// --- Helper methods

	private <T> IServiceRetrieval<T> tryUpcastService(IServiceKey<T> key) {
		Preconditions.checkArgument(key instanceof IServiceRetrieval, "not a service key from this service provider");
		IServiceRetrieval<T> entry = IServiceRetrieval.class.cast(key);
		Preconditions.checkArgument(entry.getServiceProvider() == this, "not a service key from this service provider");
		return entry;
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

		getPhase(phase).addService(getService(service), phaseBootstrapper);
	}

	// --- Phase actions

	private <A> void enterPhase(IPhaseID<A, ?> phase, A context) {
		getPhase(phase).enterServices(context);

		activePhases.add(phase);
		assert isActive(phase);
	}

	private <Z> void exitPhase(IPhaseID<?, Z> phase, Z context) {
		getPhase(phase).exitServices(context);

		activePhases.remove(phase);
		assert !isActive(phase);
	}

	private void declareParent(IPhaseID<?, ?> phase, IPhaseID<?, ?> parent) {
		PhaseEntry<?, ?> dependant = getPhase(phase);
		PhaseEntry<?, ?> dependancy = getPhase(parent);

		Preconditions.checkState(!isActive(phase), "Phase " + phase + " can't be active while declaring a requirement");
		Preconditions.checkState(
				!isActive(parent),
				"Phase " + parent + " can't be active while being declaring a requirement");

		dependant.addRequirement(getPhase(parent));
		dependancy.addRequiredFor(getPhase(phase));
	}
}
