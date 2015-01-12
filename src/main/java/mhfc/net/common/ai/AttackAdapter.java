package mhfc.net.common.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

import com.github.worldsender.mcanm.client.model.mhfcmodel.animation.IAnimation;

public abstract class AttackAdapter<T extends EntityLivingBase>
		implements
			IExecutableAttack<T> {
	private IAnimation animation;
	protected T entity;
	private EntityLivingBase target;

	public AttackAdapter() {}

	public void setAnimation(IAnimation anim) {
		this.animation = anim;
	}

	@Override
	public IAnimation getCurrentAnimation() {
		return this.animation;
	}

	@Override
	public void updateEntity(T entity) {
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
