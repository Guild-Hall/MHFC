package mhfc.net.common.ai.general.provider.adapters;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

/**
 * A class that implements the jump parameter aiming to provide a constant jump time when jumping towards the enemy with
 * enough speed to land directly at his position.
 */
public class AttackTargetAdapter<T extends EntityLiving> extends ConstantAirTimeAdapter<T> {

	public AttackTargetAdapter(float jumpTimeInTicks) {
		super(jumpTimeInTicks, entity -> {
			EntityLivingBase attackTarget = entity.getAttackTarget();
			Vec3d target;
			if (attackTarget != null) {
				target = attackTarget.getPositionVector();
			} else {
				target = entity.getPositionVector();
			}
			return target;
		});
	}
}
