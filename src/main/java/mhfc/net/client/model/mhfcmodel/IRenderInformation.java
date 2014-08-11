package mhfc.net.client.model.mhfcmodel;

import com.google.common.base.Predicate;

/**
 * This Interface is normally instantiated through a anonymous class. It has to
 * be handed over to a {@link ModelMHFC} for it to determine its current status.
 *
 * @author WorldSEnder
 *
 */
public interface IRenderInformation {
	/**
	 * Gets the animation to play. If you return null here the model will be
	 * displayed in bind pose.
	 *
	 * @return the current attack
	 */
	public Animation getCurrentAnimation();
	/**
	 * Returns a predicate for the given subframe that tests whether to render a
	 * certain part or not. This allows the animation to define parts that are
	 * only visible during a part of the animation like the spikes on
	 * Nargacuga's tail.
	 *
	 * @param part
	 *            the part in question
	 * @param subFrame
	 *            the current subframe to render
	 */
	public Predicate<String> getPartFilter(float subFrame);
}
