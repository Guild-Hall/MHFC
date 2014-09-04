package mhfc.net.client.model.mhfcmodel.animation;

import mhfc.net.client.render.mob.RenderAnimatedModel;

import com.google.common.base.Predicate;
/**
 * An animatable type that can be rendered with a {@link RenderAnimatedModel} or
 * a //TODO: RenderMHFCModelTile
 *
 * @author WorldSEnder
 *
 */
public interface IAnimatedObject {
	/**
	 * A suitable {@link Predicate} to return in
	 * {@link #getPartPredicate(float)} to render all parts without exception.
	 * Note that this will not test if the currently executed animation wants to
	 * display the part.
	 */
	public static final Predicate<String> RENDER_ALL = new Predicate<String>() {
		@Override
		public boolean apply(String input) {
			return true;
		}
	};
	/**
	 * A suitable {@link Predicate} to return in
	 * {@link #getPartPredicate(float)} to render no parts without exception.
	 * Note that this will not test if the currently executed animation wants to
	 * display the part.
	 */
	public static final Predicate<String> RENDER_NONE = new Predicate<String>() {
		@Override
		public boolean apply(String input) {
			return false;
		}
	};
	/**
	 * Gets the animation to play. If you return null here the model will be
	 * displayed in bind pose.
	 *
	 * @return the current attack
	 */
	public IAnimation getCurrentAnimation();
	/**
	 * Returns the current Frame in the animation. This is not inside the
	 * {@link IAnimation} so that each object/entity can decide on its own.
	 */
	public int getCurrentFrame();
	/**
	 * Returns for a specific subFrame in the current animation a predicate that
	 * get applied all model-parts to test if they should be rendered.
	 *
	 * @param subFrame
	 *            makes the method subFrame sensible
	 *
	 * @return a predicate to match parts against that may be rendered
	 */
	public Predicate<String> getPartPredicate(float subFrame);
}
