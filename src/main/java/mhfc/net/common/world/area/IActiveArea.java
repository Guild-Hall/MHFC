package mhfc.net.common.world.area;

import java.io.Closeable;

/**
 * Represents an {@link IArea} in use. It can be dismissed via {@link #dismiss()}.
 *
 * @author WorldSEnder
 *
 */
public interface IActiveArea extends Closeable {
	/**
	 * Gets the area of the active area
	 *
	 * @return the underlying area
	 */
	IArea getArea();

	/**
	 * Gets the {@link IAreaType} of the underlying area
	 *
	 * @return
	 */
	IAreaType getType();

	/**
	 * Dismisses the area. All following calls to this area should fail - and may throw an
	 * {@link IllegalStateException}.
	 *
	 */
	void dismiss() throws IllegalStateException;

	/**
	 * {@inheritDoc}
	 *
	 * @see #dismiss()
	 * @see ActiveAreaAdapter
	 */
	@Override
	default void close() {
		dismiss();
	}

	void you_should_probably_inherit_from_ActiveAreaAdapter();
}
