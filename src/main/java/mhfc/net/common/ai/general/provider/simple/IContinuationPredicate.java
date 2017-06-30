package mhfc.net.common.ai.general.provider.simple;

import java.util.Objects;

import mhfc.net.common.ai.general.actions.AnimatedAction;

public interface IContinuationPredicate {
	/**
	 * Usage:<br>
	 * <code><pre>
	 *protected IContinuationPredicate provideContinuationPredicate() {
	 *    return IContinuationPredicate.untilFrame(this, LAST_FRAME);
	 *}
	 * </pre></code>
	 *
	 * @param action
	 * @param lastFrame
	 * @return
	 */
	public static IContinuationPredicate untilFrame(AnimatedAction<?> action, int lastFrame) {
		return () -> action.getCurrentFrame() < lastFrame;
	}

	public boolean shouldContinueAction();

	default IContinuationPredicate and(IContinuationPredicate other) {
		Objects.requireNonNull(other);
		return () -> shouldContinueAction() && other.shouldContinueAction();
	}

	default IContinuationPredicate or(IContinuationPredicate other) {
		Objects.requireNonNull(other);
		return () -> shouldContinueAction() || other.shouldContinueAction();
	}
}
