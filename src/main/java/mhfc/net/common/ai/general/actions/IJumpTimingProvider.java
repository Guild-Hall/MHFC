package mhfc.net.common.ai.general.actions;

import mhfc.net.common.entity.type.EntityMHFCBase;

public interface IJumpTimingProvider<EntityT extends EntityMHFCBase<? super EntityT>> {
	/**
	 * Returns the frame at which the monster should perform the jump
	 */
	public boolean isJumpFrame(EntityT entity, int frame);

	public boolean isDamageFrame(EntityT entity, int frame);

	public float getTurnRate(EntityT entity, int frame);

	public static class JumpTimingAdapter<EntityT extends EntityMHFCBase<? super EntityT>>
			implements
			IJumpTimingProvider<EntityT> {
		protected int jumpFrame;
		protected float turnRate, turnRateAir;

		public JumpTimingAdapter(int jumpFrame, float turnRate, float turnRateAir) {
			this.jumpFrame = jumpFrame;
			this.turnRate = turnRate;
			this.turnRateAir = turnRateAir;
		}

		@Override
		public boolean isJumpFrame(EntityT entity, int frame) {
			return frame == jumpFrame;
		}

		@Override
		public float getTurnRate(EntityT entity, int frame) {
			return frame > jumpFrame ? turnRateAir : turnRate;
		}

		@Override
		public boolean isDamageFrame(EntityT entity, int frame) {
			return frame > jumpFrame;
		}

	}

}
