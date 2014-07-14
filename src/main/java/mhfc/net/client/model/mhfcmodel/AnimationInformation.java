package mhfc.net.client.model.mhfcmodel;

import java.util.List;

/**
 * This Interface is normally instantiated through a anonymous class.
 * It has to be handed over to a {@link ModelMHFC} for it to
 * determine its current status.
 *
 * @author WorldSEnder
 *
 */
public interface AnimationInformation {
	/**
	 * Gets the animation to play. If you return null here the model will
	 * be displayed in bind pose.
	 *
	 * @return the current attack
	 */
	public MHFCAttack getCurrentAttack();
	/**
	 * Gets the frame the animation is currently in. If the frame is not in
	 * range of the animation and the animation is not cyclic it will be shown
	 * as clamped to (0, lastFrame).
	 *
	 * @return the current frame/tick of the animation to play
	 */
	public int getAnimationFrame();
	/**
	 * Gets a list of parts that will be rendered. Like this one can switch
	 * very easily between multiple looks.
	 * The Strings must be the same as the specific part is named in the model
	 * itself. Returning {@code null} here will not render anything.
	 *
	 * @return a list of parts to render
	 */
	public List<String> getPartsToRender();
}
