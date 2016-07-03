package mhfc.net.common.util.services;

public class PrivateID {
	/**
	 * Used to generate a new id for a phase. The id is used to register and authenticate the user as to manipulate a
	 * phase in an {@link IServiceProvider}. It should be kept private.
	 *
	 * @return a new id
	 */
	public static <A, Z> IPhaseID<A, Z> generatePhaseID() {
		return new IPhaseID<A, Z>() {
		};
	}

	/**
	 * Used to generate a new id for a service. The id is used to register and authenticate the user as to manipulate a
	 * service in an {@link IServiceProvider}. It should be kept private.
	 *
	 * @return a new id
	 */
	public static <T> IServiceID<T> generateServiceID() {
		return new IServiceID<T>() {
		};
	}

}
