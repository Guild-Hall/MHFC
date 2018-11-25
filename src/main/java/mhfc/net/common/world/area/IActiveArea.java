package mhfc.net.common.world.area;

import mhfc.net.common.quests.world.QuestFlair;

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
	 * Gets the {@link QuestFlair} of the underlying area
	 *
	 * @return
	 */
	QuestFlair getFlair();

	/**
	 * Called before the first player enters the area, may perform additional clean up and set up stuff.
	 *
	 * @throws IllegalStateException
	 */
	void engage() throws IllegalStateException;

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
