package mhfc.net.common.ai.nodes;

import java.util.Optional;
import java.util.OptionalInt;

import com.github.worldsender.mcanm.common.animation.IAnimation;

public interface IAINode<T, N extends IAINode<T, N>> {
	/**
	 * The first invocation of an ai node.
	 *
	 * @return
	 */
	NodeResult<T, N> begin();

	/**
	 * Gets the animation of this AINode
	 *
	 * @return null if this node has no animation. If so, the next node on the stack will be queried
	 */
	Optional<IAnimation> getAnimation();

	/**
	 * Get the current frame in the animation.
	 *
	 * @return null if this node has no frame. If so, the next node on the stack will be queried.
	 */
	OptionalInt getCurrentFrame();
}
