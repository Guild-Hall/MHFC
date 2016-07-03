package mhfc.net.common.util.services;

/**
 * Is the public interface of the key for a phase.
 * <p>
 * 
 * @author WorldSEnder
 *
 * @param A
 *            the type of startup context
 * @param Z
 *            the type of shutdown context
 */
public interface IPhaseKey<A, Z> {
	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);
}
