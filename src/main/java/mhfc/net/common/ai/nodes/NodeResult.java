package mhfc.net.common.ai.nodes;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Variant of (T result) x (continuation) x (push newNode appl).<br>
 * If result, this means that the node that returned this popped.<br>
 * If continuation, then this node will be called again, by continuation.apply(node)<br>
 * If push, then newNode will be first evaluated. If newNode is popped with a result u, appl.apply(node, u) is called
 *
 * @author WorldSEnder
 *
 * @param <T>
 *            the result type
 * @param <N>
 *            the node type
 */
public class NodeResult<T, N extends IAINode<T, N>> {
	public static enum ResultType {
		VALUE,
		CONTINUATION,
		PUSH;
	}

	public static class PushNode<T, N extends IAINode<T, N>, U, M extends IAINode<U, M>> {
		private final M newNode;
		private final BiFunction<? super N, ? super U, NodeResult<T, N>> applyResult;

		public PushNode(
				M pushedNode,
				BiFunction<? super N, ? super U, NodeResult<T, N>> applyResult) {
			this.newNode = Objects.requireNonNull(pushedNode);
			this.applyResult = Objects.requireNonNull(applyResult);
		}

		public M getNewNode() {
			return newNode;
		}

		public BiFunction<? super N, ? super U, NodeResult<T, N>> getApplyResult() {
			return applyResult;
		}
	}

	private final ResultType type;
	private final T result;
	private final Function<? super N, NodeResult<T, N>> continuation;
	private final PushNode<T, N, ?, ?> push;

	private NodeResult(T result) {
		this.type = ResultType.VALUE;
		this.result = result;
		this.continuation = null;
		this.push = null;
	}

	private NodeResult(Function<? super N, NodeResult<T, N>> continuation) {
		this.type = ResultType.CONTINUATION;
		this.result = null;
		this.continuation = Objects.requireNonNull(continuation);
		this.push = null;
	}

	private NodeResult(PushNode<T, N, ?, ?> push) {
		this.type = ResultType.PUSH;
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

	public Function<? super N, NodeResult<T, N>> getContinuation() {
		checkType(ResultType.CONTINUATION);
		return continuation;
	}

	public PushNode<T, N, ?, ?> getPush() {
		checkType(ResultType.PUSH);
		return push;
	}

	/**
	 * Signals that the node has finished and computed the value result.
	 *
	 * @param result
	 *            the compute value
	 * @return
	 */
	public static <T, N extends IAINode<T, N>> NodeResult<T, N> withResult(T result) {
		return new NodeResult<>(result);
	}

	/**
	 * Signals that the node has not yet finished and should called again next frame by calling continuation.apply(node)
	 *
	 * @param continuation
	 *            the function to at the start of the next frame
	 * @return
	 */
	public static <T, N extends IAINode<T, N>> NodeResult<T, N> andThen(
			Function<? super N, NodeResult<T, N>> continuation) {
		return new NodeResult<>(continuation);
	}

	/**
	 * Signals that the node has not yet finished and waits for the result of pushedNode. First, pushedNode.begin() will
	 * be called, then, before control is returned to the calling node, the result of pushedNode must be computed. When
	 * the result of pushedNode is available, applyResult will be called with the result and the calling node as
	 * arguments.<br>
	 * Note: it is possible that no frames pass between returning with waitFor and the call to applyResult
	 *
	 * @param pushedNode
	 *            the node to execute before the calling node
	 * @param applyResult
	 *            the function to call when a result is available
	 * @return
	 */
	public static <T, N extends IAINode<T, N>, U, M extends IAINode<U, M>> NodeResult<T, N> waitFor(
			M pushedNode,
			BiFunction<? super N, ? super U, NodeResult<T, N>> applyResult) {
		PushNode<T, N, U, M> pushNode = new PushNode<>(pushedNode, applyResult);
		return new NodeResult<>(pushNode);
	}

	/**
	 * Convenience function. Like {@link #waitFor(IAINode, BiFunction)} but the result is ignored.
	 *
	 * @param pushedNode
	 * @param continuation
	 * @return
	 * @see #waitFor(IAINode, BiFunction)
	 */
	public static <T, N extends IAINode<T, N>, U, M extends IAINode<U, M>> NodeResult<T, N> waitUntil(
			M pushedNode,
			Function<? super N, NodeResult<T, N>> continuation) {
		return waitFor(pushedNode, (n, u) -> continuation.apply(n));
	}

}
