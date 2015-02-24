package mhfc.net.common.ai;

import net.minecraft.entity.EntityLivingBase;

import com.github.worldsender.mcanm.client.model.mhfcmodel.animation.IAnimation;

public abstract class AttackAdapter<T extends EntityLivingBase>
		implements
			IExecutableAttack<T> {
	private IAnimation animation;
	private T entity;
	/**
	 * Almost every attack has a target entity. This is completely up to you if
	 * you want to use this
	 */
	protected EntityLivingBase target;

	public AttackAdapter() {}
	/**
	 * Gets the entity this attack is bounded to (executed on).
	 * 
	 * @return
	 */
	protected T getEntity() {
		return entity;
	}

	public void setAnimation(IAnimation anim) {
		this.animation = anim;
	}

	@Override
	public IAnimation getCurrentAnimation() {
		return this.animation;
	}

	@Override
	public void rebind(T entity) {
		this.entity = entity;

	}

	@Override
	public boolean forceSelection() {
		return false;
	}

	@Override
	public byte mutexBits() {
		return 7;
	}

	@Override
	public int getNextFrame(int frame) {
		return ++frame;
	}
}
