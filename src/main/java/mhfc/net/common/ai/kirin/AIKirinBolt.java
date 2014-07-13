package mhfc.net.common.ai.kirin;

import mhfc.net.common.ai.AIAnimation;
import mhfc.net.common.entity.mob.EntityKirin;
import mhfc.net.common.entity.projectile.EntityLightning;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class AIKirinBolt extends AIAnimation<EntityKirin> {

	private EntityKirin kirin;
	private EntityLivingBase target;

	public AIKirinBolt(EntityKirin entity) {
		super(entity);
		kirin = entity;
		target = null;
	}

	@Override
	public boolean shouldAnimate() {
		target = kirin.getAttackTarget();
		if ((target == null)) {
			return false;
		}
		if (target.getDistanceSqToEntity(kirin) < 2D) {
			return false;
		}

		return kirin.currentAttackID == 1;
	}

	@Override
	public int getAnimID() {
		return 1;
	}

	@Override
	public void startExecuting() {
		super.startExecuting();

	}

	@Override
	public boolean isAutomatic() {
		return false;
	}

	@Override
	public int getDuration() {
		return 20;
	}

	@Override
	public void updateTask() {
		if (kirin.getAnimTick() == 10) {
			EntityLightning l = new EntityLightning(kirin.worldObj);

			if (target instanceof EntityPlayer
					|| target instanceof EntityWyvernHostile) {
				target.attackEntityFrom(DamageSource.causeMobDamage(kirin), 9F);
				target.motionX = 0.46D;
			} else {
				target.attackEntityFrom(DamageSource.causeMobDamage(kirin),
						25F * 4);
			}
			l.setPosition(target.posX + target.getRNG().nextInt(2),
					target.posY, target.posZ + target.getRNG().nextInt(2));
			kirin.worldObj.spawnEntityInWorld(l);

		}
	}

	@Override
	public void resetTask() {
		super.resetTask();
	}

}
