package mhfc.net.common.ai.general.provider.adapters;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.Vec3d;

public class AttackPointAdapter<T extends EntityLiving> extends ConstantAirTimeAdapter<T> {

	private static class ConstPointResolver<T extends EntityLiving> implements ITargetResolver<T> {

		private Vec3d targetPoint;

		public ConstPointResolver(Vec3d target) {
			this.targetPoint = target;
		}

		@Override
		public Vec3d getJumpTarget(T entity) {
			return this.targetPoint;
		}

	}

	public AttackPointAdapter(float jumpTimeInTicks, Vec3d targetPoint) {
		super(jumpTimeInTicks, new ConstPointResolver<T>(targetPoint));
	}

}
