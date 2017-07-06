package mhfc.net.common.ai.nodes;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.lang3.NotImplementedException;

import com.github.worldsender.mcanm.common.animation.IAnimation;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.nodes.NodeResult.PushNode;
import net.minecraft.entity.EntityLivingBase;

public abstract class NodeBasedAction<T extends EntityLivingBase> implements IExecutableAction<T> {

	/**
	 * Represents one execution element
	 *
	 * @author WorldSEnder
	 *
	 * @param <B>
	 *            the result type of this element
	 * @param <N>
	 *            the node type of this element
	 * @param <A>
	 *            the result type of the parent element
	 * @param <M>
	 *            the node type of the parent element
	 */
	private static class ExecutionElement<B, N extends IAINode<B, N>, A, M extends IAINode<A, M>> {
		// The ai node to execute
		private final N node;
		// The continuation to apply
		private Function<? super N, NodeResult<B, N>> continuation;
		// The function to apply to the result
		private BiFunction<? super M, ? super B, NodeResult<A, M>> applyResult;
		// A node that has to be completed before this one
		private ExecutionElement<?, ?, B, N> completeBefore;

		public ExecutionElement(
				N node,
				Function<? super N, NodeResult<B, N>> continuation,
						BiFunction<? super M, ? super B, NodeResult<A, M>> applyResult) {
			this.node = node;
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
			Function<? super N, NodeResult<B, N>> beforeResult = completeBefore.doContinue();
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
		 * continue once, returns null if execution of this node is not finished yet returns non null method describing
		 * a new continuation otherwise
		 **/
		private Function<? super M, NodeResult<A, M>> doContinue() {
			do {
				final boolean isCompleteBeforeUnfinished = testUnfinishedCompleteBefore();
				if (isCompleteBeforeUnfinished) {
					return null;
				}
				assert continuation != null;
				final NodeResult<B, N> nodeResult = continuation.apply(node);

				switch (nodeResult.getType()) {
				case VALUE: // We have a result
					final B actualResult = nodeResult.getResult();
					return p -> applyResult.apply(p, actualResult);
				case CONTINUATION:
					continuation = nodeResult.getContinuation();
					assert continuation != null; // Invariant from nodeResult
					return null; // We are not finished yet, continue on next invocation
				case PUSH:
					PushNode<B, N, ?, ?> toPush = nodeResult.getPush();
					completeBefore = buildCompleteBefore(toPush);
					continue;
				default:
					throw new IllegalArgumentException("node result with illegal result type");
				}
			} while (true);
		}

		// Convenience method to capture C and O
		private <C, O extends IAINode<C, O>> ExecutionElement<C, O, B, N> buildCompleteBefore(
				PushNode<B, N, C, O> toPush) {
			return new ExecutionElement<>(toPush.getNewNode(), IAINode::begin, toPush.getApplyResult());
		}

		public Optional<IAnimation> getAnimation() {
			// All of this can be replaced in java 9 by
			// Optional.ofNullable(completeBefore).flatMap(completeBefore::getAnimation).or(node::getAnimation);
			if (completeBefore != null) {
				Optional<IAnimation> completeBeforeAnimation = completeBefore.getAnimation();
				if (completeBeforeAnimation.isPresent()) {
					return completeBeforeAnimation;
				}
			}
			return node.getAnimation();
		}

		public OptionalInt getCurrentFrame() {
			if (completeBefore != null) {
				OptionalInt completeBeforeFrame = completeBefore.getCurrentFrame();
				if (completeBeforeFrame.isPresent()) {
					return completeBeforeFrame;
				}
			}
			return node.getCurrentFrame();
		}
	}

	private static class BottomAINode implements IAINode<Void, BottomAINode> {
		private BottomAINode() {
			throw new NotImplementedException("Marker type");
		}
		@Override
		public NodeResult<Void, BottomAINode> begin() {
			throw new NotImplementedException("Marker type");
		}

		@Override
		public Optional<IAnimation> getAnimation() {
			throw new NotImplementedException("Marker type");
		}

		@Override
		public OptionalInt getCurrentFrame() {
			throw new NotImplementedException("Marker type");
		}

		public NodeResult<Void, BottomAINode> sinkResult(Void result) {
			throw new NotImplementedException("Marker method");
		}
	}

	protected T entity;

	private ExecutionElement<Void, ?, Void, BottomAINode> bottomNode;

	protected abstract IAINode<Void, ?> getFirstNode();

	// captures N
	private <N extends IAINode<Void, N>> ExecutionElement<Void, N, Void, BottomAINode> buildFirstNode(
			IAINode<Void, N> firstNode) {
		@SuppressWarnings("unchecked") // Ai node says it is of type N
		N castFirstNode = (N) firstNode;
		return new ExecutionElement<>(castFirstNode, IAINode::begin, BottomAINode::sinkResult);
	}

	@Override
	public void rebind(T entity) {
		this.entity = entity;
	}

	@Override
	public abstract float getWeight();

	@Override
	public abstract boolean forceSelection();

	@Override
	public abstract byte mutexBits();

	private boolean hasBottomNode() {
		return bottomNode != null;
	}

	private void cycleOnce() {
		assert hasBottomNode();
		// We get back the result as a capture inside a lambda
		Function<?, ?> capturedResult = bottomNode.doContinue();
		if (capturedResult != null) {
			// The bottom node has finished executing
			bottomNode = null;
		}
	}

	@Override
	public void beginAction() {
		bottomNode = buildFirstNode(getFirstNode());
		cycleOnce();
	}

	@Override
	public void updateAction() {
		cycleOnce();
	}

	@Override
	public boolean shouldContinue() {
		return hasBottomNode();
	}

	@Override
	public void finishAction() {
		// Nothing to do here
	}

	@Override
	public IAnimation getCurrentAnimation() {
		if (hasBottomNode()) {
			return bottomNode.getAnimation().orElse(null);
		}
		return null;
	}

	@Override
	public int getCurrentFrame() {
		if (hasBottomNode()) {
			return bottomNode.getCurrentFrame().orElse(0);
		}
		return 0;
	}

}
