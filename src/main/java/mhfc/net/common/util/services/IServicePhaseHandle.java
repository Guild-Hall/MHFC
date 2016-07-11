package mhfc.net.common.util.services;

/**
 * An {@link IServicePhaseHandle} is a service's handle for a specific phase.
 *
 * @author WorldSEnder
 *
 * @param T
 *            the type of service
 * @param A
 *            the type of startup context
 * @param Z
 *            the type of shutdown context
 */
public interface IServicePhaseHandle<T, A, Z> {
	public static <T, A, Z> IServicePhaseHandle<T, A, Z> noInit() {
		return new IServicePhaseHandle<T, A, Z>() {
			@Override
			public void onPhaseStart(T service, A startupContext) {}

			@Override
			public void onPhaseEnd(T service, Z shutdownContext) {}
		};
	}

	/**
	 * Called when the service enters a phase it was registered for.
	 * <p>
	 * The startup context could offer a way to load stored data
	 *
	 * @param service
	 *            the service instance that is currently active.
	 * @param startupContext
	 *            the context this service is being entering
	 */
	void onPhaseStart(T service, A startupContext);

	/**
	 * Called when the service exits a phase it was registed for.
	 * <p>
	 * The shutdown context could offer a way to store loaded data
	 *
	 * @param service
	 *            the service instance that is currently active.
	 * @param shutdownContext
	 *            the context this service is being shutdowed in
	 */
	void onPhaseEnd(T service, Z shutdownContext);

}
