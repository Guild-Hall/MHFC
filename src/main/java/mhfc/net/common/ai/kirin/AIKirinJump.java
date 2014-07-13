package mhfc.net.common.ai.kirin;

import java.util.List;
import java.util.Random;

import mhfc.net.common.ai.AIAnimation;
import mhfc.net.common.entity.mob.EntityKirin;
import mhfc.net.common.entity.projectile.EntityLightning;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class AIKirinJump extends AIAnimation<EntityKirin> {

	private EntityKirin kirin;
	private EntityLivingBase target;
	private Random rand;
	public float jump;
	public AIKirinJump(EntityKirin type, float jumpmeter) {
		super(type);
		kirin = type;
		rand = type.getRNG();
		jump = jumpmeter;
	}

	@Override
	public int getAnimID() {
		return 2;
	}

	@Override
	public boolean shouldAnimate() {
		target = kirin.getAttackTarget();
		if (target == null || !kirin.onGround) return false;
		return kirin.getAnimID() == 0 && rand.nextInt(12) == 0;
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
		if (kirin.animTick == 10) {
			kirin.motionY = jump;
		}
		if (kirin.animTick > 16) {

			@SuppressWarnings("unchecked")
			List<Entity> list = kirin.worldObj
					.getEntitiesWithinAABBExcludingEntity(kirin,
							kirin.boundingBox.expand(10D, 6.0D, 10D));
			list.remove(kirin);
			for (Entity entity : list) {
				EntityLightning l = new EntityLightning(kirin.worldObj);
				l.setPosition(entity.posX, entity.posY, entity.posZ);
				kirin.worldObj.spawnEntityInWorld(l);
			}

			if (target instanceof EntityPlayer
					|| target instanceof EntityWyvernHostile) {
				target.attackEntityFrom(DamageSource.causeMobDamage(kirin), 13F);
				target.motionX = 0.46D;
			} else {
				target.attackEntityFrom(DamageSource.causeMobDamage(kirin),
						55F * 4);
			}
		}

	}

}
