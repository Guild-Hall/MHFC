package mhfc.net.common.ai.tigrex;

import java.util.List;

import mhfc.net.common.ai.AIAnimation;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class AITigrexSpin extends AIAnimation<EntityTigrex> {

	private EntityTigrex tigrex;
	private EntityLivingBase target;

	public AITigrexSpin(EntityTigrex entity) {
		super(entity);
		tigrex = entity;
		target = null;
	}

	@Override
	public boolean shouldAnimate() {
		target = tigrex.getAttackTarget();
		if (target == null) {
			return false;
		}
		return tigrex.getAnimID() == 3;
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
		target = tigrex.getAttackTarget();
	}

	@Override
	public void resetTask() {
		super.resetTask();
	}

	@Override
	public int getAnimID() {
		return 3;
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public int getDuration() {
		return 40;
	}

	@Override
	public void updateTask() {
		@SuppressWarnings("unchecked")
		List<Entity> list = tigrex.worldObj
				.getEntitiesWithinAABBExcludingEntity(tigrex,
						tigrex.boundingBox.expand(4D, 2.0D, 4D));
		list.remove(tigrex);

		for (Entity entity : list) {
			if (tigrex.getAnimTick() == 5) {
				if (entity instanceof EntityPlayer
						|| entity instanceof EntityWyvernHostile) {
					tigrex.getLookHelper().setLookPositionWithEntity(target,
							30F, 30F);
					entity.attackEntityFrom(
							DamageSource.causeMobDamage(tigrex), 14F);
					target.motionY = 1.2D;

				} else {
					entity.attackEntityFrom(
							DamageSource.causeMobDamage(tigrex), 49F * 4);
					target.motionY = 1.2D;
				}
			}
		}

	}
}
