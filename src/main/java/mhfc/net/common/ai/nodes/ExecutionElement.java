package mhfc.net.common.ai.nodes;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import mhfc.net.common.ai.nodes.NodeResult.PushNode;

/**
 * Represents one execution element
 *
 * @author WorldSEnder
 *
 * @param <T>
 *            the result type of this element
 * @param <P>
 *            the result type of the parent element
 */
/* package */ final class ExecutionElement<T, P> {
	// The continuation to apply
	private Supplier<NodeResult<T>> continuation;
	// The function to apply to the result
	private Function<? super T, NodeResult<P>> applyResult;
	// A node that has to be completed before this one
	private ExecutionElement<?, T> completeBefore;

	public ExecutionElement(Supplier<NodeResult<T>> continuation, Function<? super T, NodeResult<P>> applyResult) {
		this.continuation = Objects.requireNonNull(continuation);
		this.applyResult = Objects.requireNonNull(applyResult);
		this.completeBefore = null;
	}

	/**
	 * Test if there is an element that we are waiting for, i.e. completeBefore != null and execute it if necessary
	 * before this element
	 *
	 * @return true if execution should be delayed until next call
	 */
	private boolean testUnfinishedCompleteBefore() {
		if (completeBefore == null) {
			// Nothing to finish before hand
			return false;
		}
		Supplier<NodeResult<T>> beforeResult = completeBefore.doContinue();
		if (beforeResult == null) {
			// completeBefore has not yet finished, so we haven't either
			return true;
		}
		// Complete-before has finished
		completeBefore = null;
		continuation = beforeResult;
		// We can continue with the execution of our node
		return false;
	}

	/**
	 * continue once, returns null if execution of this node is not finished yet returns non null method describing a
	 * new continuation otherwise
	 **/
	/* package */ Supplier<NodeResult<P>> doContinue() {
		do {
			final boolean isCompleteBeforeUnfinished = testUnfinishedCompleteBefore();
			if (isCompleteBeforeUnfinished) {
				return null;
			}
			assert continuation != null;
			final NodeResult<T> nodeResult = continuation.get();

			switch (nodeResult.getType()) {
			case VALUE: // We have a result
				final T actualResult = nodeResult.getResult();
				return () -> applyResult.apply(actualResult);
			case YIELD:
				continuation = nodeResult.getContinuation();
				assert continuation != null; // Invariant from nodeResult
				return null; // We are not finished yet, continue on next invocation
			case AWAIT:
				PushNode<T, ?> toPush = nodeResult.getPush();
				completeBefore = buildCompleteBefore(toPush);
				continue;
			default:
				throw new IllegalArgumentException("node result with illegal result type");
			}
		} while (true);
	}

	// Convenience method to capture C and O
	private <C> ExecutionElement<C, T> buildCompleteBefore(PushNode<T, C> toPush) {
		return new ExecutionElement<>(() -> toPush.getAwaitedResult(), toPush.getAndThen());
	}
}
