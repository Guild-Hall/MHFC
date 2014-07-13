package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.AIAnimation;
import mhfc.net.common.entity.mob.EntityTigrex;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class AITigrexLeap extends AIAnimation<EntityTigrex> {

	private EntityTigrex tigrex;
	private EntityLivingBase target;
	// private Random rand;
	float chance;

	public AITigrexLeap(EntityTigrex entity) {
		super(entity);
		tigrex = entity;
		target = null;
	}

	@Override
	public boolean shouldAnimate() {
		target = tigrex.getAttackTarget();
		if ((target == null) || (tigrex.getDistanceSqToEntity(target) < 24D)) {
			return false;
		}
		return tigrex.getAnimID() == 4;
	}

	@Override
	public int getAnimID() {
		return 4;
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
		target = tigrex.getAttackTarget();
	}

	@Override
	public int getDuration() {
		return 20;
	}

	@Override
	public void updateTask() {
		double d0 = this.target.posX - this.tigrex.posX;
		// double d1 = this.target.posZ - this.tigrex.posZ;
		// float f = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
		if (tigrex.getAnimTick() == 5) {
			if (target instanceof EntityPlayer) {
				target.attackEntityFrom(DamageSource.causeMobDamage(tigrex),
						35F);
				tigrex.motionX = d0 + 0.03D;
				tigrex.motionY = 0.8D;
				target.motionX = 0.7D;
				target.motionY = 0.6D;
			} else {
				target.attackEntityFrom(DamageSource.causeMobDamage(tigrex),
						40F * 4);
				tigrex.motionX = d0 + 0.03D;
				tigrex.motionY = 0.8D;
				target.motionX = 0.7D;
				target.motionY = 0.6D;
			}

		}
	}

}
