package mhfc.net.common.ai.nodes;

import com.github.worldsender.mcanm.common.animation.IAnimation;

import java.util.function.IntUnaryOperator;

public class BasicExecutionContext<T> {
	private final T entity;
	private IAnimation animation;
	private int animationFrame;

	public BasicExecutionContext(T entity) {
		this.entity = entity;
	}

	protected void reset() {
		this.animation = null;
		this.animationFrame = 0;
	}

	public T getEntity() {
		return entity;
	}

	public void setAnimation(IAnimation animation) {
		this.animation = animation;
	}

	public void setFrame(int frame) {
		this.animationFrame = frame;
	}

	public void mapFrame(IntUnaryOperator mapping) {
		setFrame(mapping.applyAsInt(getAnimationFrame()));
	}

	public IAnimation getAnimation() {
		return animation;
	}

	public int getAnimationFrame() {
		return animationFrame;
	}

}
