package mhfc.net.common.ai.rathalos;

import mhfc.net.common.ai.AIAnimation;
import mhfc.net.common.entity.mob.EntityRathalos;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.DamageSource;

public class AIRathalosFireball extends AIAnimation<EntityRathalos> {

	private EntityRathalos rath;
	private EntityLivingBase target;

	public AIRathalosFireball(EntityRathalos mob) {
		super(mob);
		rath = mob;
		target = null;
	}

	@Override
	public boolean shouldAnimate() {
		target = rath.getAttackTarget();
		if (target == null) {
			return false;
		}

		return rath.currentAttackID == 1;
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
	}

	@Override
	public int getAnimID() {
		return 1;
	}

	@Override
	public boolean isAutomatic() {
		return false;
	}

	@Override
	public int getDuration() {
		return 30;
	}

	@Override
	public void updateTask() {

		double d0 = target.posX - rath.posX;
		double d1 = target.boundingBox.minY + target.height / 2.0F
				- (rath.posY + rath.height / 2.0F);
		double d2 = target.posZ - rath.posZ;

		if (rath.getAnimTick() > 10) {
			EntitySmallFireball entitysmallfireball = new EntitySmallFireball(
					rath.worldObj, rath, d0, d1, d2);
			entitysmallfireball.posY = rath.posY + rath.height / 2.0F;
			entitysmallfireball.posX = rath.posX;
			entitysmallfireball.posZ = rath.posZ;
			rath.worldObj.spawnEntityInWorld(entitysmallfireball);
			if (target instanceof EntityPlayer
					|| target instanceof EntityWyvernHostile) {
				target.attackEntityFrom(DamageSource.causeFireballDamage(
						entitysmallfireball, rath), 14);
			} else {
				target.attackEntityFrom(DamageSource.causeMobDamage(rath),
						90F * 5);
			}

		}

	}
}
