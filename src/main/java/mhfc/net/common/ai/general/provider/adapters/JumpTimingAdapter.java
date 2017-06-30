package mhfc.net.common.ai.general.provider.adapters;

import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;

public class JumpTimingAdapter<T extends EntityMHFCBase<? super T>> implements IJumpTimingProvider<T> {
	protected int jumpFrame;
	protected float turnRate, turnRateAir;

	public JumpTimingAdapter(int jumpFrame, float turnRate, float turnRateAir) {
		this.jumpFrame = jumpFrame;
		this.turnRate = turnRate;
		this.turnRateAir = turnRateAir;
	}

	@Override
	public boolean isJumpFrame(T entity, int frame) {
		return frame == jumpFrame;
	}

	@Override
	public float getTurnRate(T entity, int frame) {
		return frame > jumpFrame ? turnRateAir : turnRate;
	}

	@Override
	public boolean isDamageFrame(T entity, int frame) {
		return frame > jumpFrame;
	}

}
