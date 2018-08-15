package mhfc.net.common.ai.general.provider.adapters;

import java.util.Objects;

import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IJumpProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.ai.general.provider.simple.IDamageProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.util.math.Vec3d;

public class JumpAdapter<T extends EntityMHFCBase<? super T>> extends AttackAdapter implements IJumpProvider<T> {
	protected IJumpParameterProvider<T> jumpProvider;
	protected IJumpTimingProvider<T> jumpTiming;

	public JumpAdapter(
			IAnimationProvider animProvider,
			IDamageProvider damageProvider,
			IJumpParameterProvider<T> jumpProvider,
			IJumpTimingProvider<T> jumpTiming) {
		super(animProvider, damageProvider);
		this.jumpProvider = Objects.requireNonNull(jumpProvider);
		this.jumpTiming = Objects.requireNonNull(jumpTiming);
	}

	@Override
	public float getInitialUpVelocity(T entity) {
		return jumpProvider.getInitialUpVelocity(entity);
	}

	@Override
	public float getForwardVelocity(T entity) {
		return jumpProvider.getForwardVelocity(entity);
	}

	@Override
	public Vec3d getJumpVector(T entity) {
		return jumpProvider.getJumpVector(entity);
	}

	@Override
	public IDamageCalculator getDamageCalculator() {
		return damageProvider.getDamageCalculator();
	}

	@Override
	public boolean isJumpFrame(T entity, int frame) {
		return jumpTiming.isJumpFrame(entity, frame);
	}

	@Override
	public boolean isDamageFrame(T entity, int frame) {
		return jumpTiming.isDamageFrame(entity, frame);
	}

	@Override
	public float getTurnRate(T entity, int frame) {
		return jumpTiming.getTurnRate(entity, frame);
	}

}
