package mhfc.net.common.ai.nodes;

import java.util.function.Supplier;

import com.github.worldsender.mcanm.common.animation.IAnimation;

import mhfc.net.common.ai.IExecutableAction;
import net.minecraft.entity.EntityLivingBase;

public abstract class NodeBasedAction<T extends EntityLivingBase, C extends BasicExecutionContext<T>>
		implements
		IExecutableAction<T> {

	private static ExecutionElement<?, Void> buildFirstNode(IAINode<?> firstNode) {
		return new ExecutionElement<>(firstNode::begin, u -> null);
	}

	private ExecutionElement<?, Void> bottomNode;
	private C context = createContext(null);

	protected abstract IAINode<?> getFirstNode(C executionContext);

	protected abstract C createContext(T entity);

	@Override
	public void rebind(T entity) {
		this.context = createContext(entity);
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
		Supplier<NodeResult<Void>> capturedResult = bottomNode.doContinue();
		if (capturedResult != null) {
			// The bottom node has finished executing, we don't care about the result
			bottomNode = null;
		}
	}

	@Override
	public void beginAction() {
		bottomNode = buildFirstNode(getFirstNode(context));
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
		return context.getAnimation();
	}

	@Override
	public int getCurrentFrame() {
		return context.getAnimationFrame();
	}

}
