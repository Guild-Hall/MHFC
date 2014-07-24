package mhfc.net.client.model.mhfcmodel;

/**
 * This Interface is normally instantiated through a anonymous class. It has to
 * be handed over to a {@link ModelMHFC} for it to determine its current status.
 *
 * @author WorldSEnder
 *
 */
public interface AnimationInformation {
	/**
	 * Gets the animation to play. If you return null here the model will be
	 * displayed in bind pose.
	 *
	 * @return the current attack
	 */
	public MHFCAttack getCurrentAttack();
	/**
	 * Gets if a certain part should be shown This allows the the animation to
	 * define parts that are only visible during a part of the animation like
	 * the spikes on Nargacuga's tail. This can be overridden by the entity.
	 *
	 * @param part
	 *            the part in question
	 * @param subFrame
	 *            the current subframe to render
	 */
	public boolean shouldDisplayPart(String part, float subFrame);
}
