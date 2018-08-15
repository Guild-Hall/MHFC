package mhfc.net.common.ai.nodes;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Variant of (T result) x (yield) x (await NodeResult appl).<br>
 * If result, this means that the node that returned this popped.<br>
 * If yield, then this node will be called again next frame<br>
 * If await, then newNode will be first evaluated. When newNode returns a result with a result u, appl.apply(node, u) is
 * called
 *
 * @author WorldSEnder
 *
 * @param <T>
 *            the result type
 */
public final class NodeResult<T> {
	public static enum ResultType {
		VALUE,
		YIELD,
		AWAIT;
	}

	public static class PushNode<T, U> {
		private final NodeResult<U> waitingFor;
		private final Function<? super U, NodeResult<T>> andThen;

		public PushNode(NodeResult<U> waitingFor, Function<? super U, NodeResult<T>> andThen) {
			this.waitingFor = Objects.requireNonNull(waitingFor);
			this.andThen = Objects.requireNonNull(andThen);
		}

		public NodeResult<U> getAwaitedResult() {
			return waitingFor;
		}

		public Function<? super U, NodeResult<T>> getAndThen() {
			return andThen;
		}
	}

	private final ResultType type;
	private final T result;
	private final Supplier<NodeResult<T>> continuation;
	private final PushNode<T, ?> push;

	private NodeResult(T result) {
		this.type = ResultType.VALUE;
		this.result = result;
		this.continuation = null;
		this.push = null;
	}

	private NodeResult(Supplier<NodeResult<T>> continuation) {
		this.type = ResultType.YIELD;
		this.result = null;
		this.continuation = Objects.requireNonNull(continuation);
		this.push = null;
	}

	private NodeResult(PushNode<T, ?> push) {
		this.type = ResultType.AWAIT;
		this.result = null;
		this.continuation = null;
		this.push = push;
	}

	public ResultType getType() {
		return type;
	}

	private void checkType(ResultType expected) {
		if (type != expected) {
			throw new IllegalStateException("type was " + type + " expected " + expected);
		}
	}

	public T getResult() {
		checkType(ResultType.VALUE);
		return result;
	}

	public Supplier<NodeResult<T>> getContinuation() {
		checkType(ResultType.YIELD);
		return continuation;
	}

	public PushNode<T, ?> getPush() {
		checkType(ResultType.AWAIT);
		return push;
	}

	/**
	 * Signals that the node has finished and computed the value result.
	 *
	 * @param result
	 *            the compute value
	 * @return
	 */
	public static <T> NodeResult<T> withResult(T result) {
		return new NodeResult<>(result);
	}

	/**
	 * Signals that the node has not yet finished and should called again next frame by calling continuation.apply(node)
	 *
	 * @param continuation
	 *            the function to at the start of the next frame
	 * @return
	 */
	public static <T> NodeResult<T> yield(Supplier<NodeResult<T>> continuation) {
		return new NodeResult<>(continuation);
	}

	/**
	 * Signals that the node has not yet finished and waits for the result of pushedNode. First, pushedNode.begin() will
	 * be called, then, before control is returned to the calling node, the result of pushedNode must be computed. When
	 * the result of pushedNode is available, applyResult will be called with the result and the calling node as
	 * arguments.<br>
	 * Note: it is possible that no frames pass between returning with waitFor and the call to applyResult
	 *
	 * @param awaitedResult
	 *            the result to execute before the calling node
	 * @param applyResult
	 *            the function to call when a result is available
	 * @return
	 */
	public static <T, U> NodeResult<T> await(
			NodeResult<U> awaitedResult,
			Function<? super U, NodeResult<T>> applyResult) {
		PushNode<T, U> pushNode = new PushNode<>(awaitedResult, applyResult);
		return new NodeResult<>(pushNode);
	}

	/**
	 * Convenience function. Like {@link #await(IAINode, BiFunction)} but the result is ignored.
	 *
	 * @param pushedNode
	 * @param continuation
	 * @return
	 * @see #await(IAINode, BiFunction)
	 */
	public static <T, U> NodeResult<T> await(NodeResult<U> awaitedResult, Supplier<NodeResult<T>> continuation) {
		return await(awaitedResult, u -> continuation.get());
	}

}
